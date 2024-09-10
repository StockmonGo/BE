package com.pda.core.service;


import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.GetTravelerStockListResponseDto;
import com.pda.core.dto.TravelerStockDto;
import com.pda.core.exception.NoStockException;
import com.pda.core.repository.AccountStockRepository;
import com.pda.core.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
