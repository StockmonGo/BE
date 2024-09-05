package com.pda.core.repository;

import com.pda.core.entity.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllianceRepository extends JpaRepository<Alliance,Long> {
}
