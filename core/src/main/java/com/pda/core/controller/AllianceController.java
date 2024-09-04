package com.pda.core.controller;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.alliances.GetTravelerAlliancesListResponseDto;
import com.pda.core.service.AllianceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/core/alliances")
public class AllianceController {
    private final AllianceService allianceService;

    @GetMapping("")
    public ResponseEntity<SuccessResponse<List<GetTravelerAlliancesListResponseDto>>> getTravelerAlliancesList(@Valid Long id){
        List<GetTravelerAlliancesListResponseDto> getTravelerAlliancesListResponseDto = allianceService.getAlliances(1L);
//        List<String> getTravelerAlliancesListResponseDto = allianceService.getAlliances(1L);

        return ResponseEntity.ok(SuccessResponse.<List<GetTravelerAlliancesListResponseDto>>builder()
                .data(getTravelerAlliancesListResponseDto)
                .message("성공")
                .timestamp(new Date())
                .build()
        );
    }

    @PostMapping("/request")
    public ResponseEntity<SuccessResponse<String>> postAllianceRequest(){
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


