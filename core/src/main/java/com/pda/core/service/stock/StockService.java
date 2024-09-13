package com.pda.core.service.stock;


import com.pda.core.dto.traveler.account.GetTravelerStockListResponseDto;
import com.pda.core.dto.traveler.account.TravelerStockDto;
import com.pda.core.repository.traveler.AccountStockRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final AccountStockRepository accountStockRepository;

    public StockService(AccountStockRepository accountStockRepository){
        this.accountStockRepository = accountStockRepository;
    }


    public GetTravelerStockListResponseDto getTravelerStocks(Long accountId) {
        List<TravelerStockDto> stocks = accountStockRepository.findStocksByTravelerId(accountId);
        return new GetTravelerStockListResponseDto(stocks);
    }

}
