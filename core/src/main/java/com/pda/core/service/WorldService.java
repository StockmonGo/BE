package com.pda.core.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pda.core.dto.GetWorldStockmonsRequestDto;
import com.pda.core.entity.World;
import com.pda.core.repository.RedisRepository;
import com.pda.core.utils.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.pda.core.utils.WorldConstant.*;

@Service
@RequiredArgsConstructor
public class WorldService {

    private final RedisRepository redisRepository;
    private final int MAX_LNG = (int) ((Double.parseDouble(MAX_LONGITUDE_STRING) - Double.parseDouble(MIN_LONGITUDE_STRING)) / STOCKMON_LONGITUDE_DIFF);
    private final int[] dIndex = {
            -MAX_LNG, -MAX_LNG - 1, -MAX_LNG + 1,
            0, -1, 1,
            MAX_LNG, MAX_LNG - 1, MAX_LNG + 1};


    public List<World> getNearWorlds(GetWorldStockmonsRequestDto getWorldStockmonsRequestDto) {
        List<World> list = (List<World>) redisRepository.getList(WORLD_REDIS_KEY).get(0);

        BigDecimal latitude = getWorldStockmonsRequestDto.getLatitude();
        BigDecimal longitude = getWorldStockmonsRequestDto.getLongitude();

        BigDecimal stockmonLatitudeDiff = BigDecimal.valueOf(STOCKMON_LATITUDE_DIFF);
        BigDecimal stockmonLongitudeDiff = BigDecimal.valueOf(STOCKMON_LONGITUDE_DIFF);
        BigDecimal stockmonLatitudeMin = BigDecimal.valueOf(Double.parseDouble(MIN_LATITUDE_STRING));
        BigDecimal stockmonLongitudeMin = BigDecimal.valueOf(Double.parseDouble(MIN_LONGITUDE_STRING));

        int userLat = latitude
                .subtract(stockmonLatitudeMin)
                .divide(stockmonLatitudeDiff, RoundingMode.HALF_UP)
                .intValue();
        int userLng = longitude
                .subtract(stockmonLongitudeMin)
                .divide(stockmonLongitudeDiff, RoundingMode.HALF_UP)
                .intValue();

        int index = userLat * MAX_LNG + userLng;
        List<World> newList = new ArrayList<>();

        for (int i = 0; i < dIndex.length; i++) {
            int idx = index + dIndex[i];
            if (validIndex(idx, list)) newList.add(list.get(idx));
        }

        return newList;
    }

    private boolean validIndex(int index, List<World> list) {
        if(index > list.size() || index < 0) return false;

        return !list.get(index).getIsCaught();
    }


    @Scheduled(cron = "0 0 * * * *")
    public void setWorld() throws JsonProcessingException {
        List<Object> list = new ArrayList<>();
        long count = 0;
        double latitude = Double.parseDouble(MIN_LATITUDE_STRING);
        double nextLatitude = Double.parseDouble(MIN_LATITUDE_STRING) + STOCKMON_LATITUDE_DIFF;

        for(int i = 1; nextLatitude <= Double.parseDouble(MAX_LATITUDE_STRING); i++) {

            double longitude = Double.parseDouble(MIN_LONGITUDE_STRING);
            double nextLongitude = Double.parseDouble(MIN_LONGITUDE_STRING) + STOCKMON_LONGITUDE_DIFF;
            for(int j = 1; nextLongitude <= Double.parseDouble(MAX_LONGITUDE_STRING); j++ ) {
                long currentTimeMillis = System.currentTimeMillis();

                World newWorld = World.builder()
                        .id(count++)
                        .latitude(RandomUtil.createRandomDouble(latitude, nextLatitude))
                        .longitude(RandomUtil.createRandomDouble(longitude, nextLongitude))
                        .createdAt(new Date(currentTimeMillis))
                        .updatedAt(new Date(currentTimeMillis))
                        .isCaught(false)
                        .stockmonId(RandomUtil.createRandomInt(1, 58))
                        .build();

                list.add(newWorld);

                longitude = nextLongitude;
                nextLongitude = Double.parseDouble(MIN_LONGITUDE_STRING) + (j + 1) * STOCKMON_LONGITUDE_DIFF;
            }
            latitude = nextLatitude;
            nextLatitude = Double.parseDouble(MIN_LATITUDE_STRING) + (i + 1) * STOCKMON_LATITUDE_DIFF;

        }
        redisRepository.setToListAll(WORLD_REDIS_KEY, list);
    }

    @Scheduled(cron = "0 * * * * *")
    public void reGenerate() throws JsonProcessingException {
        List<World> list = (List<World>) redisRepository.getList(WORLD_REDIS_KEY).get(0);
        boolean isChanged = false;
        for(int i = 0; i < list.size(); i++) {
            World world = list.get(i);

            if(world.getIsCaught()) {
                isChanged = true;
                long currentTimeMillis = System.currentTimeMillis();
                World newWorld = World.builder()
                        .id(world.getId())
                        .latitude(RandomUtil.createRandomDouble(Double.parseDouble(MIN_LATITUDE_STRING) + i * STOCKMON_LATITUDE_DIFF,
                                Double.parseDouble(MIN_LATITUDE_STRING) + (i + 1) * STOCKMON_LATITUDE_DIFF))
                        .longitude(RandomUtil.createRandomDouble(Double.parseDouble(MIN_LONGITUDE_STRING) + i * STOCKMON_LONGITUDE_DIFF,
                                Double.parseDouble(MIN_LONGITUDE_STRING) + (i + 1) * STOCKMON_LONGITUDE_DIFF))
                        .createdAt(world.getCreatedAt())
                        .updatedAt(new Date(currentTimeMillis))
                        .isCaught(false)
                        .stockmonId(RandomUtil.createRandomInt(1, 58))
                        .build();
                list.set(i, newWorld);
            }

        }

        if(isChanged) redisRepository.setToListAll(WORLD_REDIS_KEY, list);
    }


}
