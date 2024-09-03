package com.pda.core.exception;

import org.springframework.http.HttpStatus;

public class UserNamePasswordException extends CoreException{

    private static final String NOT_CORRECT_USERNAME_PASSWORD = "비밀번호 불일치 | 아이디 불일치";

    public UserNamePasswordException() {
        super(HttpStatus.UNAUTHORIZED, NOT_CORRECT_USERNAME_PASSWORD);
    }

}
