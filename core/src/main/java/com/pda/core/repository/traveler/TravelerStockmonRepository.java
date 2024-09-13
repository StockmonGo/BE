package com.pda.core.repository.traveler;

import com.pda.core.entity.traveler.TravelerStockmon;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerStockmonRepository extends JpaRepository<TravelerStockmon, Long > {
    List<TravelerStockmon> findByTravelerId(Long travelerId);

    @Query("SELECT ts FROM TravelerStockmon ts WHERE ts. traveler. id = :travelerId AND ts. stockmon. id = :stockmonId")
    Optional<TravelerStockmon> findByTravelerIdAndStockmonId(@Param("travelerId") Long travelerId,
                                                             @Param("stockmonId") Long stockmonId);
    Optional<TravelerStockmon> findTravelerStockmonByTravelerIdAndStockmonId(Long travelerId, Long stockmonId);


    @Query("UPDATE TravelerStockmon ts SET ts.stockmonCount = ts.stockmonCount + 1 WHERE ts.traveler.id = :travelerId and ts.stockmon.id = :stockmonId")
    @Modifying
    void addTravelerStockmonByTravelerIdAndStockmonId(@Param("travelerId") Long travelerId, @Param("stockmonId") Long stockmonId);

    @Modifying
    @Query("UPDATE TravelerStockmon ts SET ts.stockmonCount = :stockmonCount, ts.stockmonAveragePrice = :stockmonAveragePrice WHERE ts.id = :id")
    void updateStockmonCountById(@Param("id") Long id, @Param("stockmonCount") Long stockmonCount, @Param("stockmonAveragePrice") Double stockmonAveragePrice);

}
