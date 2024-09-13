package com.pda.core.dto.traveler.stockmon;

import com.pda.core.entity.stockmon.Stockmon;
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
    private String stockCode;
    private String stockmonName;
    private String description;
    private Long stockType;
    private String stockTypeName;
    private Long stockPrice;
    private Long stockTotalPrice;
    private String stockMarket;
    private Boolean isFirst;

    public static CatchStockmonResponseDto fromEntity(Stockmon stockmon, Long stockCurrentPrice, Long stockTotalPrice, boolean isFirst) {
        return new CatchStockmonResponseDto(stockmon.getId(), stockmon.getStock().getCode(), stockmon.getName(), stockmon.getDescription(),
                stockmon.getStock().getStockType().getId(), stockmon.getStock().getStockType().getName(),
                stockCurrentPrice,stockTotalPrice, stockmon.getStock().getStockMarket(), isFirst);
    }
}
