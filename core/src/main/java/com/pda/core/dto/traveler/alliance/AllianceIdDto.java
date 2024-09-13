package com.pda.core.dto.traveler.alliance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllianceIdDto {

    private Long targetTravelerId;

}