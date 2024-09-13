package com.pda.core.service.stockmon;

import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.stockmon.yard.YardStockmonDto;
import com.pda.core.dto.stockmon.yard.YardStockmonResponseDto;
import com.pda.core.entity.traveler.TravelerStockmon;
import com.pda.core.repository.traveler.TravelerStockmonRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class YardService {
    private final TravelerStockmonRepository travelerStockmonRepository;
    private final StockFeignClient stockFeignClient;

    @Transactional
    public YardStockmonResponseDto getYardByTravelerId(Long travelerId) {
        List<TravelerStockmon> travelerStockmons = travelerStockmonRepository.findByTravelerId(travelerId);

        List<YardStockmonDto> yardStockmonDtos = new ArrayList<>();

        for (TravelerStockmon travelerStockmon : travelerStockmons) {
            YardStockmonDto dto = createYardStockmonDto(travelerStockmon);
            if (dto != null) {
                yardStockmonDtos.add(dto);
            }
        }

        return new YardStockmonResponseDto(yardStockmonDtos);
    }

    private YardStockmonDto createYardStockmonDto(TravelerStockmon travelerStockmon) {
        try {
            Long stockmonId = travelerStockmon.getStockmon().getId();
            String stockCode = travelerStockmon.getStockmon().getStock().getCode();

            long currentPrice = stockFeignClient.getCurrentPrice(stockCode).getBody();
            double averagePrice = travelerStockmon.getStockmonAveragePrice();

            boolean isGood = currentPrice >= averagePrice;

            return new YardStockmonDto(stockmonId, isGood);
        } catch (Exception e) {
            log.error("error: ", e);
            return null;
        }
    }
}
