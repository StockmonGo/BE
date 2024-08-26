package com.pda.core.repository;

import com.pda.core.entity.StockTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTowerRepository extends JpaRepository<StockTower, Long> {
}
