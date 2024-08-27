package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NoTravelerException extends CoreException {

    public NoTravelerException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
