package com.pda.core.dto.traveler.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignInRequestDto {
    private String nickname;
    private String password;
}
