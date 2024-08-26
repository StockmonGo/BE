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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.grammars.hql.HqlParser.LocalDateTimeContext;

@Entity
@Getter
@Setter
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max=10)
    private String nickname;

    private String password;

    private String role;

    private Long stockballCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    private List<TravelerAlliance> travelerAlliances = new ArrayList<>();


    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    private List<TravelerStockmon> travelerStockmons = new ArrayList<>();

}