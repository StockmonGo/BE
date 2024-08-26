package com.pda.core.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class World {

    private final Long id;
    private final Double latitude;
    private final Double longitude;
    private final Long stockmonId;
    private final Boolean isCaught;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
