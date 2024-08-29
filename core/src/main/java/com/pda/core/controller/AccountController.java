package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.AccountCheckResponseDto;
import com.pda.core.dto.CreateAccountRequestDto;
import com.pda.core.dto.HasAccountResponseDto;
import com.pda.core.entity.Account;
import com.pda.core.entity.Traveler;
import com.pda.core.service.AccountService;
import com.pda.core.service.TravelerService;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AccountController {
    private final AccountService accountService;
    private final TravelerService travelerService;

    public AccountController(AccountService accountService, TravelerService travelerService) {
        this.accountService = accountService;
        this.travelerService = travelerService;
    }

    @PostMapping("/account")
    public ResponseEntity<SuccessResponse<Void>> createAccount(@RequestBody CreateAccountRequestDto request) {
        Account createdAccount = accountService.createAccount(request.getTravelerId());

        SuccessResponse<Void> response = SuccessResponse.<Void>builder()
                .data(null)
                .message("계좌 개설 성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/account")
    public ResponseEntity<SuccessResponse<AccountCheckResponseDto>> checkAccount() {
        // TODO: 추후 로그인 로직 추가시 수정
        long travelerId = 1;
        AccountCheckResponseDto checkResult = accountService.hasAccount(travelerId);

        SuccessResponse<AccountCheckResponseDto> response = SuccessResponse.<AccountCheckResponseDto>builder()
                .data(checkResult)
                .message("계좌 개설 조회 성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<HasAccountResponseDto>> getUserProfile() {
        // TODO: 추후 로그인 로직 추가시 수정
        long travelerId = 7;

        Traveler traveler = travelerService.getTravelerById(travelerId);
        HasAccountResponseDto profileDto = HasAccountResponseDto.fromTraveler(traveler);

        SuccessResponse<HasAccountResponseDto> response = SuccessResponse.<HasAccountResponseDto>builder()
                .data(profileDto)
                .message("성공")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok(response);
    }
}
