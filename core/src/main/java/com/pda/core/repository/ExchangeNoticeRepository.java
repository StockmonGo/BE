package com.pda.core.repository;

import com.pda.core.dto.GetStockmonExchangeListResponseDto;
import com.pda.core.entity.ExchangeNotice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeNoticeRepository extends JpaRepository<ExchangeNotice, Long> {
    @Query("SELECT new com.pda.core.dto.GetStockmonExchangeListResponseDto$StockmonExchange(e.id, e.sender.id, e.senderStockmon.id) " +
            "FROM ExchangeNotice e WHERE e.receiver.id = :receiverId")
    List<GetStockmonExchangeListResponseDto.StockmonExchange> findExchangeNoticesByReceiverId(@Param("receiverId") Long receiverId);
}
