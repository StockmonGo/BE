package com.pda.core.controller;

import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.GetStockmonExchangeListResponseDto;
import com.pda.core.dto.StockmonExchangeNoticeRequestDto;
import com.pda.core.service.ExchangeNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "스톡몬 교환 관련 API 모음")
@RequestMapping("/api/core/stockmons/exchange/request")
public class ExchangeNoticeController {

    private final ExchangeNoticeService exchangeNoticeService;

    public ExchangeNoticeController(ExchangeNoticeService exchangeNoticeService) {
        this.exchangeNoticeService = exchangeNoticeService;
    }

    @PostMapping
    @Operation(summary = "스톡몬 교환 요청 API")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<Void>> createExchangeNotice(@RequestHeader(TRAVELER_ID) Long travelerId, @RequestBody StockmonExchangeNoticeRequestDto requestDto) {

        exchangeNoticeService.createExchangeNotice(requestDto, travelerId);

        SuccessResponse<Void> response = SuccessResponse.<Void>builder()
                .data(null)
                .message("스톡몬 요청 완료")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "스톡몬 교환 알림 목록 조회 API")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<SuccessResponse<GetStockmonExchangeListResponseDto>> getExchangeNotices(@RequestHeader(TRAVELER_ID) Long travelerId) {

        GetStockmonExchangeListResponseDto exchangeListDto = exchangeNoticeService.getExchangeNotices(travelerId);

        SuccessResponse<GetStockmonExchangeListResponseDto> response = SuccessResponse.<GetStockmonExchangeListResponseDto>builder()
                .data(exchangeListDto)
                .message("성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }

}
