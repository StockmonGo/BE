package com.pda.core.service;

import com.pda.core.dto.AcceptStockmonExchangeRequestDto;
import com.pda.core.dto.AcceptStockmonExchangeResponseDto;
import com.pda.core.dto.GetStockmonExchangeListResponseDto;
import com.pda.core.dto.GetStockmonExchangeListResponseDto.StockmonExchange;
import com.pda.core.dto.StockmonExchangeNoticeRequestDto;
import com.pda.core.entity.ExchangeNotice;
import com.pda.core.entity.Stockmon;
import com.pda.core.entity.Traveler;
import com.pda.core.entity.TravelerStockmon;
import com.pda.core.exception.InvalidExchangeRequestException;
import com.pda.core.exception.NoStockmonException;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.ExchangeNoticeRepository;
import com.pda.core.repository.StockmonRepository;
import com.pda.core.repository.TravelerRepository;
import com.pda.core.repository.TravelerStockmonRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ExchangeNotice createExchangeNotice(StockmonExchangeNoticeRequestDto requestDto, Long loggedInUserId) {
        ExchangeNotice exchangeNotice = requestDto.toEntity(loggedInUserId);
        return exchangeNoticeRepository.save(exchangeNotice);
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

        updateStockmonCount(loggedInUserId, requestDto.getTravelerStockmonId(), -1L);

        updateStockmonCount(loggedInUserId, notice.getSenderStockmon().getId(), 1L);

        updateStockmonCount(notice.getSender().getId(), notice.getSenderStockmon().getId(), -1L);

        updateStockmonCount(notice.getSender().getId(), requestDto.getTravelerStockmonId(), 1L);

        return new AcceptStockmonExchangeResponseDto(true);
    }

    private void updateStockmonCount(Long travelerId, Long stockmonId, Long delta) {
        Optional<TravelerStockmon> optionalTravelerStockmon = travelerStockmonRepository
                .findByTravelerIdAndStockmonId(travelerId, stockmonId);

        if (optionalTravelerStockmon.isPresent()) {
            TravelerStockmon travelerStockmon = optionalTravelerStockmon.get();
            Long newCount = travelerStockmon.getStockmonCount() + delta;
            if (newCount > 0) {
                travelerStockmon.setStockmonCount(newCount);
                travelerStockmon.setUpdatedAt(LocalDateTime.now());
                travelerStockmonRepository.save(travelerStockmon);
            } else {
                travelerStockmonRepository.delete(travelerStockmon);
            }
        } else if (delta > 0) {
            Traveler traveler = travelerRepository.findById(travelerId)
                    .orElseThrow(NoTravelerException::new);
            Stockmon stockmon = stockmonRepository.findById(stockmonId)
                    .orElseThrow(NoStockmonException::new);

            //TODO: SETTER 관련 논의 필요, 없던 스톡몬이면 새로 생성하는 부분
            TravelerStockmon newTravelerStockmon = new TravelerStockmon();
            newTravelerStockmon.setTraveler(traveler);
            newTravelerStockmon.setStockmon(stockmon);
            newTravelerStockmon.setStockmonCount(delta);
            newTravelerStockmon.setStockmonAveragePrice(0.0);
            newTravelerStockmon.setCreatedAt(LocalDateTime.now());
            newTravelerStockmon.setUpdatedAt(LocalDateTime.now());
            travelerStockmonRepository.save(newTravelerStockmon);
        } else {
            throw new InvalidExchangeRequestException();
        }
    }

}