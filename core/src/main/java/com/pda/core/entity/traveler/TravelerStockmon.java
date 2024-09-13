package com.pda.core.entity.traveler;

import com.pda.core.entity.stockmon.Stockmon;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelerStockmon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long stockmonCount;

    @Setter
    private Double stockmonAveragePrice;

    @ManyToOne
    @JoinColumn(name = "traveler_id")
    private Traveler traveler;

    @ManyToOne
    @JoinColumn(name = "stockmon_id")
    private Stockmon stockmon;

    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

}
