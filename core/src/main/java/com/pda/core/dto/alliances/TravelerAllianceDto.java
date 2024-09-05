package com.pda.core.dto.alliances;

import com.pda.core.entity.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TravelerAllianceDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Traveler traveler;

    private Alliance alliance;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public TravelerAlliance toEntity(){
        return TravelerAlliance.builder()
                .id(id)
                .traveler(traveler)
                .alliance(alliance)
                .build();
    }
}



