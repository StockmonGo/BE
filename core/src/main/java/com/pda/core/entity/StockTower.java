package com.pda.core.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class StockTower {

    private final long id;
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String imgUrl;
    private final String description;

    private final Date createdAt;
    private final Date updatedAt;
}
