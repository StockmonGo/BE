package com.pda.core.exception;


import org.springframework.http.HttpStatus;

public class HasAccountException extends CoreException{
    public HasAccountException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
