package com.pda.core.repository;

import com.pda.core.entity.StockTowerTimer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTowerTimerRepository extends JpaRepository<StockTowerTimer, Long> {
    Optional<StockTowerTimer> findFirstByTravelerIdAndStockTowerIdOrderByUpdatedAtDesc(Long travelerId, Long stockTowerId);
    Optional<StockTowerTimer> findFirstByStockTowerIdOrderByUpdatedAtDesc(Long stockTowerId);
}
