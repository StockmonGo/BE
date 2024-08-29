package com.pda.core.service;


import com.pda.core.dto.GetWorldStockmonsRequestDto;
import com.pda.core.entity.StockTower;
import com.pda.core.repository.StockTowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockTowerService {

    private final StockTowerRepository stockTowerRepository;

    public List<StockTower> getNearStockTowers(GetWorldStockmonsRequestDto getWorldStockmonsRequestDto) {

        return stockTowerRepository.findAll();
    }
}
