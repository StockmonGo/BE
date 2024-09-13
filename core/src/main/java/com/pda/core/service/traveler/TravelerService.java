package com.pda.core.service.traveler;


import com.pda.core.dto.traveler.user.GetTravelerNicknameResponseDto;
import com.pda.core.dto.traveler.stockball.GetTravelerStockballsResponseDto;
import com.pda.core.dto.traveler.user.JoinRequestDto;
import com.pda.core.dto.traveler.user.JoinResponseDto;
import com.pda.core.entity.traveler.Traveler;
import com.pda.core.exception.traveler.DuplicateTravelerException;
import com.pda.core.exception.traveler.NoTravelerException;
import com.pda.core.repository.traveler.TravelerRepository;
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
    private static final long MAX_BALL = 50;

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

        if(inviterNickname != null) {
                travelerRepository.findByNickname(inviterNickname).ifPresent(traveler1 -> {
                    travelerRepository.addStockball(inviterNickname, Math.min(INVITER_PRESENT, MAX_BALL - traveler1.getStockballCount()));
                });
        }
        return JoinResponseDto.fromEntity(traveler);
    }

    @Transactional
    public void remove(Long travelerId) {
        travelerRepository.deleteById(travelerId);
    }

    @Transactional
    public GetTravelerStockballsResponseDto getTravelerStockballsById(Long travelerId) {
        Long stockballCount = travelerRepository.findStockballCountById(travelerId)
                .orElseThrow((NoTravelerException::new));

        return new GetTravelerStockballsResponseDto(stockballCount);

    }

    public void setTutorialWatched(Long travelerId) {
        Traveler traveler = travelerRepository.findById(travelerId)
                .orElseThrow((NoTravelerException::new));

        traveler.setTutorialWatched(true);
        travelerRepository.save(traveler);
    }

}
