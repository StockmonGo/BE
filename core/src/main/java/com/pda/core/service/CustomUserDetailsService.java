package com.pda.core.service;

import com.pda.core.dto.CustomUserDetails;
import com.pda.core.entity.Traveler;
import com.pda.core.repository.TravelerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private final TravelerRepository travelerRepository;

    public CustomUserDetailsService(TravelerRepository travelerRepository){
        this.travelerRepository = travelerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        Traveler traveler = travelerRepository.findByNickname(nickname);

        System.out.println(nickname);

        if(traveler != null){
            return new CustomUserDetails(traveler);
        }

        return null;
    }
}