package com.pda.core.service;

import com.pda.core.dto.StockmonExchangeNoticeRequestDto;
import com.pda.core.entity.ExchangeNotice;
import com.pda.core.repository.ExchangeNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeNoticeService {

    private final ExchangeNoticeRepository exchangeNoticeRepository;

    @Autowired
    public ExchangeNoticeService(ExchangeNoticeRepository exchangeNoticeRepository) {
        this.exchangeNoticeRepository = exchangeNoticeRepository;
    }

    public ExchangeNotice createExchangeNotice(StockmonExchangeNoticeRequestDto requestDto, Long loggedInUserId) {
        ExchangeNotice exchangeNotice = requestDto.toEntity(loggedInUserId);
        return exchangeNoticeRepository.save(exchangeNotice);
    }

}
