package com.pda.core.dto;

import com.pda.core.entity.Account;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountDto {
    private String accountNumber;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Account toEntity(){
        return Account.builder()
                .accountNumber(this.accountNumber)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
