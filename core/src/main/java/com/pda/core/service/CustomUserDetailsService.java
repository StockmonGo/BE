package com.pda.core.service;

import com.pda.core.dto.CustomUserDetails;
import com.pda.core.dto.LoginDTO;
import com.pda.core.entity.Traveler;
import com.pda.core.repository.TravelerRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final TravelerRepository travelerRepository;

    public CustomUserDetailsService(TravelerRepository travelerRepository){
        this.travelerRepository = travelerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        Traveler traveler = travelerRepository.findByNickname(nickname).orElseThrow();

        System.out.println(nickname);

        if(traveler != null){
            return new CustomUserDetails(traveler);
        }

        return null;
    }
}