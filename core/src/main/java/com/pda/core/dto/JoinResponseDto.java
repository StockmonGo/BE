package com.pda.core.dto;


import com.pda.core.entity.Traveler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinResponseDto {
    private final String nickname;

    public static JoinResponseDto fromEntity(Traveler traveler) {
        return new JoinResponseDto(traveler.getNickname());
    }
}
