package com.pda.core.controller;


import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.*;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.service.CustomUserDetailService;
import com.pda.core.service.TravelerService;
import java.util.Date;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

@RestController
@Tag(name = "모험가 관련 API 모음")
@RequiredArgsConstructor
@RequestMapping("/api/core/users")
public class TravelerController {
    private final TravelerService travelerService;
    private final CustomUserDetailService customUserDetailService;


    @Operation(summary = "모험가 닉네임 검색 API")
    @SecurityRequirement(name = JWT)
    @GetMapping
    public ResponseEntity<SuccessResponse<GetTravelerNicknameResponseDto>> getTravelerByNickname(@RequestParam("name") String name) {
        GetTravelerNicknameResponseDto traveler = travelerService.findTravelerByNickname(name).orElseThrow(NoTravelerException::new);

        return ResponseEntity.ok(SuccessResponse.<GetTravelerNicknameResponseDto>builder()
                .data(traveler)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }


    @GetMapping("/profile")
    @Operation(summary = "회원 정보 조회 API")
    @SecurityRequirement(name = JWT)
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

    @Operation(summary = "회원가입 API")
    @PostMapping("/join")
    public ResponseEntity<SuccessResponse<JoinResponseDto>> join(@RequestBody JoinRequestDto joinRequestDto) {
        return ResponseEntity.ok().body(
                SuccessResponse.<JoinResponseDto>builder()
                        .message("생성 완료")
                        .data(travelerService.createTraveler(joinRequestDto))
                        .timestamp(new Date())
                        .build());
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<?>> signin(@RequestBody SignInRequestDto signInRequestDto) {
        customUserDetailService.loadUserByUsername(signInRequestDto.getNickname());

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴 API")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<?>> withdraw(@RequestHeader(TRAVELER_ID) Long travelerId) {
       travelerService.remove(travelerId);
       return ResponseEntity.ok().body(SuccessResponse.<Object>builder()
                       .data(null)
                       .data(null)
                       .message("회원 탈퇴 완료")
                       .timestamp(new Date())
               .build());
    }

}
