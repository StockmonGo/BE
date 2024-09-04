package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Alliance {

    @Id
    private Long id;

    @OneToMany(mappedBy = "alliance")
    @JsonIgnoreProperties("alliance")
    private List<TravelerAlliance> travelerAlliances = new ArrayList<>();

}