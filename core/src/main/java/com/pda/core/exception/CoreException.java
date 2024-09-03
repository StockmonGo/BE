package com.pda.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoreException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CoreException(HttpStatus httpStatus, String message) {
       super(message);
        this.httpStatus = httpStatus;
    }

}
