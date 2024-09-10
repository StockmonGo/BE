package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughStockmonException extends CoreException{

    private static final String NOT_ENOUGH_STOCKMON = "충분한 스톡몬이 없음";
    public NotEnoughStockmonException() {
        super(HttpStatus.BAD_REQUEST, NOT_ENOUGH_STOCKMON);
    }
}
