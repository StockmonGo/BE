package com.pda.core.dto;


import com.pda.core.entity.StockTower;
import com.pda.core.entity.Stockmon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetWorldStockmonsResponseDto {

    private final List<WorldStockmonDto> stockmons;
    private final List<StockTower> stockTowers;
}
