package com.pda.core.repository;

import com.pda.core.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class StockRepository {
    private final List<Stock> stocks;

    public StockRepository() {
        stocks = new ArrayList<>();
        stocks.add(new Stock(1, "신한투자증권", "005930", 1, new Date(), new Date()));
    }

    public Optional<Stock> findById(long id) {
        for(Stock stock : stocks) {
            if(stock.getId() == id) {
                return Optional.of(stock);
            }
        }
        return Optional.empty();
    }
}
