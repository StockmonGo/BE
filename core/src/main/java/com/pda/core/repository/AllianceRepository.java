package com.pda.core.repository;

import com.pda.core.dto.alliance.GetTravelerAllianceListResponseDto;
import com.pda.core.entity.TravelerAlliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllianceRepository extends JpaRepository<TravelerAlliance, Long> {

    @Query(value = "SELECT t.id, t.nickname FROM traveler_alliance t1 " +
            "INNER JOIN traveler_alliance t2 ON t1.alliance_id = t2.alliance_id " +
            "AND t1.traveler_id != t2.traveler_id " +
            "LEFT JOIN traveler t ON t2.traveler_id = t.id " +
            "WHERE t1.traveler_id = :id", nativeQuery = true)
    List<Object[]> findTravelerAllianceDataByTravelerId(Long id);

    default Optional<List<GetTravelerAllianceListResponseDto>> findNicknamesByAllianceId(Long id) {
        List<Object[]> results = findTravelerAllianceDataByTravelerId(id);
        List<GetTravelerAllianceListResponseDto> dtos = results.stream()
                .map(result -> new GetTravelerAllianceListResponseDto(
                        ((Number) result[0]).longValue(), // travelerId
                        (String) result[1] // nickname
                ))
                .toList();

        return Optional.ofNullable(dtos.isEmpty() ? null : dtos);
    }
}