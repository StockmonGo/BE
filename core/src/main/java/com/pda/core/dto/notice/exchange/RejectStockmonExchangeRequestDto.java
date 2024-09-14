package com.pda.core.dto.notice.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RejectStockmonExchangeRequestDto {
    private Long noticeId;
}
