package com.pda.core.dto.alliance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTravelerAllianceListResponseDto {
    private Long travelerId;
    private String nickName;

    public static GetTravelerAllianceListResponseDto fromEntity(Long travelerId, String nickName) {
        return new GetTravelerAllianceListResponseDto(travelerId, nickName);
    }
}