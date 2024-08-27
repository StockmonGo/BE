package com.pda.commons.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FailResponse {
    private final String result = "fail";
    private final String message;
    private final Date timestamp;

}
