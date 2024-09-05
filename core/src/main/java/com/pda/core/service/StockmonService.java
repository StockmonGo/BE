package com.pda.core.service;


import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.GetStockmonDetailFromDbDto;
import com.pda.core.dto.GetStockmonDetailResponseDto;
import com.pda.core.entity.Stockmon;
import com.pda.core.exception.NoStockmonDetailException;
import com.pda.core.repository.StockmonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockmonService {

    private final StockmonRepository stockmonRepository;
    private final StockFeignClient stockFeignClient;

    public Stockmon getStockmon(long id) {
        return stockmonRepository.findById(id).orElseThrow();
    }

    @Transactional
    public GetStockmonDetailResponseDto getStockmonDetail(Long id, Long travelerId) {
        GetStockmonDetailFromDbDto dbDto = stockmonRepository.findStockmonDetailById(id, travelerId)
                .orElseThrow(NoStockmonDetailException::new);

        return GetStockmonDetailResponseDto.from(dbDto, stockFeignClient.getTotalPrice(dbDto.getStockCode()).getBody());
    }

}
