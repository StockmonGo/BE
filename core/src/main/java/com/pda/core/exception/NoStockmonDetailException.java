package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NoStockmonDetailException extends CoreException{

    public NoStockmonDetailException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
