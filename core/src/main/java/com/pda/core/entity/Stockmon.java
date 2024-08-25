package com.pda.core.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class Stockmon {

    private final long id;

    private final String imgUrl;
    private final String logoUrl;
    private final long stockId;
    private final String description;
    private final double appearanceProbability;

    private final Date createdAt;
    private final Date updatedAt;
}
