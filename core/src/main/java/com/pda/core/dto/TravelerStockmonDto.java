package com.pda.core.dto;

import com.pda.core.entity.Stock;
import com.pda.core.entity.Stockmon;
import com.pda.core.entity.TravelerStockmon;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TravelerStockmonDto {

    private Long id;
    private String name;
    private String imgUrl;
    private Long catchCount;
    private String stockCode;
    private Double stockAveragePrice;

    public static TravelerStockmonDto fromEntity(TravelerStockmon travelerStockmon) {

        Stockmon stockmon = travelerStockmon.getStockmon();
        Stock stock = stockmon.getStock();

        return TravelerStockmonDto.builder()
                .id(stockmon.getId())
                .name(stock.getName())
                .imgUrl(stockmon.getImgUrl())
                .catchCount(travelerStockmon.getStockmonCount())
                .stockCode(stock.getCode())
                .stockAveragePrice(travelerStockmon.getStockmonAveragePrice())
                .build();
    }

}
