package com.pda.core.service;


import com.pda.core.client.StockFeignClient;
import com.pda.core.exception.NoStockException;
import com.pda.core.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockFeignClient stockFeignClient;
//    private static long id = 1;
//
//    @Scheduled(cron = "0 * * * * *")
//    public void caching() {
//        String code = stockRepository.findById(id).orElseThrow(NoStockException::new).getCode();
//
//        stockFeignClient.getCurrentPrice(code);
//        stockFeignClient.getClosedPrice(code);
//        stockFeignClient.getTotalPrice(code);
//        id++;
//        id %= 60;
//    }

}
