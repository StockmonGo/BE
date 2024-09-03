package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.AccountCheckResponseDto;
import com.pda.core.entity.Account;
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
@RequestMapping("/api/core/users/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @Operation(summary = "계좌 개설 API")
    @SecurityRequirement(name = JWT)
    @PostMapping
    public ResponseEntity<SuccessResponse<Void>> createAccount(@RequestHeader(TRAVELER_ID) Long travelerId) {
        Account createdAccount = accountService.createAccount(travelerId);

        SuccessResponse<Void> response = SuccessResponse.<Void>builder()
                .data(null)
                .message("계좌 개설 성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "계좌 개설 여부 API")
    @SecurityRequirement(name = JWT)
    @GetMapping
    public ResponseEntity<SuccessResponse<AccountCheckResponseDto>> checkAccount(@RequestHeader(TRAVELER_ID) Long travelerId) {
        AccountCheckResponseDto checkResult = accountService.hasAccount(travelerId);

        SuccessResponse<AccountCheckResponseDto> response = SuccessResponse.<AccountCheckResponseDto>builder()
                .data(checkResult)
                .message("계좌 개설 조회 성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }
}
