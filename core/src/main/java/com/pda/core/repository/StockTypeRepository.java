package com.pda.core.repository;

import com.pda.core.entity.StockType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTypeRepository extends JpaRepository<StockType, Long> {
}
