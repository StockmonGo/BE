package com.pda.core.dto;

import com.pda.core.entity.Stockmon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatchStockmonResponseDto {

    private Long stockmonId;
    private String stockmonName;
    private String stockType;
    private Long stockPrice;
    private Long stockTotalPrice;
    private String stockMarket;

    public static CatchStockmonResponseDto fromEntity(Stockmon stockmon, Long stockCurrentPrice, Long stockTotalPrice) {
        return new CatchStockmonResponseDto(stockmon.getId(), stockmon.getName(), stockmon.getStock().getStockType().getName(), stockCurrentPrice,stockTotalPrice, stockmon.getStock().getStockMarket());
    }
}
