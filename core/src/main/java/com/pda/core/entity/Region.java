package com.pda.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Id
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    private Integer radius;

    @ManyToOne
    @JoinColumn(name = "stock_type_id")
    private StockType stocktype;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
