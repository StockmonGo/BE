package com.pda.core.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class Stock {

    private final long id;

    private final String name;
    private final String code;

    private final long stockTypeId;

    private final Date createdAt;
    private final Date updatedAt;
}
