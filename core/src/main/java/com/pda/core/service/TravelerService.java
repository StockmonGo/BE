package com.pda.core.service;

import com.pda.core.dto.GetTravelerNicknameResponseDto;
import com.pda.core.repository.TravelerRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TravelerService {

    private final TravelerRepository travelerRepository;

    public TravelerService(TravelerRepository travelerRepository){
        this.travelerRepository = travelerRepository;
    }

    @Transactional
    public Optional<GetTravelerNicknameResponseDto> findTravelerByNickname(String nickname) {
        return travelerRepository.findByNickname(nickname)
                .map(GetTravelerNicknameResponseDto::fromEntity);
    }


}
