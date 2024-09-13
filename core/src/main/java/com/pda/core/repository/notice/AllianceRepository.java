package com.pda.core.repository.notice;


import com.pda.core.entity.traveler.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AllianceRepository extends JpaRepository<Alliance,Long> {
}

