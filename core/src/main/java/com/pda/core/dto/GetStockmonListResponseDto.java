package com.pda.core.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class GetStockmonListResponseDto {
    private List<TravelerStockmonDto> stockmons;

    public GetStockmonListResponseDto(List<TravelerStockmonDto> stockmons) {
        this.stockmons = stockmons;
    }
}
