package com.pda.core.exception.traveler;

import com.pda.core.exception.CoreException;
import org.springframework.http.HttpStatus;

public class NoTravelerException extends CoreException {

    public static final String NO_TRAVELER = "사용자 없음";

    public NoTravelerException() {
        super(HttpStatus.BAD_REQUEST, NO_TRAVELER);
    }

}
