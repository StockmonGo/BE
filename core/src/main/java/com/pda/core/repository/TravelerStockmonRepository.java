package com.pda.core.repository;

import com.pda.core.entity.TravelerStockmon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerStockmonRepository extends JpaRepository<TravelerStockmon, Long > {
    List<TravelerStockmon> findByTravelerId(Long travelerId);
}
