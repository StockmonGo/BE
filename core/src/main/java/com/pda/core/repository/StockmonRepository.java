package com.pda.core.repository;

import com.pda.core.dto.GetStockmonDetailFromDbDto;
import com.pda.core.entity.Stockmon;
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
            "FROM Stockmon s " +
            "LEFT JOIN s.travelerStockmons ts " +
            "WHERE s.id = :id")

    Optional<GetStockmonDetailFromDbDto> findStockmonDetailById(@Param("id") Long id);
}
