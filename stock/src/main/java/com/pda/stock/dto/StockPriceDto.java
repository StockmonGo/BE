package com.pda.stock.dto;

import lombok.Getter;

@Getter
public class StockPriceDto {
    private String content;

    public StockPriceDto() {
    }

    public StockPriceDto(String content) {
        this.content = content;
    }

}
