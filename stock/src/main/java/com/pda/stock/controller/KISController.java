package com.pda.stock.controller;


import com.pda.commons.dto.SuccessResponse;
import com.pda.stock.dto.StockChartResponseDto;
import com.pda.stock.service.KISService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class KISController {

    private final KISService kisService;

    @GetMapping("/total-price/{code}")
    public ResponseEntity<Long> getStockTotalPrice(@PathVariable("code") String code) {
        return ResponseEntity.ok().body(kisService.getStockTotalPrice(code));
    }

    @GetMapping("/current-price/{code}")
    public ResponseEntity<Long> getStockCurrentPrice(@PathVariable("code") String code){
        return ResponseEntity.ok().body(kisService.getStockCurrentPrice(code));
    }


    @GetMapping("/chart/{code}")
    public ResponseEntity<SuccessResponse<List<StockChartResponseDto>>> getStockChart(@PathVariable("code") String code) {
        return ResponseEntity.ok(
                SuccessResponse.<List<StockChartResponseDto>>builder()
                        .data(kisService.getStockChart(code))
                        .message("차트 데이터 조회")
                        .timestamp(new Date())
                        .build());
    }

}
