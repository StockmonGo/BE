package com.pda.core.repository.notice;

import com.pda.core.dto.notice.exchange.GetStockmonExchangeListResponseDto;
import com.pda.core.entity.notice.ExchangeNotice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeNoticeRepository extends JpaRepository<ExchangeNotice, Long> {
    @Query("SELECT new com.pda.core.dto.notice.exchange.GetStockmonExchangeListResponseDto.StockmonExchange(" +
            "e.id, e.sender.id, e.sender.nickname, e.senderStockmon.id, s.name, e.createdAt) " +
            "FROM ExchangeNotice e " +
            "JOIN Stockmon s ON e.senderStockmon.id = s.id " +
            "WHERE e.receiver.id = :receiverId")
    List<GetStockmonExchangeListResponseDto.StockmonExchange> findExchangeNoticesByReceiverId(@Param("receiverId") Long receiverId);

    @Query("SELECT e FROM ExchangeNotice e WHERE e.id = :noticeId AND e.receiver.id = :receiverId")
    Optional<ExchangeNotice> findByIdAndReceiverId(@Param("noticeId") Long noticeId, @Param("receiverId") Long receiverId);

    void deleteById(Long id);
}
