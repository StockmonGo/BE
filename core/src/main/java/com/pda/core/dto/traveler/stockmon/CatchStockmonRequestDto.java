package com.pda.core.dto.traveler.stockmon;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatchStockmonRequestDto {

    private int worldId;
    private Long stockmonId;
    private Double latitude;
    private Double longitude;
    private Long usedStockballs;

}
