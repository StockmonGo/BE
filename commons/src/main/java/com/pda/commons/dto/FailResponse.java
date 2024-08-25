package com.pda.commons.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class FailResponse {
    private final String result = "fail";
    private final String message;
    private final Date timestamp;

}
