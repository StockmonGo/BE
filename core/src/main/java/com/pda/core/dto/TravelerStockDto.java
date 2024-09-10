package com.pda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelerStockDto {

    private Long stockId;
    private String stockName;
    private Double stockCount;
    private String stockCode;
    private String accountNumber;

    public TravelerStockDto(Long stockId, String stockName, String stockCode, Double stockCount, String accountNumber) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.stockCount = stockCount;
        this.accountNumber = accountNumber;
    }

}
