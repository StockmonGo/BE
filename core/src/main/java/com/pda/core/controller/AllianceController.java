package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.alliance.GetTravelerAllianceListResponseDto;
import com.pda.core.service.AllianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/core/alliances")
public class AllianceController {
    private final AllianceService allianceService;

    @Operation(summary = "동맹 목록 조회 API")
    @GetMapping("")
    public ResponseEntity<SuccessResponse<List<GetTravelerAllianceListResponseDto>>> getTravelerAlliancesList(@RequestHeader(TRAVELER_ID) Long travelerId){
        List<GetTravelerAllianceListResponseDto> getTravelerAllianceListResponseDto = allianceService.getAlliances(travelerId);

        return ResponseEntity.ok(SuccessResponse.<List<GetTravelerAllianceListResponseDto>>builder()
                .data(getTravelerAllianceListResponseDto)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

    @Operation(summary = "동맹 추가 요청 APi")
    @PostMapping("/request")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<String>> postAllianceRequest(@RequestHeader(TRAVELER_ID) Long travelerId){
        System.out.println(travelerId);
        return ResponseEntity.ok(SuccessResponse.<String>builder()
                .message("성공")
                .data("동맹 요청 성공")
                .timestamp(new Date())
                .build());
    }


    @PostMapping("/accept")
    public ResponseEntity<SuccessResponse<String>> postAllianceAccept(){

        return ResponseEntity.ok(SuccessResponse.<String>builder()
                .data("accept")
                .message("성공")
                .timestamp(new Date())
                .build());
    }

    @PostMapping("/reject")
    public ResponseEntity<SuccessResponse<String>> postAllianceReject(){
        return ResponseEntity.ok(SuccessResponse.<String>builder()
                .data("reject")
                .message("성공")
                .timestamp(new Date())
                .build());
    }
}