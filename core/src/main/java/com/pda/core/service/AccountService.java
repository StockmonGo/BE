package com.pda.core.service;


import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.AccountCheckResponseDto;
import com.pda.core.dto.AccountDto;
import com.pda.core.dto.HasAccountResponseDto;
import com.pda.core.entity.*;
import com.pda.core.exception.*;
import com.pda.core.repository.*;
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
    public Account createAccount(Long travelerId) {
        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow(NoTravelerException::new);

        if (traveler.getAccount() != null) {
            throw new HasAccountException();
        }

        String accountNumber;
        do {
            accountNumber = generateAccountNumber();
        } while (!accountRepository.existsByAccountNumber(accountNumber));

        AccountDto accountDto = AccountDto.builder()
                .accountNumber(generateAccountNumber())
                .build();

        Account account = accountDto.toEntity();
        Account savedAccount = accountRepository.save(account);

        traveler.setAccount(savedAccount);
        travelerRepository.save(traveler);


        return savedAccount;
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

        if(travelerStockmon.getStockmonCount() < 20) {
            throw new NotEnoughStockmonException();
        }

        travelerStockmonRepository.updateStockmonCountById(travelerStockmon.getId(), travelerStockmon.getStockmonCount() - 20);

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
