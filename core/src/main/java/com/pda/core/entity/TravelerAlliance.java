package com.pda.core.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class TravelerAlliance {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "traveler_id")
    private Traveler traveler;

    @ManyToOne
    @JoinColumn(name = "alliance_id")
    private Alliance alliance;

}
