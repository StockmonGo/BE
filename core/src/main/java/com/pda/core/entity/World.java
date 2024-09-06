package com.pda.core.entity;

import java.util.Date;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class World {

    private Long id;
    private Double latitude;
    private Double longitude;
    private Long stockmonId;
    @Setter
    private Boolean isCaught;

    private Date createdAt;
    private Date updatedAt;

    public World(Long id, Double latitude, Double longitude, Long stockmonId, Boolean isCaught) {
        long currentTimeMillis = System.currentTimeMillis();

        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stockmonId = stockmonId;
        this.isCaught = isCaught;
        createdAt = new Date(currentTimeMillis);
        updatedAt = new Date(currentTimeMillis);
    }

    public World() {
    }
}
