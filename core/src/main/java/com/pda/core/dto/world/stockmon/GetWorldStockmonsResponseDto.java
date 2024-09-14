package com.pda.core.dto.world.stockmon;


import com.pda.core.entity.world.StockTower;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetWorldStockmonsResponseDto {

    private final List<WorldStockmonDto> stockmons;
    private final List<StockTower> stockTowers;
}
