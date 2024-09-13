package com.pda.core.dto.stockmon.stockbook;

import lombok.Getter;

@Getter
public class GetStockmonDetailFromDbDto {

    private Long stockmonId;
    private String stockmonName;
    private String description;
    private String imgUrl;
    private Long stockType;
    private Double stockmonAveragePrice;
    private String stockTypeName;
    private String stockName;
    private String stockCode;
    private Long catchCount;
    private String stockMarket;

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