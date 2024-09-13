package com.pda.core.repository.stockmon;

import com.pda.core.dto.stockmon.stockbook.GetStockmonDetailFromDbDto;
import com.pda.core.entity.stockmon.Stockmon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockmonRepository extends JpaRepository<Stockmon, Long> {
    @Query("SELECT new com.pda.core.dto.GetStockmonDetailFromDbDto(" +
            "    s.id, " +
            "    s.name, " +
            "    s.description, " +
            "    s.imgUrl, " +
            "    s.stock.stockType.id, " +
            "    ts.stockmonAveragePrice, " +
            "    s.stock.stockType.name, " +
            "    s.stock.name, " +
            "    s.stock.code, " +
            "    COALESCE(ts.stockmonCount, 0L), " +
            "    s.stock.stockMarket " +
            ")" +
            "FROM TravelerStockmon ts LEFT JOIN Stockmon s " +
            "ON s.id = ts.stockmon.id " +
            "WHERE ts.traveler.id = :travelerId and ts.stockmon.id = :id")

    Optional<GetStockmonDetailFromDbDto> findStockmonDetailById(@Param("id") Long id, @Param("travelerId") Long travelerId);
}
