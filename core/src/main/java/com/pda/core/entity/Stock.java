package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Stock {

    private long id;

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
