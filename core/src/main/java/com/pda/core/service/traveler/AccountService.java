package com.pda.core.service.traveler;


import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.traveler.account.AccountCheckResponseDto;
import com.pda.core.dto.traveler.account.AccountDto;
import com.pda.core.dto.traveler.account.AccountStockResponseDto;
import com.pda.core.dto.traveler.account.HasAccountResponseDto;
import com.pda.core.entity.stock.Stock;
import com.pda.core.entity.traveler.Account;
import com.pda.core.entity.traveler.AccountStock;
import com.pda.core.entity.traveler.Traveler;
import com.pda.core.entity.traveler.TravelerStockmon;
import com.pda.core.exception.stock.NoStockException;
import com.pda.core.exception.traveler.HasAccountException;
import com.pda.core.exception.traveler.NoAccountException;
import com.pda.core.exception.traveler.NoTravelerException;
import com.pda.core.exception.traveler.NotEnoughStockmonException;
import com.pda.core.repository.stock.StockRepository;
import com.pda.core.repository.traveler.AccountRepository;
import com.pda.core.repository.traveler.AccountStockRepository;
import com.pda.core.repository.traveler.TravelerRepository;
import com.pda.core.repository.traveler.TravelerStockmonRepository;
import com.pda.core.utils.RandomUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final Integer STANDARD_PRICE = 1000;
    private final AccountRepository accountRepository;
    private final TravelerRepository travelerRepository;
    private final TravelerStockmonRepository travelerStockmonRepository;
    private final StockFeignClient stockFeignClient;
    private final AccountStockRepository accountStockRepository;
    private final StockRepository stockRepository;



    @Transactional
    public AccountStockResponseDto createAccount(Long travelerId) {
        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow(NoTravelerException::new);

        if (traveler.getAccount() != null) {
            throw new HasAccountException();
        }

        String accountNumber = generateAccountNumber();

        AccountDto accountDto = AccountDto.builder()
                .accountNumber(accountNumber)
                .build();

        Account account = accountDto.toEntity();
        Account savedAccount = accountRepository.save(account);
        traveler.setAccount(savedAccount);

        Stock stock = stockRepository.findById(RandomUtil.createRandomInt(1,59)).orElseThrow(NoStockException::new);

        accountStockRepository.save(AccountStock.builder()
                .account(savedAccount)
                .stock(stock)
                .stockCount(1.0)
                .build());
        travelerRepository.save(traveler);

        return new AccountStockResponseDto(stock.getId(), stock.getName(), stock.getCode());
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        accountNumber = sb.toString();
        accountNumber = accountNumber.substring(0, 4) + "-" +
                accountNumber.substring(4, 8) + "-" +
                accountNumber.substring(8, 12);

        return accountNumber;
    }


    public AccountCheckResponseDto hasAccount(Long travelerId) {
        boolean hasAccount = travelerRepository.findById(travelerId)
                .map(traveler -> traveler.getAccount() != null)
                .orElse(false);

        return new AccountCheckResponseDto(hasAccount);
    }

    public HasAccountResponseDto getUserProfile(Long travelerId) {
        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow(NoTravelerException::new);
        return HasAccountResponseDto.fromTraveler(traveler);
    }

    @Transactional
    public void changeRealStock(Long travelerId, String code) {
        long currentPrice = stockFeignClient.getCurrentPrice(code).getBody();
        double stockCount = 1.0 / (double) (currentPrice / STANDARD_PRICE);

        Stock stock = stockRepository.findByCode(code).orElseThrow(NoStockException::new);
        Account account = accountRepository.findByTravelerId(travelerId).orElseThrow(NoAccountException::new);
        TravelerStockmon travelerStockmon = travelerStockmonRepository.findByTravelerIdAndStockmonId(travelerId, stock.getId()).orElseThrow(NotEnoughStockmonException::new);

        long stockmonCount = travelerStockmon.getStockmonCount();
        if(stockmonCount < 20) {
            throw new NotEnoughStockmonException();
        }

        if(stockmonCount == 20) {
            travelerStockmon.setStockmonAveragePrice(0.0);
        }

        travelerStockmonRepository.updateStockmonCountById(travelerStockmon.getId(), stockmonCount - 20, travelerStockmon.getStockmonAveragePrice());

        if (accountStockRepository.existsAccountStockByAccountIdAndStockId(account.getId(), stock.getId())) {
            accountStockRepository.updateStock(account.getId(), stock.getId(), stockCount);
        } else {
            accountStockRepository.save(AccountStock.builder()
                    .account(account)
                    .stock(stock)
                    .stockCount(stockCount)
                    .build());
        }
    }

}
