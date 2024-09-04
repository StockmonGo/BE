package com.pda.core.service;


import com.pda.core.dto.GetStockmonDetailFromDbDto;
import com.pda.core.dto.GetStockmonDetailResponseDto;
import com.pda.core.entity.Stockmon;
import com.pda.core.exception.NoStockmonDetailException;
import com.pda.core.repository.StockmonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockmonService {

    private final StockmonRepository stockmonRepository;

    public Stockmon getStockmon(long id) {
        return stockmonRepository.findById(id).orElseThrow();
    }

    public GetStockmonDetailResponseDto getStockmonDetail(Long id, Long travelerId) {
        GetStockmonDetailFromDbDto dbDto = stockmonRepository.findStockmonDetailById(id, travelerId)
                .orElseThrow(NoStockmonDetailException::new);

        return GetStockmonDetailResponseDto.from(dbDto);
    }

}
