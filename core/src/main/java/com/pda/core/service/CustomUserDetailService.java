package com.pda.core.service;


import com.pda.core.dto.CustomUserDetailDto;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.TravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final TravelerRepository travelerRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Traveler traveler = travelerRepository.findByNickname(nickname).orElseThrow(NoTravelerException::new);

        return new CustomUserDetailDto(traveler);
    }
}
