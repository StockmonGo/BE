package com.pda.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-feign")
public interface StockFeignClient {

    @GetMapping("/test")
    ResponseEntity<String> getTest();

    @GetMapping("/total-price/{code}")
    ResponseEntity<Long> getTotalPrice(@PathVariable("code") String code);

    @GetMapping("/current-price/{code}")
    ResponseEntity<Long> getCurrentPrice(@PathVariable("code") String code);

    @GetMapping("/closed-price/{code}")
    ResponseEntity<Long> getClosedPrice(@PathVariable("code") String code);
}
