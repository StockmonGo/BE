package com.pda.core.repository.notice;

import com.pda.core.dto.notice.alliance.GetAllianceNoticeListResponseDto;
import com.pda.core.entity.notice.AllianceNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllianceNoticeRepository extends JpaRepository<AllianceNotice,Long> {

    @Query("SELECT an.id AS noticeId, t.nickname AS nickName, an.createdAt AS createdAt " +
            "FROM AllianceNotice an " +
            "JOIN Traveler t ON an.sender.id = t.id " +
            "WHERE an.receiver.id = :travelerId")
    Optional<List<GetAllianceNoticeListResponseDto>> findAllianceNoticeListByTravelerId(@Param("travelerId") Long travelerId);
}
