package com.pda.core.service;


import com.pda.core.dto.GetWorldStockmonsRequestDto;
import com.pda.core.entity.StockTower;
import com.pda.core.repository.StockTowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.pda.core.utils.WorldConstants.*;

@Service
@RequiredArgsConstructor
public class StockTowerService {

    private final StockTowerRepository stockTowerRepository;

    public List<StockTower> getNearStockTowers(GetWorldStockmonsRequestDto getWorldStockmonsRequestDto) {
        BigDecimal stockmonLatitudeDiff = new BigDecimal(STOCKMON_LATITUDE_DIFF).multiply(BigDecimal.valueOf(3));
        BigDecimal stockmonLongitudeDiff = new BigDecimal(STOCKMON_LONGITUDE_DIFF).multiply(BigDecimal.valueOf(3));

        double minLatitude = getWorldStockmonsRequestDto.getLatitude().subtract(stockmonLatitudeDiff).doubleValue();
        double minLongitude = getWorldStockmonsRequestDto.getLongitude().subtract(stockmonLongitudeDiff).doubleValue();
        double maxLatitude = getWorldStockmonsRequestDto.getLatitude().add(stockmonLatitudeDiff).doubleValue();
        double maxLongitude = getWorldStockmonsRequestDto.getLongitude().add(stockmonLongitudeDiff).doubleValue();

        return stockTowerRepository.findByLocation(minLatitude, minLongitude, maxLatitude, maxLongitude);
    }
}
