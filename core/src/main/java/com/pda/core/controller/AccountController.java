package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.AccountCheckResponseDto;
import com.pda.core.dto.AccountStockResponseDto;
import com.pda.core.dto.ChangeRealStockRequestDto;
import com.pda.core.service.AccountService;
import java.util.Date;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

@RestController
@Tag(name = "계좌 관련 API 모음")
@RequestMapping("/api/core")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @Operation(summary = "계좌 개설 API")
    @SecurityRequirement(name = JWT)
    @PostMapping("/users/account")
    public ResponseEntity<SuccessResponse<AccountStockResponseDto>> createAccount(@RequestHeader(TRAVELER_ID) Long travelerId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.<AccountStockResponseDto>builder()
                .data(accountService.createAccount(travelerId))
                .message("계좌 개설 성공")
                .timestamp(new Date())
                .build());
    }

    @Operation(summary = "계좌 개설 여부 API")
    @SecurityRequirement(name = JWT)
    @GetMapping("/users/account")
    public ResponseEntity<SuccessResponse<AccountCheckResponseDto>> checkAccount(@RequestHeader(TRAVELER_ID) Long travelerId) {
        AccountCheckResponseDto checkResult = accountService.hasAccount(travelerId);

        SuccessResponse<AccountCheckResponseDto> response = SuccessResponse.<AccountCheckResponseDto>builder()
                .data(checkResult)
                .message("계좌 개설 조회 성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "실제 주식 교환 요청 API")
    @SecurityRequirement(name = JWT)
    @PostMapping("/stocks/exchange")
    public ResponseEntity<SuccessResponse<Object>> checkOut(@RequestHeader(TRAVELER_ID) Long travelerId, @RequestBody ChangeRealStockRequestDto changeRealStockRequestDto) {
        accountService.changeRealStock(travelerId, changeRealStockRequestDto.getStockCode());
        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("주식 획득")
                        .timestamp(new Date())
                .data(null).build());
    }
}
