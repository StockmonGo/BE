package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class CoolDownException extends CoreException {

    public static final String COOL_DOWN = "조금 이따 돌려주세요";

    public CoolDownException() {
        super(HttpStatus.BAD_REQUEST, COOL_DOWN);
    }

}
