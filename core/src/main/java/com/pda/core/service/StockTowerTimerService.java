package com.pda.core.service;

import com.pda.core.dto.StockTowerTimerResponseDto;
import com.pda.core.dto.StockTowerTimerUpdateDto;
import com.pda.core.entity.StockTowerTimer;
import com.pda.core.exception.CoolDownException;
import com.pda.core.repository.StockTowerTimerRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class StockTowerTimerService {

    private final StockTowerTimerRepository stockTowerTimerRepository;
    private static final int COOLDOWN_MINUTES = 3;
    private final Random random = new Random();

    public StockTowerTimerService(StockTowerTimerRepository stockTowerTimerRepository) {
        this.stockTowerTimerRepository = stockTowerTimerRepository;
    }

    @Transactional
    public StockTowerTimerResponseDto useTower(Long travelerId, Long stockTowerId) {
        LocalDateTime now = LocalDateTime.now();
        Optional<StockTowerTimer> latestTimer = stockTowerTimerRepository
                .findFirstByTravelerIdAndStockTowerIdOrderByUpdatedAtDesc(travelerId, stockTowerId);

        if (latestTimer.isPresent()) {
            Duration timeSinceLastUse = Duration.between(latestTimer.get().getUpdatedAt(), now);
            if (timeSinceLastUse.toMinutes() < COOLDOWN_MINUTES) {
                throw new CoolDownException();
            }
        }

        StockTowerTimerUpdateDto updateDto = StockTowerTimerUpdateDto.builder()
                .travelerId(travelerId)
                .stockTowerId(stockTowerId)
                .updatedAt(now)
                .build();

        StockTowerTimer updatedTimer = updateDto.toEntity(latestTimer.orElse(new StockTowerTimer()));

        stockTowerTimerRepository.save(updatedTimer);

        // TODO: 우선 3~6까지 랜덤으로 생성
        long increasedStockBall = random.nextInt(4) + 3;

        return new StockTowerTimerResponseDto(increasedStockBall);
    }
}
