package com.pda.core.service;

import com.pda.core.dto.JoinDTO;
import com.pda.core.entity.Traveler;
import com.pda.core.repository.TravelerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final TravelerRepository travelerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(TravelerRepository travelerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.travelerRepository = travelerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO){
        String nickname = joinDTO.getNickname();
        String password = joinDTO.getPassword();

        Boolean isExist = travelerRepository.existsByNickname(nickname);
        if (isExist) {
            return;
        }

        Traveler traveler = new Traveler();

        traveler.setNickname(nickname);
        traveler.setPassword(password);
        traveler.setRole("ROLE_USER");
        travelerRepository.save(traveler);
    }
}
