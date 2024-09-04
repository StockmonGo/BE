package com.pda.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllianceNotice {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Traveler sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Traveler receiver;

}
