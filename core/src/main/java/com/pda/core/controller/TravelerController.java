package com.pda.core.controller;

import static com.pda.core.exception.ExceptionMessage.NO_TRAVELER;

import com.pda.commons.dto.SuccessResponse;
import com.pda.core.dto.GetTravelerNicknameResponseDto;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.service.TravelerService;
import java.util.Date;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TravelerController {
    private final TravelerService travelerService;

    public TravelerController(TravelerService travelerService){
        this.travelerService = travelerService;
    }
    @GetMapping("/users")
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

}
