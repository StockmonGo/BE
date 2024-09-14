package com.pda.core.dto.notice.exchange;

import com.pda.core.entity.notice.ExchangeNotice;
import com.pda.core.entity.stockmon.Stockmon;
import com.pda.core.entity.traveler.Traveler;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockmonExchangeNoticeRequestDto {
    private Long travelerStockmonId;
    private Long receiverId;

    public ExchangeNotice toEntity(Long senderId, double averagePrice) {
        return ExchangeNotice.builder()
                .sender(new Traveler(senderId))
                .receiver(new Traveler(receiverId))
                .senderStockmon(new Stockmon(travelerStockmonId))
                .stockAveragePrice(averagePrice)
                .createdAt(LocalDateTime.now())
                .build();
    }
}