package com.pda.commons.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class SuccessResponse<T> {
    private final String result = "success";
    private final String message;
    private final T data;
    private final Date timestamp;

}
