package com.pda.core.dto.notice.exchange;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetStockmonExchangeListResponseDto {
    private List<StockmonExchange> stockmons;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StockmonExchange {
        private Long noticeId;
        private Long senderId;
        private String senderNickname;
        private Long senderStockmonId;
        private String senderStockmonName;
        private LocalDateTime createAt;

    }

}