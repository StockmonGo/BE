package com.pda.core.exception;


import org.springframework.http.HttpStatus;

public class SecurityFilterException extends CoreException{

    private static final String REQUEST_BODY = "요청 바디를 다시 확인해주세요.";

    public SecurityFilterException() {
        super(HttpStatus.BAD_REQUEST, REQUEST_BODY);
    }

}
