package com.pda.core.entity;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class StockTower {

    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private String imgUrl;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
