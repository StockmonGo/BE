package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name = "stock_type_id")
    private  StockType stockType;

    private  LocalDateTime createdAt;

    private  LocalDateTime updatedAt;

    @OneToMany(mappedBy = "stock")
    @JsonIgnoreProperties("stock")
    private List<Stockmon> stockmons = new ArrayList<>();

    public Stock(long id, String name, String code, StockType stockType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.stockType = stockType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
