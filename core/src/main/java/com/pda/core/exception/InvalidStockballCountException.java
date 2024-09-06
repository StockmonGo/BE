package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidStockballCountException extends CoreException{

    private static final String INVALID_STOCKBALL = "비정상적인 스톡볼 사용";
    
    public InvalidStockballCountException() {
        super(HttpStatus.BAD_REQUEST, INVALID_STOCKBALL);
    }
}
