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

}
