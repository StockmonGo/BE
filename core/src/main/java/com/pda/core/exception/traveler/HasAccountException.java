package com.pda.core.exception.traveler;

import com.pda.core.exception.CoreException;
import org.springframework.http.HttpStatus;

public class HasAccountException extends CoreException {

    public static final String HAS_ACCOUNT = "이미 계좌가 존재";

    public HasAccountException() {
        super(HttpStatus.BAD_REQUEST, HAS_ACCOUNT);
    }

}
