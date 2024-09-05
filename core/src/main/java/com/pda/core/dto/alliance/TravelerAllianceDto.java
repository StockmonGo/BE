package com.pda.core.dto.alliance;

import com.pda.core.entity.TravelerAlliance;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TravelerAllianceDto {

    private List<TravelerAlliance> travelerAlliances;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}