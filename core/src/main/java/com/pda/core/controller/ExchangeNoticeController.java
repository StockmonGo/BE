package com.pda.core.controller;

import static com.pda.core.config.SwaggerConfig.JWT;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.StockmonExchangeNoticeRequestDto;
import com.pda.core.service.ExchangeNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<SuccessResponse<Void>> createExchangeNotice(@RequestBody StockmonExchangeNoticeRequestDto requestDto) {
        // TODO: 추후 로그인 로직 추가시 수정
        long loggedInUserId = 1L;

        exchangeNoticeService.createExchangeNotice(requestDto, loggedInUserId);

        SuccessResponse<Void> response = SuccessResponse.<Void>builder()
                .data(null)
                .message("요청 완료")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }

}
