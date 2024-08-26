package com.pda.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockTower {

    @Id
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    private String imgUrl;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
