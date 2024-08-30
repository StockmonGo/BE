package com.pda.core.service;

import com.pda.core.dto.StockTowerTimerResponseDto;
import com.pda.core.entity.StockTowerTimer;
import com.pda.core.repository.RedisRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockTowerTimerService {

    private final RedisRepository redisRepository;

    // Redis에 저장될 때 사용할 키
    private static final String STOCK_TOWER_TIMER_KEY = "stockTowerTimer";

    public StockTowerTimerResponseDto saveStockTowerTimer(Long travelerId, Long stockTowerId) {
        StockTowerTimer timer = StockTowerTimer.builder()
                .id(generateUniqueId())
                .travelerId(travelerId)
                .stockTowerId(stockTowerId)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        redisRepository.pushToList(STOCK_TOWER_TIMER_KEY, timer);

        long increasedStockBall = 4;

        return new StockTowerTimerResponseDto(increasedStockBall);
    }

    private Long generateUniqueId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    /**
     * 모든 StockTowerTimer 객체를 조회
     * @return StockTowerTimer 객체 리스트
     */
    public List<StockTowerTimer> getAllStockTowerTimers() {
        List<Object> timerObjects = redisRepository.getList(STOCK_TOWER_TIMER_KEY);
        return timerObjects.stream()
                .map(obj -> (StockTowerTimer) obj)
                .collect(Collectors.toList());
    }

    /**
     * 특정 traveler의 StockTowerTimer 객체를 조회
     * @param travelerId 여행자 ID
     * @return 해당 traveler의 StockTowerTimer 객체 리스트
     */
    public List<StockTowerTimer> getStockTowerTimersByTravelerId(Long travelerId) {
        return getAllStockTowerTimers().stream()
                .filter(timer -> timer.getTravelerId().equals(travelerId))
                .collect(Collectors.toList());
    }
}
