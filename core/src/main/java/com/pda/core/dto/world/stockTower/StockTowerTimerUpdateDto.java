package com.pda.core.dto.world.stockTower;
import com.pda.core.entity.world.StockTowerTimer;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class StockTowerTimerUpdateDto {

    private Long travelerId;
    private Long stockTowerId;
    private LocalDateTime updatedAt;

    public static StockTowerTimerUpdateDto of(Long travelerId, Long stockTowerId, LocalDateTime updatedAt) {
        return StockTowerTimerUpdateDto.builder()
                .travelerId(travelerId)
                .stockTowerId(stockTowerId)
                .updatedAt(updatedAt)
                .build();
    }

    public StockTowerTimer toEntity(StockTowerTimer existingTimer) {
        return StockTowerTimer.builder()
                .id(existingTimer.getId())  // 기존 ID 유지
                .travelerId(this.travelerId)
                .stockTowerId(this.stockTowerId)
                .updatedAt(this.updatedAt)
                .build();
    }

}