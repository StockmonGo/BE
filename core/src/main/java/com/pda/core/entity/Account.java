package com.pda.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
//@NoArgsConstructor
public class Account {

    @Id
    private Long id;

    private String accountNumber;

    @OneToMany(mappedBy = "account")
    @JsonIgnoreProperties("account")
    private List<AccountStock> accountStocks = new ArrayList<>();

    public Account() {
    }

}