package com.pda.core.service.notice;

import com.pda.core.dto.notice.exchange.AcceptStockmonExchangeRequestDto;
import com.pda.core.dto.notice.exchange.AcceptStockmonExchangeResponseDto;
import com.pda.core.dto.notice.exchange.GetStockmonExchangeListResponseDto;
import com.pda.core.dto.notice.exchange.GetStockmonExchangeListResponseDto.StockmonExchange;
import com.pda.core.dto.notice.exchange.StockmonExchangeNoticeRequestDto;
import com.pda.core.entity.notice.ExchangeNotice;
import com.pda.core.entity.stockmon.Stockmon;
import com.pda.core.entity.traveler.Traveler;
import com.pda.core.entity.traveler.TravelerStockmon;
import com.pda.core.exception.notice.InvalidExchangeRequestException;
import com.pda.core.exception.stockmon.NoStockmonException;
import com.pda.core.exception.traveler.NoTravelerException;
import com.pda.core.repository.notice.ExchangeNoticeRepository;
import com.pda.core.repository.stockmon.StockmonRepository;
import com.pda.core.repository.traveler.TravelerRepository;
import com.pda.core.repository.traveler.TravelerStockmonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeNoticeService {

    private final ExchangeNoticeRepository exchangeNoticeRepository;
    private final TravelerStockmonRepository travelerStockmonRepository;
    private final TravelerRepository travelerRepository;
    private final StockmonRepository stockmonRepository;

    @Autowired
    public ExchangeNoticeService(ExchangeNoticeRepository exchangeNoticeRepository,
                                 TravelerStockmonRepository travelerStockmonRepository,
                                 TravelerRepository travelerRepository, StockmonRepository stockmonRepository) {
        this.exchangeNoticeRepository = exchangeNoticeRepository;
        this.travelerStockmonRepository = travelerStockmonRepository;
        this.travelerRepository = travelerRepository;
        this.stockmonRepository = stockmonRepository;
    }

    @Transactional
    public void createExchangeNotice(StockmonExchangeNoticeRequestDto requestDto, Long loggedInUserId) {
        TravelerStockmon travelerStockmon = travelerStockmonRepository
                .findByTravelerIdAndStockmonId(loggedInUserId,
                        requestDto.getTravelerStockmonId()).orElseThrow(NoStockmonException::new);

        updateStockmonCount(travelerStockmon, -1L);
        ExchangeNotice exchangeNotice = requestDto.toEntity(loggedInUserId, travelerStockmon.getStockmonAveragePrice());
        exchangeNoticeRepository.save(exchangeNotice);
    }

    public GetStockmonExchangeListResponseDto getExchangeNotices(Long loggedInUserId) {
        List<StockmonExchange> stockmonExchanges =
                exchangeNoticeRepository.findExchangeNoticesByReceiverId(loggedInUserId);

        return new GetStockmonExchangeListResponseDto(stockmonExchanges);
    }

    @Transactional
    public AcceptStockmonExchangeResponseDto acceptExchange(AcceptStockmonExchangeRequestDto requestDto,
                                                            Long loggedInUserId) {
        ExchangeNotice notice = exchangeNoticeRepository.findByIdAndReceiverId(requestDto.getNoticeId(), loggedInUserId)
                .orElseThrow(NoTravelerException::new);

        TravelerStockmon travelerStockmon = travelerStockmonRepository
                .findByTravelerIdAndStockmonId(loggedInUserId,
                        requestDto.getTravelerStockmonId()).orElseThrow(NoStockmonException::new);
        double receiverAveragePrice = travelerStockmon.getStockmonAveragePrice();
        updateStockmonCount(travelerStockmon, -1L);
        upStockmonCount(notice.getSender().getId(), loggedInUserId, requestDto.getTravelerStockmonId(), notice.getSenderStockmon().getId(), notice.getStockAveragePrice(), receiverAveragePrice);


        exchangeNoticeRepository.delete(notice);

        return new AcceptStockmonExchangeResponseDto(true);
    }

    private void upStockmonCount(Long senderId, Long receiverId, Long senderReceiveStockmonId, Long recevierReceiveStockmonId, Double averagePrice, Double receiverAveragePrice) {
        TravelerStockmon senderReceiveTravelerStockmon = travelerStockmonRepository
                .findByTravelerIdAndStockmonId(senderId, senderReceiveStockmonId).orElseGet(() -> getNewTravelerStockmon(
                        travelerRepository.getReferenceById(senderId),
                        stockmonRepository.getReferenceById(senderReceiveStockmonId)
                ));
        TravelerStockmon receiverReceiveTravelerStockmon = travelerStockmonRepository
                .findByTravelerIdAndStockmonId(receiverId, recevierReceiveStockmonId).orElseGet(() -> getNewTravelerStockmon(
                        travelerRepository.getReferenceById(receiverId),
                        stockmonRepository.getReferenceById(recevierReceiveStockmonId)
                ));
        long senderCount = senderReceiveTravelerStockmon.getStockmonCount() + 1;
        long receiverCount = receiverReceiveTravelerStockmon.getStockmonCount() + 1;

        senderReceiveTravelerStockmon.setStockmonCount(senderCount);
        senderReceiveTravelerStockmon.setStockmonAveragePrice(
                (senderReceiveTravelerStockmon.getStockmonAveragePrice() * (senderCount - 1) +
                        receiverAveragePrice) / senderCount
        );

        receiverReceiveTravelerStockmon.setStockmonCount(receiverCount);
        receiverReceiveTravelerStockmon.setStockmonAveragePrice(
                (receiverAveragePrice * (receiverCount - 1) +
                averagePrice) / receiverCount
        );

        travelerStockmonRepository.save(senderReceiveTravelerStockmon);
        travelerStockmonRepository.save(receiverReceiveTravelerStockmon);

    }

    protected void updateStockmonCount(TravelerStockmon travelerStockmon, Long delta) {
        long newCount = travelerStockmon.getStockmonCount() + delta;
        if (newCount > 0) {
            travelerStockmon.setStockmonCount(newCount);
            travelerStockmon.setUpdatedAt(LocalDateTime.now());
            travelerStockmonRepository.save(travelerStockmon);
        } else {
            travelerStockmonRepository.delete(travelerStockmon);
        }
    }
    @Transactional
    protected void updateStockmonCount(Long travelerId, Long stockmonId, Long delta) {
        Optional<TravelerStockmon> optionalTravelerStockmon = travelerStockmonRepository
                .findByTravelerIdAndStockmonId(travelerId, stockmonId);

        if (optionalTravelerStockmon.isPresent()) {
            updateStockmonCount(optionalTravelerStockmon.get(), delta);
        } else if (delta > 0) {
            Traveler traveler = travelerRepository.findById(travelerId)
                    .orElseThrow(NoTravelerException::new);
            Stockmon stockmon = stockmonRepository.findById(stockmonId)
                    .orElseThrow(NoStockmonException::new);

            TravelerStockmon newTravelerStockmon = TravelerStockmon.builder()
                    .traveler(traveler)
                    .stockmon(stockmon)
                    .stockmonCount(delta)
                    .stockmonAveragePrice(0.0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now()).build();

            travelerStockmonRepository.save(newTravelerStockmon);
        } else {
            throw new InvalidExchangeRequestException();
        }

    }

    private TravelerStockmon getNewTravelerStockmon(Traveler traveler, Stockmon stockmon) {
        return TravelerStockmon.builder()
                .traveler(traveler)
                .stockmon(stockmon)
                .stockmonCount(0L)
                .stockmonAveragePrice(0.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
    }

    @Transactional
    public void rejectExchange(Long noticeId, Long loggedInUserId) {
        ExchangeNotice notice = exchangeNoticeRepository.findByIdAndReceiverId(noticeId, loggedInUserId)
                .orElseThrow(NoTravelerException::new);

        // 요청자의 스톡몬 개수를 다시 증가
        updateStockmonCount(notice.getSender().getId(), notice.getSenderStockmon().getId(), 1L);

        exchangeNoticeRepository.delete(notice);
    }

}