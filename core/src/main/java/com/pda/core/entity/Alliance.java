package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Alliance {

    @Id
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    @OneToMany(mappedBy = "alliance")
    @JsonIgnoreProperties("alliance")
    private List<TravelerAlliance> travelerAlliances = new ArrayList<>();

}
