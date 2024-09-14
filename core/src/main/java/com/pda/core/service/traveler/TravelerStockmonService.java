package com.pda.core.service.traveler;

import com.pda.commons.dto.StockInfoDto;
import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.traveler.stockmon.CatchStockmonRequestDto;
import com.pda.core.dto.traveler.stockmon.CatchStockmonResponseDto;
import com.pda.core.dto.stockmon.stockbook.GetStockmonListResponseDto;
import com.pda.core.dto.traveler.stockball.DecreaseCountRequestDto;
import com.pda.core.dto.traveler.stockmon.TravelerStockmonDto;
import com.pda.core.entity.traveler.Traveler;
import com.pda.core.entity.world.World;
import com.pda.core.exception.traveler.InvalidStockballCountException;
import com.pda.core.exception.stockmon.NoStockmonDetailException;
import com.pda.core.exception.traveler.NoTravelerException;
import com.pda.core.repository.*;
import com.pda.core.entity.stockmon.Stockmon;
import com.pda.core.entity.traveler.TravelerStockmon;
import com.pda.core.repository.stockmon.StockmonRepository;
import com.pda.core.repository.traveler.TravelerRepository;
import com.pda.core.repository.traveler.TravelerStockmonRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pda.core.service.world.WorldConstant.*;

@Service
@RequiredArgsConstructor
public class TravelerStockmonService {

    private final TravelerStockmonRepository travelerStockmonRepository;
    private final TravelerRepository travelerRepository;
    private final RedisRepository redisRepository;
    private final StockmonRepository stockmonRepository;
    private final StockFeignClient stockFeignClient;

    @Transactional
    public GetStockmonListResponseDto getStockmonsByTravelerId(Long travelerId) {

        List<TravelerStockmon> travelerStockmons = travelerStockmonRepository.findByTravelerId(travelerId);

        List<TravelerStockmonDto> travelerStockmonDtos = new ArrayList<>();

        for (TravelerStockmon travelerStockmon : travelerStockmons) {
            TravelerStockmonDto dto = TravelerStockmonDto.fromEntity(travelerStockmon);
            travelerStockmonDtos.add(dto);
        }

        return new GetStockmonListResponseDto(travelerStockmonDtos);

    }

    @Transactional
    public void decreaseStockball(Long travelerId, DecreaseCountRequestDto decreaseCountRequestDto) {

        Traveler traveler = travelerRepository.findById(travelerId).orElseThrow(NoTravelerException::new);
        long usedStockball = decreaseCountRequestDto.getUsedStockballs();

        if(traveler.getStockballCount() < usedStockball) throw new InvalidStockballCountException();
        travelerRepository.minusStockball(travelerId, usedStockball);
    }

    @Transactional
    public CatchStockmonResponseDto updateCatchStockmon(Long travelerId, CatchStockmonRequestDto catchStockmonRequestDto) {

        int worldId = catchStockmonRequestDto.getWorldId();
        long stockmonId = catchStockmonRequestDto.getStockmonId();
        long usedStockball = catchStockmonRequestDto.getUsedStockballs();
        double latitude = catchStockmonRequestDto.getLatitude();
        double longitude = catchStockmonRequestDto.getLongitude();

        Stockmon stockmon = stockmonRepository.findById(stockmonId).orElseThrow(NoStockmonDetailException::new);
        Traveler traveler = travelerRepository.findById(travelerId).orElseThrow(NoTravelerException::new);

        if(traveler.getStockballCount() < usedStockball) throw new InvalidStockballCountException();
        List<World> list;
        try {
            if(!isMainStreet(latitude, longitude)) {
                list = (List<World>) redisRepository.getList(WORLD_REDIS_KEY).get(0);
                World world = list.get(worldId);
                world.setIsCaught(true);
                list.set(worldId, world);
                redisRepository.setToListAll(WORLD_REDIS_KEY, list);
            } else {
                list = (List<World>) redisRepository.getList(MAIN_STREET_REDIS_KEY).get(0);
                World world = list.get(worldId);
                world.setIsCaught(true);
                list.set(worldId, world);
                redisRepository.setToListAll(MAIN_STREET_REDIS_KEY, list);
            }
        } catch(IndexOutOfBoundsException ignored) {}

        travelerRepository.minusStockball(travelerId, usedStockball);

        AtomicBoolean isFirst = new AtomicBoolean(false);
        TravelerStockmon travelerStockmon = travelerStockmonRepository.findTravelerStockmonByTravelerIdAndStockmonId(travelerId, stockmonId).orElseGet(() -> {
            isFirst.set(true); // orElse로 새로운 객체를 생성할 때 isFirst를 true로 설정
            return TravelerStockmon.builder()
                    .traveler(travelerRepository.getReferenceById(travelerId))
                    .stockmon(stockmonRepository.getReferenceById(stockmonId))
                    .stockmonCount(0L)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .stockmonAveragePrice(0.0)
                    .build();
        });

        long count = travelerStockmon.getStockmonCount() + 10;
        StockInfoDto stockInfoDto = stockFeignClient.getStockInfo(travelerStockmon.getStockmon().getStock().getCode()).getBody();
        long currentPrice = Long.parseLong(stockInfoDto.getOutput().getCurrentPrice());
        long totalPrice = Long.parseLong(stockInfoDto.getOutput().getTotalPrice()) * 1_000_000_00;
        travelerStockmon.setStockmonCount(count);
        travelerStockmon.setStockmonAveragePrice(
                (travelerStockmon.getStockmonAveragePrice() * (count - 10) +
                        Double.parseDouble(String.valueOf(currentPrice)) * 10) / count);

        travelerStockmonRepository.save(travelerStockmon);

        return CatchStockmonResponseDto.fromEntity(stockmon, currentPrice, totalPrice, isFirst.get());
    }

    private boolean isMainStreet(double latitude, double longitude) {
        return (latitude > MAIN_STREET_MIN_LATITUDE)
                && latitude < MAIN_STREET_MAX_LATITUDE
                && longitude > MAIN_STREET_MIN_LONGITUDE
                && longitude < MAIN_STREET_MAX_LONGITUDE;
    }

}
