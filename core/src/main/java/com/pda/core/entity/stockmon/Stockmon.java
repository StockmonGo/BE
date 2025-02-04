package com.pda.core.entity.stockmon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pda.core.entity.traveler.TravelerStockmon;
import com.pda.core.entity.notice.ExchangeNotice;
import com.pda.core.entity.stock.Stock;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stockmon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imgUrl;

    private String logoUrl;

    @OneToOne
    private Stock stock;

    private String description;

    private Double appearanceProbability;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "stockmon")
    @JsonIgnoreProperties("stockmon")
    private List<TravelerStockmon> travelerStockmons = new ArrayList<>();

    @OneToMany(mappedBy = "senderStockmon")
    @JsonIgnoreProperties("senderStockmon")
    private List<ExchangeNotice> exchangeNotices = new ArrayList<>();

    public Stockmon(Long id) {
        this.id = id;
    }

}
