package com.pda.core.dto;

import com.pda.core.entity.ExchangeNotice;
import com.pda.core.entity.Stockmon;
import com.pda.core.entity.Traveler;
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

    public ExchangeNotice toEntity(Long senderId) {
        return ExchangeNotice.builder()
                .sender(new Traveler(senderId))
                .receiver(new Traveler(receiverId))
                .senderStockmon(new Stockmon(travelerStockmonId))
                .createdAt(LocalDateTime.now())
                .build();
    }
}