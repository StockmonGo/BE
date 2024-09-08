package com.pda.core.dto.alliance_notice;

import java.time.LocalDateTime;

public interface GetAllianceNoticeListResponseDto {
    Long getNoticeId();
    String getNickName();
    LocalDateTime getCreatedAt();
}
