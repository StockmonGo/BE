package com.pda.core.service.notice;

import com.pda.core.dto.traveler.alliances.GetTravelerAllianceListResponseDto;
import com.pda.core.dto.notice.alliance.AllianceNoticeDto;
import com.pda.core.dto.notice.alliance.GetAllianceNoticeListResponseDto;
import com.pda.core.dto.traveler.alliances.AllianceDto;
import com.pda.core.dto.traveler.alliances.TravelerAllianceDto;
import com.pda.core.entity.traveler.Alliance;
import com.pda.core.entity.notice.AllianceNotice;
import com.pda.core.entity.traveler.Traveler;
import com.pda.core.entity.traveler.TravelerAlliance;
import com.pda.core.exception.traveler.NoTravelerException;
import com.pda.core.repository.notice.AllianceNoticeRepository;
import com.pda.core.repository.notice.AllianceRepository;
import com.pda.core.repository.traveler.TravelerAllianceRepository;
import com.pda.core.repository.traveler.TravelerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AllianceService {
    private final TravelerAllianceRepository travelerAllianceRepository;
    private final AllianceNoticeRepository allianceNoticeRepository;

    private final AllianceRepository allianceRepository;
    private final TravelerRepository travelerRepository;

    public AllianceService(AllianceNoticeRepository allianceNoticeRepository, TravelerRepository travelerRepository,
                           TravelerAllianceRepository travelerAllianceRepository,AllianceRepository allianceRepository){
        this.allianceNoticeRepository = allianceNoticeRepository;
        this.travelerRepository=travelerRepository;
        this.travelerAllianceRepository = travelerAllianceRepository;
        this.allianceRepository = allianceRepository;
    }




    @Transactional
    public List<GetTravelerAllianceListResponseDto> getAlliances(Long travelerId){
        List<GetTravelerAllianceListResponseDto> travelerAllianceList =
                travelerAllianceRepository.findTravelerAllianceDataByTravelerId(travelerId).orElseThrow();


        return travelerAllianceList;

    }

    public List<GetAllianceNoticeListResponseDto> getAllianceNoticeList(Long travelerId){
        List<GetAllianceNoticeListResponseDto> getAllianceNoticeListResponseDto =
                allianceNoticeRepository.findAllianceNoticeListByTravelerId(travelerId).orElseThrow();

        return getAllianceNoticeListResponseDto;
    }

    @Transactional
    public Boolean createAllianceRequest(Long senderId, Long receiverId){
        Traveler senderTraveler = travelerRepository.findById(senderId)
                .orElseThrow(NoTravelerException::new);

        Traveler receiverTraveler = travelerRepository.findById(receiverId)
                .orElseThrow(NoTravelerException::new);

        AllianceNoticeDto allianceNoticeDto = AllianceNoticeDto
                .builder()
                .sender(senderTraveler)
                .receiver(receiverTraveler)
                .build();
        AllianceNotice allianceNotice = allianceNoticeDto.toEntity();

        AllianceNotice savedAllianceNotice = allianceNoticeRepository.save(allianceNotice);

        return true;
    }

    @Transactional
    public Boolean acceptAlliance(Long senderId, Long noticeId) {
        // AllianceNotice를 찾아옵니다.
        AllianceNotice allianceNotice = allianceNoticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice ID"));

        // Sender와 Receiver를 가져옵니다.
        Traveler sender = allianceNotice.getSender();
        Traveler receive = allianceNotice.getReceiver();

        // Alliance를 먼저 생성합니다.
        Alliance alliance = AllianceDto.builder().build().toEntity();
        allianceRepository.save(alliance);

        // TravelerAlliance 객체들을 생성하고 Alliance를 설정합니다.
        TravelerAlliance senderTravelerAlliance = TravelerAllianceDto
                .builder()
                .traveler(sender)
                .alliance(alliance)  // Alliance 설정
                .build().toEntity();

        TravelerAlliance receiveTravelerAlliance = TravelerAllianceDto
                .builder()
                .traveler(receive)
                .alliance(alliance)  // Alliance 설정
                .build().toEntity();

        // TravelerAlliance 리스트에 추가
        List<TravelerAlliance> travelerAlliances = new ArrayList<>();
        travelerAlliances.add(senderTravelerAlliance);
        travelerAlliances.add(receiveTravelerAlliance);

        // TravelerAlliance를 저장합니다.
        travelerAllianceRepository.saveAll(travelerAlliances);
        allianceNoticeRepository.deleteById(noticeId);

        return true;
    }

    @Transactional
    public Boolean rejectAlliance(Long noticeId){
        allianceNoticeRepository.deleteById(noticeId);
        return true;
    }



}
