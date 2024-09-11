package com.pda.core.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pda.core.dto.GetWorldStockmonsRequestDto;
import com.pda.core.entity.World;
import com.pda.core.repository.RedisRepository;
import com.pda.core.utils.RandomUtil;
import jakarta.transaction.Transactional;
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
    private final int MAX_STREET_LNG = (int) ((MAIN_STREET_MAX_LONGITUDE - MAIN_STREET_MIN_LONGITUDE) / STOCKMON_LONGITUDE_DIFF);
    private final int[] dIndex = {
            -MAX_LNG, -MAX_LNG - 1, -MAX_LNG + 1,
            0, -1, 1,
            MAX_LNG, MAX_LNG - 1, MAX_LNG + 1};
    private final int[] mIndex = {
            -MAX_STREET_LNG, -MAX_STREET_LNG - 1, MAX_STREET_LNG + 1,
            0, -1, 1,
            MAX_STREET_LNG, MAX_STREET_LNG - 1, MAX_STREET_LNG + 1
    };
    private final int GENERAL_MAX = 87429;


    public List<World> getNearWorlds(GetWorldStockmonsRequestDto getWorldStockmonsRequestDto) {

        BigDecimal latitude = getWorldStockmonsRequestDto.getLatitude();
        BigDecimal longitude = getWorldStockmonsRequestDto.getLongitude();

        if(isMainStreet(latitude.doubleValue(), longitude.doubleValue())) {
            List<World> list = (List<World>) redisRepository.getList(MAIN_STREET_REDIS_KEY).get(0);
            List<World> newList = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                World world = list.get(i);
                if(validIndex(i, list)) {
                    newList.add(world);
                }
            }

            return newList;
        }

        List<World> list = (List<World>) redisRepository.getList(WORLD_REDIS_KEY).get(0);

        BigDecimal stockmonLatitudeDiff = BigDecimal.valueOf(STOCKMON_LATITUDE_DIFF);
        BigDecimal stockmonLongitudeDiff = BigDecimal.valueOf(STOCKMON_LONGITUDE_DIFF);
        BigDecimal stockmonLatitudeMin = BigDecimal.valueOf(Double.parseDouble(MIN_LATITUDE_STRING));
        BigDecimal stockmonLongitudeMin = BigDecimal.valueOf(Double.parseDouble(MIN_LONGITUDE_STRING));

        BigDecimal streetLatitudeMin = BigDecimal.valueOf(MAIN_STREET_MIN_LATITUDE);
        BigDecimal streetLongitudeMin = BigDecimal.valueOf(MAIN_STREET_MIN_LONGITUDE);

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
            if(validIndex(idx, list)) newList.add(list.get(idx));
        }

        if(isMainStreet(latitude.doubleValue(), longitude.doubleValue())) {
            int streetLat = latitude
                    .subtract(streetLatitudeMin)
                    .divide(stockmonLatitudeDiff, RoundingMode.HALF_UP)
                    .intValue();
            int streetLong = longitude
                    .subtract(streetLongitudeMin)
                    .divide(stockmonLongitudeDiff, RoundingMode.HALF_UP)
                    .intValue();

            for(int i = 0; i < mIndex.length; i++) {
                int streetIndex = GENERAL_MAX + streetLat * MAX_STREET_LNG + streetLong + mIndex[i];
                if (validIndex(streetIndex, list)) newList.add(list.get(streetIndex));
            }
        }

        return newList;
    }

    private boolean validIndex(int index, List<World> list) {
        if(index >= list.size() || index < 0) return false;

        return !list.get(index).getIsCaught();
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void setWorld() throws JsonProcessingException {
        List<Object> list = new ArrayList<>();
        List<World> tmpList = new ArrayList<>();
        long count = 0;
        double latitude = Double.parseDouble(MIN_LATITUDE_STRING);
        double nextLatitude = Double.parseDouble(MIN_LATITUDE_STRING) + STOCKMON_LATITUDE_DIFF;
        int tmp = 0;

        for(int i = 1; nextLatitude <= Double.parseDouble(MAX_LATITUDE_STRING); i++) {
            double longitude = Double.parseDouble(MIN_LONGITUDE_STRING);
            double nextLongitude = Double.parseDouble(MIN_LONGITUDE_STRING) + STOCKMON_LONGITUDE_DIFF;
            for(int j = 1; nextLongitude <= Double.parseDouble(MAX_LONGITUDE_STRING); j++ ) {
                list.add(getNewWorld(count, latitude, longitude, nextLatitude, nextLongitude));
                if(isMainStreet(latitude, longitude)) {
                    tmpList.add(getNewWorld(GENERAL_MAX + tmp, latitude, longitude, nextLatitude, nextLongitude));
                    tmp++;
                }
                count++;

                longitude = nextLongitude;
                nextLongitude = Double.parseDouble(MIN_LONGITUDE_STRING) + (j + 1) * STOCKMON_LONGITUDE_DIFF;
            }
            latitude = nextLatitude;
            nextLatitude = Double.parseDouble(MIN_LATITUDE_STRING) + (i + 1) * STOCKMON_LATITUDE_DIFF;
        }
        list.addAll(tmpList);
        redisRepository.setToListAll(WORLD_REDIS_KEY, list);
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void setMainStreetWorld() throws JsonProcessingException {

        List<Object> list = new ArrayList<>();
        long count = 0;
        double latitude = MAIN_STREET_MIN_LATITUDE;
        double nextLatitude = MAIN_STREET_MIN_LATITUDE + STOCKMON_LATITUDE_DIFF;

        for(int i = 1; nextLatitude <= MAIN_STREET_MAX_LATITUDE; i++) {
            double longitude = MAIN_STREET_MIN_LONGITUDE;
            double nextLongitude = MAIN_STREET_MIN_LONGITUDE + STOCKMON_LONGITUDE_DIFF;
            for(int j = 1; nextLongitude <= MAIN_STREET_MAX_LONGITUDE; j++) {
                list.add(getNewWorld(count++, latitude, longitude, nextLatitude, nextLongitude));
                list.add(getNewWorld(count++, latitude, longitude, nextLatitude, nextLongitude));
                longitude = nextLongitude;
                nextLongitude = MAIN_STREET_MIN_LONGITUDE + (j + 1) * STOCKMON_LONGITUDE_DIFF;
            }
            latitude = nextLatitude;
            nextLatitude = MAIN_STREET_MIN_LATITUDE + (i + 1) * STOCKMON_LATITUDE_DIFF;
        }
        redisRepository.setToListAll(MAIN_STREET_REDIS_KEY, list);
    }

    private World getNewWorld(long count, double latitude, double longitude, double nextLatitude, double nextLongitude) {

        long currentTimeMillis = System.currentTimeMillis();
        return World.builder()
                .id(count)
                .latitude(RandomUtil.createRandomDouble(latitude, nextLatitude))
                .longitude(RandomUtil.createRandomDouble(longitude, nextLongitude))
                .createdAt(new Date(currentTimeMillis))
                .updatedAt(new Date(currentTimeMillis))
                .isCaught(false)
                .stockmonId(RandomUtil.createRandomInt(1, 58))
                .build();
    }

    private boolean isMainStreet(double latitude, double longitude) {

        return (latitude > MAIN_STREET_MIN_LATITUDE)
                && latitude < MAIN_STREET_MAX_LATITUDE
                && longitude > MAIN_STREET_MIN_LONGITUDE
                && longitude < MAIN_STREET_MAX_LONGITUDE;
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void reGenerate() throws JsonProcessingException {
        List<World> list = (List<World>) redisRepository.getList(WORLD_REDIS_KEY).get(0);
        boolean isChanged = false;
        double latitude = Double.parseDouble(MIN_LATITUDE_STRING);
        double nextLatitude = Double.parseDouble(MIN_LATITUDE_STRING) + STOCKMON_LATITUDE_DIFF;

        int count = 0;
        for(int i = 1; nextLatitude <= Double.parseDouble(MAX_LATITUDE_STRING); i++) {
            double longitude = Double.parseDouble(MIN_LONGITUDE_STRING);
            double nextLongitude = Double.parseDouble(MIN_LONGITUDE_STRING) + STOCKMON_LONGITUDE_DIFF;
            for(int j = 1; nextLongitude <= Double.parseDouble(MAX_LONGITUDE_STRING); j++ ) {

                World world = list.get(count);

                if(world.getIsCaught()) {
                    isChanged = true;
                    list.set(count, getReWorld(world, latitude, longitude, nextLatitude, nextLongitude));
                }

                longitude = nextLongitude;
                nextLongitude = Double.parseDouble(MIN_LONGITUDE_STRING) + (j + 1) * STOCKMON_LONGITUDE_DIFF;
                count++;
            }
            latitude = nextLatitude;
            nextLatitude = Double.parseDouble(MIN_LATITUDE_STRING) + (i + 1) * STOCKMON_LATITUDE_DIFF;

        }

        for(int i = count; i < list.size(); i++) {
            World world = list.get(i);
            if(world.getIsCaught()) {
                isChanged = true;
                list.set(i, getReWorld(world));
            }
        }
        if(isChanged) {
            redisRepository.setToListAll(WORLD_REDIS_KEY, list);
        }

    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void reMainStreetGenerate() throws JsonProcessingException {
        List<World> list = (List<World>) redisRepository.getList(MAIN_STREET_REDIS_KEY).get(0);
        boolean isChanged = false;
        double latitude = MAIN_STREET_MIN_LATITUDE;
        double nextLatitude = MAIN_STREET_MIN_LATITUDE + STOCKMON_LATITUDE_DIFF;

        int count = 0;
        for(int i = 1; nextLatitude <= MAIN_STREET_MAX_LATITUDE; i++) {
            double longitude = MAIN_STREET_MIN_LONGITUDE;
            double nextLongitude = MAIN_STREET_MIN_LONGITUDE+ STOCKMON_LONGITUDE_DIFF;
            for(int j = 1; nextLongitude <= MAIN_STREET_MAX_LONGITUDE; j++) {

                World world = list.get(count);
                World world2 = list.get(count+1);

                if(world.getIsCaught()) {
                    isChanged = true;
                    list.set(count, getReWorld(world, latitude, longitude, nextLatitude, nextLongitude));
                }

                if(world2.getIsCaught()) {
                    isChanged = true;
                    list.set(count + 1, getReWorld(world2, latitude, longitude, nextLatitude, nextLongitude));
                }

                longitude = nextLongitude;
                nextLongitude = MAIN_STREET_MIN_LONGITUDE+ (j + 1) * STOCKMON_LONGITUDE_DIFF;
                count+=2;
            }
            latitude = nextLatitude;
            nextLatitude = MAIN_STREET_MIN_LATITUDE+ (i + 1) * STOCKMON_LATITUDE_DIFF;

        }

        if(isChanged) {
            redisRepository.setToListAll(MAIN_STREET_REDIS_KEY, list);
        }

    }

    private World getReWorld(World world, double latitude, double longitude, double nextLatitude, double nextLongitude) {
        long currentTimeMillis = System.currentTimeMillis();
        return World.builder()
                .id(world.getId())
                .latitude(RandomUtil.createRandomDouble(latitude, nextLatitude))
                .longitude(RandomUtil.createRandomDouble(longitude, nextLongitude))
                .createdAt(world.getCreatedAt())
                .updatedAt(new Date(currentTimeMillis))
                .isCaught(false)
                .stockmonId(RandomUtil.createRandomInt(1, 58))
                .build();
    }

    private World getReWorld(World world) {
        long currentTimeMillis = System.currentTimeMillis();
        return World.builder()
                .id(world.getId())
                .latitude(world.getLatitude())
                .longitude(world.getLongitude())
                .createdAt(world.getCreatedAt())
                .updatedAt(new Date(currentTimeMillis))
                .isCaught(false)
                .stockmonId(RandomUtil.createRandomInt(1, 58))
                .build();
    }


}
