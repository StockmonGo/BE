package com.pda.core.exception.traveler;

import com.pda.core.exception.CoreException;
import org.springframework.http.HttpStatus;

public class DuplicateTravelerException extends CoreException {

    private static final String DUPLICATE_NICKNAME = "닉네임 중복";

    public DuplicateTravelerException() {
        super(HttpStatus.BAD_REQUEST, DUPLICATE_NICKNAME);
    }
}
