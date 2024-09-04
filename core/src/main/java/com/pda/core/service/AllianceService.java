package com.pda.core.service;

import com.pda.core.dto.alliances.GetTravelerAlliancesListResponseDto;
import com.pda.core.repository.AllianceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AllianceService {

    private final AllianceRepository alliancesRepository;

    public AllianceService(AllianceRepository alliancesRepository){
        this.alliancesRepository = alliancesRepository;
    }


    @Transactional
    public List<GetTravelerAlliancesListResponseDto> getAlliances(Long allianceId){
        List<GetTravelerAlliancesListResponseDto> travelerAllianceList = alliancesRepository.findNicknamesByAllianceId(1L).orElseThrow();

        return travelerAllianceList;

    }
}
