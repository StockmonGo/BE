package com.pda.core.repository.traveler;

import com.pda.core.dto.traveler.alliances.GetTravelerAllianceListResponseDto;
import com.pda.core.entity.traveler.TravelerAlliance;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelerAllianceRepository extends JpaRepository<TravelerAlliance, Long> {

    @Query("SELECT DISTINCT t.id AS travelerId, t.nickname AS nickname FROM TravelerAlliance ta1 " +
            "JOIN TravelerAlliance ta2 ON ta1.alliance.id = ta2.alliance.id " +
            "AND ta1.traveler.id <> ta2.traveler.id " +
            "JOIN Traveler t ON ta2.traveler.id = t.id " +
            "WHERE ta1.traveler.id = :id")
    Optional<List<GetTravelerAllianceListResponseDto>> findTravelerAllianceDataByTravelerId(@Param("id") Long id);
}
