package com.pda.core.controller;

import com.pda.core.dto.GetStockmonListResponseDto;
import com.pda.core.service.TravelerStockmonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TravelerStockmonController {

    private final TravelerStockmonService travelerStockmonService;

    public TravelerStockmonController(TravelerStockmonService travelerStockmonService) {
        this.travelerStockmonService = travelerStockmonService;
    }

    @GetMapping("/stockmons")
    public GetStockmonListResponseDto getStockmons() {
        // TODO: 추후 로그인 로직 추가시 수정
        long travelerId = 1;
        return travelerStockmonService.getStockmonsByTravelerId(travelerId);
    }
}