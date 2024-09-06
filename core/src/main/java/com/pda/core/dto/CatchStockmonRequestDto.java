package com.pda.core.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatchStockmonRequestDto {

    private int worldId;
    private Long stockmonId;
    private Long usedStockballs;

}
