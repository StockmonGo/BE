package com.pda.core.repository.world;

import com.pda.core.entity.world.StockTowerTimer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTowerTimerRepository extends JpaRepository<StockTowerTimer, Long> {
    Optional<StockTowerTimer> findFirstByTravelerIdAndStockTowerIdOrderByUpdatedAtDesc(Long travelerId, Long stockTowerId);
}
