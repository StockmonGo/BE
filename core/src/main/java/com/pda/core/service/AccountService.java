package com.pda.core.service;

import static com.pda.core.exception.ExceptionMessage.HAS_ACCOUNT;
import static com.pda.core.exception.ExceptionMessage.NO_TRAVELER;

import com.pda.core.dto.AccountCheckResponseDto;
import com.pda.core.dto.AccountDto;
import com.pda.core.dto.HasAccountResponseDto;
import com.pda.core.entity.Account;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.HasAccountException;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.AccountRepository;
import com.pda.core.repository.TravelerRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TravelerRepository travelerRepository;

    public AccountService(AccountRepository accountRepository, TravelerRepository travelerRepository){
        this.accountRepository=accountRepository;
        this.travelerRepository=travelerRepository;
    }

    public Account createAccount(Long travelerId){
        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow(() -> new NoTravelerException(
                HttpStatus.BAD_REQUEST, NO_TRAVELER));

        if (traveler.getAccount() != null) {
            throw new HasAccountException(HttpStatus.BAD_REQUEST, HAS_ACCOUNT);
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
                .orElseThrow(() -> new NoTravelerException(
                        HttpStatus.BAD_REQUEST, NO_TRAVELER));
        return HasAccountResponseDto.fromTraveler(traveler);
    }

    private String generateAccountNumber() {
        // TODO: 어떤 형식으로 계좌를 발급할지 고민
        return "000-" + UUID.randomUUID().toString().substring(0, 8);
    }

}
