package com.pda.core.dto.alliance_notice;

import com.pda.core.entity.AllianceNotice;
import com.pda.core.entity.Traveler;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class AllianceNoticeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Traveler sender;

    private Traveler receiver;

    public AllianceNotice toEntity(){
        return AllianceNotice.builder()
                .id(id)
                .sender(sender)
                .receiver(receiver)
                .createdAt(LocalDateTime.now())
                .build();
    }


}
