package com.pda.core.entity;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class World {

    private final long id;
    private final double latitude;
    private final double longitude;
    private final long stockmonId;
    private final boolean isCaught;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
