package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.GetStockmonDetailResponseDto;
import com.pda.core.dto.GetStockmonListResponseDto;
import com.pda.core.dto.GetTravelerStockballsResponseDto;
import com.pda.core.service.StockmonService;
import com.pda.core.service.TravelerService;
import com.pda.core.service.TravelerStockmonService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Date;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

@RestController
@Tag(name = "모험가_스톡몬 관련 API 모음")
@RequestMapping("/api/core")
public class TravelerStockmonController {

    private final TravelerStockmonService travelerStockmonService;
    private final StockmonService stockmonService;
    private final TravelerService travelerService;

    public TravelerStockmonController(TravelerStockmonService travelerStockmonService,
                                      StockmonService stockmonService, TravelerService travelerService) {
        this.travelerStockmonService = travelerStockmonService;
        this.stockmonService = stockmonService;
        this.travelerService = travelerService;
    }

    @GetMapping("/stockmons")
    @Operation(summary = "모험가_스톡몬 목록 조회 API")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<GetStockmonListResponseDto>> getStockmons(
            @RequestHeader(TRAVELER_ID) Long travelerId) {
        GetStockmonListResponseDto stockmons = travelerStockmonService.getStockmonsByTravelerId(travelerId);
        return ResponseEntity.ok(SuccessResponse.<GetStockmonListResponseDto>builder()
                .data(stockmons)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

    @GetMapping("/stockmons/{id}")
    @Operation(summary = "스톡몬 상세 정보 조회")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<GetStockmonDetailResponseDto>> getStockmonDetail(@PathVariable("id") Long id,
                                                                                           @RequestHeader(TRAVELER_ID) Long travelerId) {
        GetStockmonDetailResponseDto stockmonDetail = stockmonService.getStockmonDetail(id, travelerId);
        return ResponseEntity.ok(SuccessResponse.<GetStockmonDetailResponseDto>builder()
                .data(stockmonDetail)
                .message("스톡몬 상세 조회 성공")
                .timestamp(new Date())
                .build()
        );
    }

    @GetMapping("/stockballs")
    @Operation(summary = "스톡볼 개수 조회")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<GetTravelerStockballsResponseDto>> getStockballs(@RequestHeader(TRAVELER_ID) Long travelerId) {

        GetTravelerStockballsResponseDto stockballs = travelerService.getTravelerStockballsById(travelerId);
        return ResponseEntity.ok(SuccessResponse.<GetTravelerStockballsResponseDto>builder()
                .data(stockballs)
                .message("스톡볼 개수 조회 성공")
                .timestamp(new Date())
                .build()
        );

    }

}

