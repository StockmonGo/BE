package com.pda.core.dto.stockmon.yard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YardStockmonDto {

    private Long id;
    private boolean isGood;

}
