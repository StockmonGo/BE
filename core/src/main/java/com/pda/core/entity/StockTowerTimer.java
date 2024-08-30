package com.pda.core.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StockTowerTimer {

    private Long id;
    private Long travelerId;
    private Long stockTowerId;
    private Date createdAt;
    private Date updatedAt;

}
