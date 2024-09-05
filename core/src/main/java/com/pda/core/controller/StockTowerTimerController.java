package com.pda.core.controller;

import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.StockTowerTimerDetailResponseDto;
import com.pda.core.dto.StockTowerTimerRequestDto;
import com.pda.core.dto.StockTowerTimerResponseDto;
import com.pda.core.service.StockTowerTimerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "스톡타워 관련 API 모음")
@RequestMapping("api/core/stocktowers")
public class StockTowerTimerController {

    private final StockTowerTimerService stockTowerTimerService;

    public StockTowerTimerController(StockTowerTimerService stockTowerTimerService) {
        this.stockTowerTimerService = stockTowerTimerService;
    }

    @PostMapping
    @Operation(summary = "스톡타워 보상 요청 API")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<StockTowerTimerResponseDto>> useTower( @RequestHeader(TRAVELER_ID) Long travelerId,
            @Valid @RequestBody StockTowerTimerRequestDto request) {
        try {
            StockTowerTimerResponseDto response = stockTowerTimerService.useTower(travelerId,
                    request.getStockTowerId());
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

    @GetMapping("/{id}")
    @Operation(summary = "스톡타워 상세 조회 API")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<StockTowerTimerDetailResponseDto>> getStockTowerDetail(@PathVariable("id") Long id) {
        StockTowerTimerDetailResponseDto detail = stockTowerTimerService.getStockTowerDetail(id);
        return ResponseEntity.ok(SuccessResponse.<StockTowerTimerDetailResponseDto>builder()
                .data(detail)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

}

