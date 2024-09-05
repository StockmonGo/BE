package com.pda.core.repository;

import com.pda.core.dto.alliances.GetTravelerAlliancesListResponseDto;
import com.pda.core.entity.TravelerAlliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelerAllianceRepository extends JpaRepository<TravelerAlliance, Long> {

    @Query("SELECT DISTINCT t.id, t.nickname FROM TravelerAlliance ta1 " +
            "JOIN TravelerAlliance ta2 ON ta1.alliance.id = ta2.alliance.id " +
            "AND ta1.traveler.id <> ta2.traveler.id " +
            "JOIN Traveler t ON ta2.traveler.id = t.id " +
            "WHERE ta1.traveler.id = :id")
    List<Object[]> findTravelerAllianceDataByTravelerId(Long id);

    default Optional<List<GetTravelerAlliancesListResponseDto>> findNicknamesByAllianceId(Long id) {
        List<Object[]> results = findTravelerAllianceDataByTravelerId(id);

        if (results.isEmpty()) {
            return Optional.empty();
        }

        List<GetTravelerAlliancesListResponseDto> dtos = results.stream()
                .map(result -> new GetTravelerAlliancesListResponseDto(
                        ((Number) result[0]).longValue(), // travelerId
                        (String) result[1] // nickname
                ))
                .distinct()  // 중복 제거
                .toList();

        return Optional.of(dtos);
    }
}
