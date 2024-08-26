package com.pda.core.service;

import com.pda.core.repository.StockRepository;
import com.pda.core.repository.StockmonRepository;
import com.pda.core.repository.TravelerStockmonRepository;
import com.pda.core.dto.GetStockmonListResponseDto;
import com.pda.core.dto.TravelerStockmonDto;
import com.pda.core.entity.Stock;
import com.pda.core.entity.Stockmon;
import com.pda.core.entity.TravelerStockmon;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TravelerStockmonService {

    private final TravelerStockmonRepository travelerStockmonRepository;
    private final StockmonRepository stockmonRepository;
    private final StockRepository stockRepository;

    public TravelerStockmonService(TravelerStockmonRepository travelerStockmonRepository,
                                   StockmonRepository stockmonRepository,
                                   StockRepository stockRepository) {
        this.travelerStockmonRepository = travelerStockmonRepository;
        this.stockmonRepository = stockmonRepository;
        this.stockRepository = stockRepository;
    }


    // 특정 여행자의 모든 스톡몬 정보를 조회하는 메서드
    @Transactional
    public GetStockmonListResponseDto getStockmonsByTravelerId(Long travelerId) {

        List<TravelerStockmon> travelerStockmons = travelerStockmonRepository.findByTravelerId(travelerId);

        List<TravelerStockmonDto> stockmonDtos = new ArrayList<>();

        for (TravelerStockmon travelerStockmon : travelerStockmons) {
            TravelerStockmonDto dto = convertToTravelerStockmonDto(travelerStockmon);
            stockmonDtos.add(dto);
        }

        return new GetStockmonListResponseDto(stockmonDtos);

    }

    // TravelerStockmon 엔티티를 TravelerStockmonDto로 변환하는 메서드
    private TravelerStockmonDto convertToTravelerStockmonDto(TravelerStockmon travelerStockmon) {

        Stockmon stockmon = travelerStockmon.getStockmon();

        Stock stock = stockmon.getStock();

        TravelerStockmonDto dto = new TravelerStockmonDto();

        // 4. TravelerStockmon, Stockmon, Stock 엔티티의 정보를 DTO에 설정
        dto.setId(stockmon.getId());
        dto.setName(stock.getName());
        dto.setImgUrl(stockmon.getImgUrl());
        dto.setCount(travelerStockmon.getStockmonCount());
        dto.setStockCode(stock.getCode());
        dto.setStockAveragePrice(travelerStockmon.getStockmonAveragePrice());

        return dto;
    }
}
