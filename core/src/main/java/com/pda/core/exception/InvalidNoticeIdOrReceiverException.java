package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidNoticeIdOrReceiverException extends CoreException {

    public static final String INVALID_NOTICE = "유효하지 않은 알림";

    public InvalidNoticeIdOrReceiverException() {
        super(HttpStatus.BAD_REQUEST, INVALID_NOTICE);
    }

}
