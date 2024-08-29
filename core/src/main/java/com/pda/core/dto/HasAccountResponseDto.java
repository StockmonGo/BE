package com.pda.core.dto;

import com.pda.core.entity.Traveler;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class HasAccountResponseDto {
    private String nickname;
    private Boolean hasAccount;
    private String accountNumber;

    @Builder
    public HasAccountResponseDto(String nickname, Boolean hasAccount, String accountNumber) {
        this.nickname = nickname;
        this.hasAccount = hasAccount;
        this.accountNumber = accountNumber;
    }

    public static HasAccountResponseDto fromTraveler(Traveler traveler) {
        return HasAccountResponseDto.builder()
                .nickname(traveler.getNickname())
                .hasAccount(traveler.getAccount() != null)
                .accountNumber(traveler.getAccount() != null ? traveler.getAccount().getAccountNumber() : null)
                .build();
    }

}
