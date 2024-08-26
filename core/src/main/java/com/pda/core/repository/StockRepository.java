package com.pda.core.repository;

import com.pda.core.entity.Stock;
import com.pda.core.entity.StockType;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class StockRepository {
    private final List<Stock> stocks;

    private final StockTypeRepository stockTypeRepository;

    @Autowired
    public StockRepository(StockTypeRepository stockTypeRepository) {
        this.stockTypeRepository = stockTypeRepository;
        stocks = new ArrayList<>();

        stockTypeRepository.findById(1).ifPresent(stockType ->
                stocks.add(new Stock(1, "신한투자증권", "005930", stockType, LocalDateTime.now(), LocalDateTime.now()))
        );
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
