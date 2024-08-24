package com.pda.core.repository;


import com.pda.core.entity.StockType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class StockTypeRepository {

    private final List<StockType> stockTypes;

    public StockTypeRepository() {
        stockTypes = new ArrayList<>();

        stockTypes.add(new StockType(1, "금융", new Date(), new Date()));
    }

    public Optional<StockType> findById(long id) {
        for(StockType stockType : stockTypes) {
            if(stockType.getId() == id) {
                return Optional.of(stockType);
            }
        }
        return Optional.empty();
    }

}
