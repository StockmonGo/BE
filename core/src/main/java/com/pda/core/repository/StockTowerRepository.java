package com.pda.core.repository;

import com.pda.core.entity.StockTower;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockTowerRepository {

    private final List<StockTower> stockTowers;

    public StockTowerRepository() {
        stockTowers = new ArrayList<>();
        stockTowers.add(new StockTower(1, "알파코 제3캠퍼스",
                37.56855565070902,
                127.00964405969148,
                "data:image/jpeg;base64,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",
                "성수의 알파코 캠퍼스에는 코딩 괴물이 살고 있다.",
                LocalDateTime.now(), LocalDateTime.now()));
    }

    public List<StockTower> findAll() {
        return stockTowers;
    }
}
