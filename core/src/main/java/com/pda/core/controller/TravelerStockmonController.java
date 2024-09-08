package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.*;
import com.pda.core.service.StockmonService;
import com.pda.core.service.TravelerService;
import com.pda.core.service.TravelerStockmonService;
import com.pda.core.service.YardService;
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
    private final YardService yardService;

    public TravelerStockmonController(TravelerStockmonService travelerStockmonService,
                                      StockmonService stockmonService, TravelerService travelerService, YardService yardService) {
        this.travelerStockmonService = travelerStockmonService;
        this.stockmonService = stockmonService;
        this.travelerService = travelerService;
        this.yardService = yardService;
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

    @PostMapping("/stockmons")
    @Operation(summary = "스톡몬 포획")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<CatchStockmonResponseDto>> catchStockmon(@RequestHeader(TRAVELER_ID) Long travelerId, @RequestBody CatchStockmonRequestDto catchStockmonRequestDto) {
        CatchStockmonResponseDto catchStockmonResponseDto = travelerStockmonService.updateCatchStockmon(travelerId, catchStockmonRequestDto);

        return ResponseEntity.ok()
                .body(SuccessResponse.<CatchStockmonResponseDto>builder()
                        .data(catchStockmonResponseDto)
                        .message("포획 성공")
                        .timestamp(new Date())
                        .build());
    }

    @GetMapping("/stockmons/yard")
    @Operation(summary = "스톡몬 마당 조회 API")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<SuccessResponse<YardStockmonResponseDto>> getYardStockmons(
            @RequestHeader(TRAVELER_ID) Long travelerId) {

        YardStockmonResponseDto yardStockmonResponseDto = yardService.getYardByTravelerId(travelerId);

        SuccessResponse<YardStockmonResponseDto> response = SuccessResponse.<YardStockmonResponseDto>builder()
                .data(yardStockmonResponseDto)
                .message("성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }

}

