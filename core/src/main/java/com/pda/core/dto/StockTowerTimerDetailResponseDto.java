package com.pda.core.dto;

import com.pda.core.entity.StockTower;
import com.pda.core.entity.StockTowerTimer;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockTowerTimerDetailResponseDto {
    private Long id;
    private String name;
    private LocalDateTime spinnedAt;
    private LocalDateTime currentTime;
    private Long limit;

    public static StockTowerTimerDetailResponseDto fromEntity(StockTowerTimer stockTowerTimer, StockTower stockTower) {
        return StockTowerTimerDetailResponseDto.builder()
                .id(stockTower.getId())
                .name(stockTower.getName())
                .spinnedAt(stockTowerTimer != null ? stockTowerTimer.getUpdatedAt() : null)
                .currentTime(LocalDateTime.now())
                .limit(180L) // 타이머 3분 설정
                .build();
    }
    
}
