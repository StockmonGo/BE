package com.pda.core.dto.notice.alliance;

import java.time.LocalDateTime;

public interface GetAllianceNoticeListResponseDto {
    Long getNoticeId();
    String getNickName();
    LocalDateTime getCreatedAt();
}
