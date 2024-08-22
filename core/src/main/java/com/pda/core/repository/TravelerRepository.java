package com.pda.core.repository;

import com.pda.core.entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    Boolean existsByNickname(String nickname);

    Traveler findByNickname(String nickname);

}
