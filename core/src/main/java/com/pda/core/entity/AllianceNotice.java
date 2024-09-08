package com.pda.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllianceNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Traveler sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Traveler receiver;

    @JoinColumn(name = "created_at")
    private LocalDateTime createdAt;

}
