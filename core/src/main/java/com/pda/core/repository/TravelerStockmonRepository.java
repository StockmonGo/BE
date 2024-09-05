package com.pda.core.repository;

import com.pda.core.entity.TravelerStockmon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerStockmonRepository extends JpaRepository<TravelerStockmon, Long > {
    List<TravelerStockmon> findByTravelerId(Long travelerId);

    @Query("SELECT ts FROM TravelerStockmon ts WHERE ts. traveler. id = :travelerId AND ts. stockmon. id = :stockmonId")
    Optional<TravelerStockmon> findByTravelerIdAndStockmonId(@Param("travelerId") Long travelerId,
                                                             @Param("stockmonId") Long stockmonId);

}
