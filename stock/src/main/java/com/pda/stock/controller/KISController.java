package com.pda.stock.controller;


import com.pda.stock.service.KISService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class KISController {

    private final KISService kisService;

    @GetMapping("/total-price/{code}")
    public ResponseEntity<Long> getStockTotalPrice(@PathVariable String code) {
        return ResponseEntity.ok().body(kisService.getStockTotalPrice(code));
    }

}
