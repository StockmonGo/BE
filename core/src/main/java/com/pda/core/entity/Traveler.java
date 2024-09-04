package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=10)
    private String nickname;

    private String password;

    private String role;

    private Long stockballCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    @Setter
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    @Builder.Default
    private List<TravelerAlliance> travelerAlliances = new ArrayList<>();

    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    @Builder.Default
    private List<TravelerStockmon> travelerStockmons = new ArrayList<>();

    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    @Builder.Default
    private List<ExchangeNotice> exchangeNotices = new ArrayList<>();

    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    @Builder.Default
    private List<AllianceNotice> allianceNotices = new ArrayList<>();

    public Traveler(Long id) {
        this.id = id;
    }

}