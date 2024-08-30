package com.pda.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetStockmonDetailResponseDto {

    private Long stockmonId;
    private String stockmonName;
    private String description;
    private String imgUrl;
    private Long stockType;
    private String stockTypeName;
    private String stockName;
    private String stockCode;
    private Long stockmonAveragePrice;
    private Long catchCount;
    private Long stockTotalPrice;
    private String stockMarket;

    public static GetStockmonDetailResponseDto from(GetStockmonDetailFromDbDto dbDto) {
        return GetStockmonDetailResponseDto.builder()
                .stockmonId(dbDto.getStockmonId())
                .stockmonName(dbDto.getStockmonName())
                .description(dbDto.getDescription())
                .imgUrl(dbDto.getImgUrl())
                .stockType(dbDto.getStockType())
                .stockTypeName(dbDto.getStockTypeName())
                .stockName(dbDto.getStockName())
                .stockCode(dbDto.getStockCode())
                .stockmonAveragePrice(convertToLong(dbDto.getStockmonAveragePrice()))
                .catchCount(dbDto.getCatchCount())
                .stockTotalPrice(null)
                .stockMarket(dbDto.getStockMarket())
                .build();
    }

    private static Long convertToLong(Double value) {
        return value != null ? Math.round(value) : null;
    }

}
