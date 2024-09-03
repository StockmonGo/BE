package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NoStockTowerException extends CoreException{

    public static final String NO_STOCKTOWER = "스톡타워가 존재하지 않음";

    public NoStockTowerException() {
        super(HttpStatus.BAD_REQUEST, NO_STOCKTOWER);
    }

}
