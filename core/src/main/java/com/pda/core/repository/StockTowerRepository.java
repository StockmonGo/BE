package com.pda.core.repository;

import com.pda.core.entity.StockTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTowerRepository extends JpaRepository<StockTower, Long> {

    @Query("select st from StockTower st where st.latitude >= :minLatitude and st.latitude <= :maxLatitude and st.longitude >= :minLongitude and st.longitude <= :maxLongitude")
    List<StockTower> findByLocation(
            @Param("minLatitude") double minLatitude,
            @Param("minLongitude") double minLongitude,
            @Param("maxLatitude") double maxLatitude,
            @Param("maxLongitude") double maxLongitude
    );
}
