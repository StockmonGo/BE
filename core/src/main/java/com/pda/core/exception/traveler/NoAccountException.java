package com.pda.core.exception.traveler;

import com.pda.core.exception.CoreException;
import org.springframework.http.HttpStatus;

public class NoAccountException extends CoreException {

    private static final String NO_ACCOUNT = "일치하는 계좌 없음";

    public NoAccountException() {
        super(HttpStatus.BAD_REQUEST, NO_ACCOUNT);
    }
}
