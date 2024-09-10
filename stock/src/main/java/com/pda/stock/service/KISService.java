package com.pda.stock.service;


import com.pda.stock.client.KISFeignClient;
import com.pda.stock.dto.StockChartDto;
import com.pda.stock.dto.StockChartResponseDto;
import com.pda.stock.dto.StockInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KISService {

    @Value(value = "${stock.app.key}")
    private String APP_KEY;

    @Value(value = "${stock.app.secret}")
    private String APP_SECRET;

    @Value(value = "${stock.access.token}")
    private String TOKEN;

    private final KISFeignClient kisFeignClient;

    private final static long MILLION = 1_000_000;

    @Cacheable(value = "TotalPrice", key = "#code", cacheManager = "redisCacheManager")
    public long getStockTotalPrice(String code) {
        StockInfoDto stockInfo = kisFeignClient.getStockInfo(code, "Bearer " + TOKEN, APP_KEY, APP_SECRET);
        getCacheCurrentPrice(code, stockInfo.getOutput().getCurrentPrice());
        return Long.parseLong(stockInfo.getOutput().getTotalPrice()) * MILLION;
    }

    @Cacheable(value = "CurrentPrice", key = "#code", cacheManager = "redisCacheManager")
    public long getStockCurrentPrice(String code) {
        StockInfoDto stockInfo = kisFeignClient.getStockInfo(code, "Bearer " + TOKEN, APP_KEY, APP_SECRET);
        getCacheTotalPrice(code, stockInfo.getOutput().getTotalPrice());
        return Long.parseLong(stockInfo.getOutput().getCurrentPrice());
    }

    @Cacheable(value = "CurrentPrice", key = "#code", cacheManager = "redisCacheManager")
    public long getCacheCurrentPrice(String code, String currentPrice) {
        return Long.parseLong(currentPrice);
    }

    @Cacheable(value = "TotalPrice", key = "#code", cacheManager = "redisCacheManager")
    public long getCacheTotalPrice(String code, String totalPrice) {
        return Long.parseLong(totalPrice);
    }

    @Cacheable(value = "StockChart", key = "#code", cacheManager = "redisCacheManager")
    public List<StockChartResponseDto> getStockChart(String code) {
        StockChartDto stockChartDto = kisFeignClient.getMonthChart(code, "Bearer " + TOKEN, APP_KEY, APP_SECRET);
        List<StockChartResponseDto> stockChartResponseDtos = new ArrayList<>();
        for(int i = 5; i >= 0; i--) {
            StockChartDto.Output output = stockChartDto.getOutput()[i];
            String date = output.getDate();

            stockChartResponseDtos.add(StockChartResponseDto.builder()
                    .time(date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6))
                    .value(Long.parseLong(output.getValue()))
                    .build());

        }
        return stockChartResponseDtos;
    }

    @Cacheable(value = "ClosedChart", key = "#code", cacheManager = "redisCacheManager")
    public long getStockClosedPrice(String code) {
        return Long.parseLong(kisFeignClient.getMonthChart(code,"Bearer "+TOKEN, APP_KEY, APP_SECRET).getOutput()[0].getValue());
    }

}
