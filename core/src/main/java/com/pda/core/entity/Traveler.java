package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Traveler {

    @Id
    private Long id;

    @Size(max=10)
    private String nickname;

    private String password;

    private String role;

    private Long stockballCount;

    private Date createdAt;

    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "traveler")
    @JsonIgnoreProperties("traveler")
    private List<TravelerAlliance> travelerAlliances = new ArrayList<>();
}
