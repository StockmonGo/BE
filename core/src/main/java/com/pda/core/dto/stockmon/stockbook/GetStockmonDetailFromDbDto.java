package com.pda.core.dto.stockmon.stockbook;

import lombok.Getter;

@Getter
public class GetStockmonDetailFromDbDto {

    private final Long stockmonId;
    private final String stockmonName;
    private final String description;
    private final String imgUrl;
    private final Long stockType;
    private final Double stockmonAveragePrice;
    private final String stockTypeName;
    private final String stockName;
    private final String stockCode;
    private final Long catchCount;
    private final String stockMarket;

    public GetStockmonDetailFromDbDto(Long stockmonId, String stockmonName, String description, String imgUrl,
                                      Long stockType, Double stockmonAveragePrice, String stockTypeName,
                                      String stockName, String stockCode, Long catchCount, String stockMarket) {
        this.stockmonId = stockmonId;
        this.stockmonName = stockmonName;
        this.description = description;
        this.imgUrl = imgUrl;
        this.stockType = stockType;
        this.stockmonAveragePrice = stockmonAveragePrice;
        this.stockTypeName = stockTypeName;
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.catchCount = catchCount;
        this.stockMarket = stockMarket;
    }

}