package com.pda.core.service;

import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.*;
import com.pda.core.entity.*;
import com.pda.core.exception.InvalidStockballCountException;
import com.pda.core.exception.NoStockmonDetailException;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.*;
import com.pda.core.entity.Stockmon;
import com.pda.core.entity.TravelerStockmon;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pda.core.utils.WorldConstant.WORLD_REDIS_KEY;

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
        long usedStockball = decreaseCountRequestDto.getUsedStockball();

        if(traveler.getStockballCount() < usedStockball) throw new InvalidStockballCountException();
        travelerRepository.minusStockball(travelerId, usedStockball);
    }

    @Transactional
    public CatchStockmonResponseDto updateCatchStockmon(Long travelerId, CatchStockmonRequestDto catchStockmonRequestDto) {

        int worldId = catchStockmonRequestDto.getWorldId();
        long stockmonId = catchStockmonRequestDto.getStockmonId();
        long usedStockball = catchStockmonRequestDto.getUsedStockballs();
        Stockmon stockmon = stockmonRepository.findById(stockmonId).orElseThrow(NoStockmonDetailException::new);
        Traveler traveler = travelerRepository.findById(travelerId).orElseThrow(NoTravelerException::new);

        if(traveler.getStockballCount() < usedStockball) throw new InvalidStockballCountException();

        List<World> list = (List<World>) redisRepository.getList(WORLD_REDIS_KEY).get(0);
        World world = list.get(worldId);
        world.setIsCaught(true);
        list.set(worldId, world);
        redisRepository.setToListAll(WORLD_REDIS_KEY, list);


        travelerRepository.minusStockball(travelerId, usedStockball);

        TravelerStockmon travelerStockmon = travelerStockmonRepository.findTravelerStockmonByTravelerIdAndStockmonId(travelerId, stockmonId).orElse(
                TravelerStockmon.builder()
                        .traveler(travelerRepository.getReferenceById(travelerId))
                        .stockmon(stockmonRepository.getReferenceById(stockmonId))
                        .stockmonCount(0L)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .stockmonAveragePrice(0.0)
                        .build());

        long count = travelerStockmon.getStockmonCount() + 1;
        long currentPrice = stockFeignClient.getCurrentPrice(travelerStockmon.getStockmon().getStock().getCode()).getBody();
        long totalPrice = stockFeignClient.getTotalPrice(travelerStockmon.getStockmon().getStock().getCode()).getBody();
        travelerStockmon.setStockmonCount(count);
        travelerStockmon.setStockmonAveragePrice(
                (travelerStockmon.getStockmonAveragePrice() * (count - 1) +
                        Double.parseDouble(String.valueOf(currentPrice)) /count));

        travelerStockmonRepository.save(travelerStockmon);

        return CatchStockmonResponseDto.fromEntity(stockmon, currentPrice, totalPrice);
    }

}
