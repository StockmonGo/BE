package com.pda.core.exception;

import com.pda.commons.dto.FailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CoreExceptionHandler {

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<FailResponse> processCore(CoreException coreException) {
            return ResponseEntity.status(coreException.getHttpStatus())
                    .body(FailResponse.builder()
                            .message(coreException.getMessage())
                            .timestamp(new Date())
                            .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailResponse> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        String errorMessage = methodArgumentNotValidException
                .getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();


        return ResponseEntity
                .badRequest()
                .body(FailResponse
                        .builder()
                        .message(errorMessage)
                        .timestamp(new Date())
                        .build());
    }


}
