package com.pda.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "test", url = "http://localhost:8081")
public interface StockFeignClient {

    @GetMapping("/api/stock/test")
    ResponseEntity<String> getTest();

    @GetMapping("/api/stock/total-price/{code}")
    ResponseEntity<Long> getTotalPrice(@PathVariable String code);
}
