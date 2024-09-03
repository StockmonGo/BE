package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.StockTowerTimerRequestDto;
import com.pda.core.dto.StockTowerTimerResponseDto;
import com.pda.core.service.StockTowerTimerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/core/stocktowers")
public class StockTowerTimerController {

    private final StockTowerTimerService stockTowerTimerService;

    public StockTowerTimerController(StockTowerTimerService stockTowerTimerService){
        this.stockTowerTimerService = stockTowerTimerService;
    }
    @PostMapping
    public ResponseEntity<SuccessResponse<StockTowerTimerResponseDto>> useTower(@Valid @RequestBody StockTowerTimerRequestDto request) {

        long travelerId = 1;
        try {
            StockTowerTimerResponseDto response = stockTowerTimerService.useTower( travelerId, request.getStockTowerId());
            return ResponseEntity.ok(SuccessResponse
                    .<StockTowerTimerResponseDto>builder()
                    .data(response)
                    .message("성공")
                    .timestamp(new Date())
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(SuccessResponse
                    .<StockTowerTimerResponseDto>builder()
                    .message(e.getMessage())
                    .timestamp(new Date())
                    .build());
        }
    }
}
