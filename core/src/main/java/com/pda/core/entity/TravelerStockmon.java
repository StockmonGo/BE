package com.pda.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TravelerStockmon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stockmonCount;

    private Double stockmonAveragePrice;

    @ManyToOne
    @JoinColumn(name = "traveler_id")
    private Traveler traveler;

    @ManyToOne
    @JoinColumn(name = "stockmon_id")
    private Stockmon stockmon;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
