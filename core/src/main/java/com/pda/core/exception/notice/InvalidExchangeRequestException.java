package com.pda.core.exception.notice;

import com.pda.core.exception.CoreException;
import org.springframework.http.HttpStatus;

public class InvalidExchangeRequestException extends CoreException {

    public static final String INVALID_REQUEST = "유효하지 않은 요청";

    public InvalidExchangeRequestException() {
        super(HttpStatus.BAD_REQUEST, INVALID_REQUEST);
    }

}
