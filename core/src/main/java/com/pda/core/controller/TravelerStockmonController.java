package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.GetStockmonListResponseDto;
import com.pda.core.dto.GetWorldStockmonsResponseDto;
import com.pda.core.service.TravelerStockmonService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Date;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "모험가_스톡몬 관련 API 모음")
@RequestMapping("/api/core")
public class TravelerStockmonController {

    private final TravelerStockmonService travelerStockmonService;

    public TravelerStockmonController(TravelerStockmonService travelerStockmonService) {
        this.travelerStockmonService = travelerStockmonService;
    }

    @GetMapping("/stockmons")
    @Operation(summary = "모험가_스톡몬 목록 조회 API")
    public ResponseEntity<SuccessResponse<GetStockmonListResponseDto>> getStockmons() {
        // TODO: 추후 로그인 로직 추가시 수정
        long travelerId = 1;
        GetStockmonListResponseDto stockmons = travelerStockmonService.getStockmonsByTravelerId(travelerId);
        return ResponseEntity.ok(SuccessResponse.<GetStockmonListResponseDto>builder()
                .data(stockmons)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

}