package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;

import com.pda.core.dto.alliance.GetTravelerAllianceListResponseDto;
import com.pda.core.dto.alliance_notice.AllianceNoticeIdDto;
import com.pda.core.dto.alliance_notice.GetAllianceNoticeListResponseDto;
import com.pda.core.dto.alliances.AllianceIdDto;
import com.pda.core.service.AllianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import static com.pda.core.config.SwaggerConfig.JWT;
import static com.pda.core.config.SwaggerConfig.TRAVELER_ID;

@RestController
@RequiredArgsConstructor
@Tag(name = "동맹 관련 API 모음")
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

    @Operation(summary = "동맹 요청 목록 조회 API")
    @GetMapping("/request")
    public ResponseEntity<SuccessResponse<List<GetAllianceNoticeListResponseDto>>> getAllianceRequest(@RequestHeader(TRAVELER_ID) Long travelerId){
        List<GetAllianceNoticeListResponseDto> getTravelerAllianceListResponseDto = allianceService.getAllianceNoticeList(travelerId);

        return ResponseEntity.ok(SuccessResponse.<List<GetAllianceNoticeListResponseDto>>builder()
                .data(getTravelerAllianceListResponseDto)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

    @Operation(summary = "동맹 추가 요청 API")
    @PostMapping("/request")
    @SecurityRequirement(name = JWT)
    public ResponseEntity<SuccessResponse<String>> postAllianceRequest(@RequestHeader(TRAVELER_ID) Long travelerId, @RequestBody AllianceIdDto targetTravelerId){
        Boolean isSuccess =allianceService.createAllianceRequest(travelerId,targetTravelerId.getTargetTravelerId());
        return ResponseEntity.ok(SuccessResponse.<String>builder()
                .message(isSuccess+"")
                .data("동맹 요청 성공")
                .timestamp(new Date())
                .build());
    }


    @Operation(summary = "동맹 수락 API")
    @PostMapping("/accept")
    public ResponseEntity<SuccessResponse<String>> postAllianceAccept(@RequestHeader(TRAVELER_ID) Long travelerId, @RequestBody AllianceNoticeIdDto noticeId){
        allianceService.acceptAlliance(travelerId,noticeId.getNoticeId());
        return ResponseEntity.ok(SuccessResponse.<String>builder()
                .data("accept")
                .message("성공")
                .timestamp(new Date())
                .build());
    }

    @Operation(summary = "동맹 거절 API")
    @PostMapping("/reject")
    public ResponseEntity<SuccessResponse<String>> postAllianceReject(@RequestHeader(TRAVELER_ID) Long travelerId, @RequestBody AllianceNoticeIdDto noticeId){
        allianceService.rejectAlliance(noticeId.getNoticeId());
        return ResponseEntity.ok(SuccessResponse.<String>builder()
                .data("reject")
                .message("성공")
                .timestamp(new Date())
                .build());
    }
}