package com.pda.core.dto;

import com.pda.core.entity.Traveler;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetTravelerNicknameResponseDto {

    private Long id;
    private String nickname;

    public static GetTravelerNicknameResponseDto fromEntity(Traveler traveler) {
        return GetTravelerNicknameResponseDto.builder()
                .id(traveler.getId())
                .nickname(traveler.getNickname())
                .build();
    }

}
