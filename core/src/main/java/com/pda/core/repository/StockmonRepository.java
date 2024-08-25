package com.pda.core.repository;


import com.pda.core.entity.Stockmon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class StockmonRepository {
    private List<Stockmon> stockmons;

    public StockmonRepository() {
        stockmons = new ArrayList<>();
        stockmons.add(new Stockmon(
                1,
                "https://w7.pngwing.com/pngs/773/168/png-transparent-pokemon-pikachu-illustration-pokemon-go-pokemon-yellow-pikachu-ash-ketchum-pikachu-mammal-vertebrate-video-game.png",
                "https://w7.pngwing.com/pngs/773/168/png-transparent-pokemon-pikachu-illustration-pokemon-go-pokemon-yellow-pikachu-ash-ketchum-pikachu-mammal-vertebrate-video-game.png",
                1,
                "알파코에 사는 괴물이다. 정말 무섭게 생겼다.",
                1,
                new Date(), new Date()));
    }

    public Optional<Stockmon> findById(long id) {
        for(Stockmon stockmon : stockmons) {
            if(stockmon.getId() == id) return Optional.of(stockmon);
        }
        return Optional.empty();
    }
}
