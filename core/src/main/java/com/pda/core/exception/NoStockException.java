package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NoStockException extends CoreException{

    private static final String NO_STOCK = "일치하는 주식 없음";
    public NoStockException() {
        super(HttpStatus.BAD_REQUEST, NO_STOCK);
    }
}
