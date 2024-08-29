package com.pda.stock.entity;

import lombok.Getter;

@Getter
public class StockPriceDto {
    private String price;

    public StockPriceDto() {
    }

    public StockPriceDto(String price) {
        this.price = price;
    }

}
