package com.pda.core.service;


import com.pda.core.client.StockFeignClient;
import com.pda.core.dto.AccountCheckResponseDto;
import com.pda.core.dto.AccountDto;
import com.pda.core.dto.HasAccountResponseDto;
import com.pda.core.entity.Account;
import com.pda.core.entity.AccountStock;
import com.pda.core.entity.Stock;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.HasAccountException;
import com.pda.core.exception.NoAccountException;
import com.pda.core.exception.NoStockException;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.AccountRepository;
import com.pda.core.repository.AccountStockRepository;
import com.pda.core.repository.StockRepository;
import com.pda.core.repository.TravelerRepository;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final TravelerRepository travelerRepository;
    private final StockFeignClient stockFeignClient;
    private final AccountStockRepository accountStockRepository;

    private static final Integer STANDARD_PRICE = 1000;
    private final StockRepository stockRepository;

    public Account createAccount(Long travelerId){
        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow(NoTravelerException::new);

        if (traveler.getAccount() != null) {
            throw new HasAccountException();
        }

        AccountDto accountDto = AccountDto.builder()
                .accountNumber(generateAccountNumber())
                .build();

        Account account = accountDto.toEntity();
        Account savedAccount = accountRepository.save(account);

        traveler.setAccount(savedAccount);
        travelerRepository.save(traveler);

        return savedAccount;
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

    private String generateAccountNumber() {
        // TODO: 어떤 형식으로 계좌를 발급할지 고민
        return "000-" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Transactional
    public void changeRealStock(Long travelerId, String code) {
        long currentPrice = stockFeignClient.getCurrentPrice(code).getBody();
        double stockCount = 1.0 / (double) (currentPrice / STANDARD_PRICE);

        Stock stock = stockRepository.findByCode(code).orElseThrow(NoStockException::new);
        Account account = accountRepository.findByTravelerId(travelerId).orElseThrow(NoAccountException::new);

        if(accountStockRepository.existsAccountStockByAccountIdAndStockId(account.getId(), stock.getId())) {
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
