package com.pda.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorldStockmonDto {
    private final long id;
    private final long stockmon_id;
    private final double latitude;
    private final double longitude;
}
