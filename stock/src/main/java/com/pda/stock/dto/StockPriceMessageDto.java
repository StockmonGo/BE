package com.pda.stock.dto;

import lombok.Getter;

@Getter
public class StockPriceMessageDto {
    private String price;

    public StockPriceMessageDto() {
    }

    public StockPriceMessageDto(String price) {
        this.price = price;
    }

}
