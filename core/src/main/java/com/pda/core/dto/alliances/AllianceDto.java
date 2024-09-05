package com.pda.core.dto.alliances;

import com.pda.core.entity.Alliance;
import com.pda.core.entity.TravelerAlliance;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllianceDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long traverId;

    private List<TravelerAlliance> travelerAlliances;

    public Alliance toEntity(){
        return Alliance.builder()
                .id(traverId)
                .travelerAlliances(travelerAlliances)
                .build();
    }
}
