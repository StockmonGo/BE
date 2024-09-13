package com.pda.core.dto.traveler.stockmon;

import com.pda.core.entity.stock.Stock;
import com.pda.core.entity.stockmon.Stockmon;
import com.pda.core.entity.traveler.TravelerStockmon;
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
                .id(stock.getId())
                .name(stock.getName())
                .imgUrl(stockmon.getImgUrl())
                .catchCount(travelerStockmon.getStockmonCount())
                .stockCode(stock.getCode())
                .stockAveragePrice(travelerStockmon.getStockmonAveragePrice())
                .build();
    }

}
