package com.pda.core.repository;

import com.pda.core.entity.Traveler;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    Boolean existsByNickname(String nickname);
    Optional<Traveler> findByNickname(String nickname);

}
