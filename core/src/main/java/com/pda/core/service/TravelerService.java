package com.pda.core.service;

import static com.pda.core.exception.ExceptionMessage.NO_TRAVELER;

import com.pda.core.dto.GetTravelerNicknameResponseDto;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.TravelerRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.http.HttpStatus;
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

    public Traveler getTravelerById(Long travelerId) {
        return travelerRepository.findById(travelerId)
                .orElseThrow(() -> new NoTravelerException(
                HttpStatus.BAD_REQUEST, NO_TRAVELER));
    }


}
