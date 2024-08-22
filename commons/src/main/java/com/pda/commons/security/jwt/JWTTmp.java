package com.pda.commons.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JWTTmp {
    private final Integer id;
    private final String role;
}
