package com.pda.stock.service;


import com.pda.stock.client.KISFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
