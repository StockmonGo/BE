package com.pda.core.service.world;

import com.pda.core.dto.world.stockTower.StockTowerTimerDetailResponseDto;
import com.pda.core.dto.world.stockTower.StockTowerTimerResponseDto;
import com.pda.core.entity.world.StockTower;
import com.pda.core.entity.world.StockTowerTimer;
import com.pda.core.entity.traveler.Traveler;
import com.pda.core.exception.world.CoolDownException;
import com.pda.core.exception.world.NoStockTowerException;
import com.pda.core.exception.traveler.NoTravelerException;
import com.pda.core.repository.world.StockTowerRepository;
import com.pda.core.repository.world.StockTowerTimerRepository;
import com.pda.core.repository.traveler.TravelerRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class StockTowerTimerService {

    private final StockTowerTimerRepository stockTowerTimerRepository;
    private final StockTowerRepository stockTowerRepository;
    private final TravelerRepository travelerRepository;
    private static final int COOLDOWN_MINUTES = 3;
    private final Random random = new Random();

    public StockTowerTimerService(StockTowerTimerRepository stockTowerTimerRepository, StockTowerRepository stockTowerRepository, TravelerRepository travelerRepository) {
        this.stockTowerTimerRepository = stockTowerTimerRepository;
        this.stockTowerRepository = stockTowerRepository;
        this.travelerRepository = travelerRepository;
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

        StockTowerTimer newTimer = new StockTowerTimer();
        newTimer.setTravelerId(travelerId);
        newTimer.setStockTowerId(stockTowerId);
        newTimer.setUpdatedAt(now);

        stockTowerTimerRepository.save(newTimer);

        // TODO: 우선 3~6까지 랜덤으로 생성
        long increasedStockBall = random.nextInt(4) + 3;

        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow((NoTravelerException::new));

        long currentStockBallCount = traveler.getStockballCount();
        long newStockBallCount = Math.min(currentStockBallCount + increasedStockBall, 50);  // 최대값을 50으로 제한


        long actualIncrease = newStockBallCount - currentStockBallCount;

        traveler.setStockballCount(newStockBallCount);
        travelerRepository.save(traveler);

        return new StockTowerTimerResponseDto(actualIncrease);
    }


    @Transactional
    public StockTowerTimerDetailResponseDto getStockTowerDetail(Long travelerId, Long stockTowerId) {
        StockTower stockTower = stockTowerRepository.findById(stockTowerId)
                .orElseThrow(() -> new NoStockTowerException());

        StockTowerTimer stockTowerTimer = stockTowerTimerRepository
                .findFirstByTravelerIdAndStockTowerIdOrderByUpdatedAtDesc(travelerId, stockTowerId)
                .orElse(null);

        return StockTowerTimerDetailResponseDto.fromEntity(stockTowerTimer, stockTower);
    }
    
}
