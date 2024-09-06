package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NoStockmonException extends CoreException {

    public static final String NO_STOCKMON = "보유하지 않은 스톡몬";

    public NoStockmonException(){
        super(HttpStatus.BAD_REQUEST, NO_STOCKMON);
    }
}
