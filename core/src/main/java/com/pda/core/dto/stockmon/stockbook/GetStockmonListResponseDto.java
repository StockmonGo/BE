package com.pda.core.dto.stockmon.stockbook;

import java.util.List;

import com.pda.core.dto.traveler.stockmon.TravelerStockmonDto;
import lombok.Getter;

@Getter
public class GetStockmonListResponseDto {
    private List<TravelerStockmonDto> stockmons;

    public GetStockmonListResponseDto(List<TravelerStockmonDto> stockmons) {
        this.stockmons = stockmons;
    }
}
