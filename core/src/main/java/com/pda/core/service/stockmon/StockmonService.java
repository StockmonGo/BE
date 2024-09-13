package com.pda.core.service.stockmon;


import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.stockmon.stockbook.GetStockmonDetailFromDbDto;
import com.pda.core.dto.stockmon.stockbook.GetStockmonDetailResponseDto;
import com.pda.core.entity.stockmon.Stockmon;
import com.pda.core.exception.stockmon.NoStockmonDetailException;
import com.pda.core.repository.stockmon.StockmonRepository;
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

        return GetStockmonDetailResponseDto.from(dbDto, stockFeignClient.getTotalPrice(dbDto.getStockCode()).getBody(),stockFeignClient.getClosedPrice(dbDto.getStockCode()).getBody());
    }

}
