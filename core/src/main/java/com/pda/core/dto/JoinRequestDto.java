package com.pda.core.dto;

import com.pda.core.entity.Traveler;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class JoinRequestDto {
    private static final String OVER_MAX_NICKNAME = "닉네임 길이 초과";

    @Max(value = 10, message = OVER_MAX_NICKNAME)
    private final String nickname;
    private final String password;
    @Max(value = 10, message = OVER_MAX_NICKNAME)
    private final String inviterNickname;

    public Traveler toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Traveler.builder()
                .role("ROLE_USER")
                .nickname(nickname)
                .password(bCryptPasswordEncoder.encode(password))
                .stockballCount(20L)
                .tutorialWatched(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
