package com.pda.core.dto.world.stockmon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorldStockmonDto {
    private final long id;
    private final long stockmonId;
    private final double latitude;
    private final double longitude;
}
