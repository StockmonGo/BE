package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class NoStockmonDetailException extends CoreException{

    public static final String NO_STOCKMONDETAIL = "스톡몬 상세 정보 없음";

    public NoStockmonDetailException() {
        super(HttpStatus.BAD_REQUEST, NO_STOCKMONDETAIL);
    }

}
