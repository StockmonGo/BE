package com.pda.core.dto.traveler.account;

import com.pda.core.entity.traveler.Traveler;
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
    private Boolean tutorialWatched;

    @Builder
    public HasAccountResponseDto(String nickname, Boolean hasAccount, String accountNumber,Boolean tutorialWatched) {
        this.nickname = nickname;
        this.hasAccount = hasAccount;
        this.accountNumber = accountNumber;
        this.tutorialWatched = tutorialWatched;
    }

    public static HasAccountResponseDto fromTraveler(Traveler traveler) {
        return HasAccountResponseDto.builder()
                .nickname(traveler.getNickname())
                .hasAccount(traveler.getAccount() != null)
                .accountNumber(traveler.getAccount() != null ? traveler.getAccount().getAccountNumber() : null)
                .tutorialWatched(traveler.getTutorialWatched())
                .build();
    }

}
