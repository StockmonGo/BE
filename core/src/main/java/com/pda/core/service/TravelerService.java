package com.pda.core.service;


import com.pda.core.dto.GetTravelerNicknameResponseDto;
import com.pda.core.dto.JoinRequestDto;
import com.pda.core.dto.JoinResponseDto;
import com.pda.core.entity.Traveler;
import com.pda.core.exception.DuplicateTravelerException;
import com.pda.core.exception.NoTravelerException;
import com.pda.core.repository.TravelerRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelerService {

    private final TravelerRepository travelerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final long INVITER_PRESENT = 20;

    @Transactional
    public Optional<GetTravelerNicknameResponseDto> findTravelerByNickname(String nickname) {
        return travelerRepository.findByNickname(nickname)
                .map(GetTravelerNicknameResponseDto::fromEntity);
    }

    @Transactional
    public Traveler getTravelerById(Long travelerId) {
        return travelerRepository.findById(travelerId)
                .orElseThrow(NoTravelerException::new);
    }

    @Transactional
    public JoinResponseDto createTraveler(JoinRequestDto joinRequestDTO){
        String nickname = joinRequestDTO.getNickname();
        String inviterNickname = joinRequestDTO.getInviterNickname();

        if (travelerRepository.existsByNickname(nickname)) throw new DuplicateTravelerException();

        Traveler traveler = travelerRepository.save(joinRequestDTO.toEntity(bCryptPasswordEncoder));

        // TODO : 일단 초대인 보상으로 스톡볼 20개 더 주는 걸로 하긴 했는데.. 얘기 좀 더 해봐야할 듯
        if(inviterNickname != null) {
                travelerRepository.addStockball(inviterNickname, INVITER_PRESENT);
        }
        return JoinResponseDto.fromEntity(traveler);
    }

    @Transactional
    public void remove(Long travelerId) {
        travelerRepository.deleteById(travelerId);
    }

}
