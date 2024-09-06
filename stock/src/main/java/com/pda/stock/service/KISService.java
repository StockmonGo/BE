package com.pda.stock.service;


import com.pda.stock.client.KISFeignClient;
import com.pda.stock.dto.StockChartDto;
import com.pda.stock.dto.StockChartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public long getStockTotalPrice(String code) {
        return Long.parseLong(kisFeignClient.getStockInfo(code, "Bearer " + TOKEN, APP_KEY, APP_SECRET).getOutput().getTotalPrice()) * MILLION;
    }

    public long getStockCurrentPrice(String code) {
        return Long.parseLong(kisFeignClient.getStockInfo(code, "Bearer " + TOKEN, APP_KEY, APP_SECRET).getOutput().getCurrentPrice());
    }

    public List<StockChartResponseDto> getStockChart(String code) {
        StockChartDto stockChartDto = kisFeignClient.getMonthChart(code, "Bearer " + TOKEN, APP_KEY, APP_SECRET);
        List<StockChartResponseDto> stockChartResponseDtos = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            StockChartDto.Output output = stockChartDto.getOutput()[i];
            String date = output.getDate();

            stockChartResponseDtos.add(StockChartResponseDto.builder()
                    .date(date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6))
                    .value(Long.parseLong(output.getValue()))
                    .build());

        }
        return stockChartResponseDtos;
    }

}
