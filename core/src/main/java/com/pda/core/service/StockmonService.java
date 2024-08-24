package com.pda.core.service;

import com.pda.core.entity.Stockmon;
import com.pda.core.repository.StockmonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockmonService {

    private final StockmonRepository stockmonRepository;


    public Stockmon getStockmon(long id) {
        return stockmonRepository.findById(id).orElseThrow();
    }

}
