package com.pda.core.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStockResponseDto {
    private Long stockId;
    private String stockName;
    private String stockCode;
}
