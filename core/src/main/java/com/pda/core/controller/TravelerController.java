package com.pda.core.controller;

import static com.pda.core.exception.ExceptionMessage.NO_TRAVELER;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.GetTravelerNicknameResponseDto;
import com.pda.core.dto.HasAccountResponseDto;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.service.TravelerService;
import java.util.Date;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "모험가 관련 API 모음")
@RequestMapping("/api/core/users")
public class TravelerController {
    private final TravelerService travelerService;

    public TravelerController(TravelerService travelerService){
        this.travelerService = travelerService;
    }

    @GetMapping
    @Operation(summary = "모험가 닉네임 검색 API")
    public ResponseEntity<SuccessResponse<GetTravelerNicknameResponseDto>> getTravelerByNickname(@RequestParam("name") String name) {
        GetTravelerNicknameResponseDto traveler = travelerService.findTravelerByNickname(name).orElseThrow(() -> new NoTravelerException(
                HttpStatus.BAD_REQUEST, NO_TRAVELER));

        return ResponseEntity.ok(SuccessResponse.<GetTravelerNicknameResponseDto>builder()
                .data(traveler)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

    @Operation(summary = "회원 정보 조회 API")
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
