package com.pda.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Region {

    private Long id;

    private String name;

    private double latitude;

    private double longitude;

    private Integer radius;

    @ManyToOne
    @JoinColumn(name = "stock_type_id")
    private StockType stocktype;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
