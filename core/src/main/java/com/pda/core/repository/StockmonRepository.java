package com.pda.core.repository;

import com.pda.core.entity.Stockmon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockmonRepository extends JpaRepository<Stockmon, Long> {
}
