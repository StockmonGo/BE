package com.pda.core.entity.world;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
