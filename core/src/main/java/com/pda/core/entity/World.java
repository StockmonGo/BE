package com.pda.core.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class World {

    private final long id;
    private final double latitude;
    private final double longitude;
    private final long stockmonId;
    private final boolean isCaught;
    private final Date createdAt;
    private final Date updatedAt;
}
