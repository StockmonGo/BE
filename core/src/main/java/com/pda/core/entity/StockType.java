package com.pda.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class StockType {

    private final long id;
    private final String name;
    private final Date createdAt;
    private final Date updatedAt;
}
