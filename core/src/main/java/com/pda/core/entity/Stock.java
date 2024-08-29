package com.pda.core.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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


    public Stock(long id, String name, String code, StockType stockType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.stockType = stockType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
