package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.StockTowerTimerRequestDto;
import com.pda.core.dto.StockTowerTimerResponseDto;
import com.pda.core.service.StockTowerTimerService;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Operation(summary = "스톡 타워 보상 요청")
    public ResponseEntity<SuccessResponse<StockTowerTimerResponseDto>> startStockTower(@RequestBody StockTowerTimerRequestDto request) {
        // TODO: 추후 로그인 로직 추가시 수정
        long travelerId = 1;
        StockTowerTimerResponseDto responseDto = stockTowerTimerService.saveStockTowerTimer(travelerId, request.getStockTowerId());

        return ResponseEntity.ok(SuccessResponse.<StockTowerTimerResponseDto>builder()
                .data(responseDto)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

}
