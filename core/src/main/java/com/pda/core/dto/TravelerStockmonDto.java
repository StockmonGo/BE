package com.pda.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelerStockmonDto {

    private Long id;
    private String name;
    private String imgUrl;
    private long count;
    private String stockCode;
    private Double stockAveragePrice;
}
