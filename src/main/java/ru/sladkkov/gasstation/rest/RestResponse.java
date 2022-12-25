package ru.sladkkov.gasstation.rest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RestResponse {
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public static NegativeResponse<String> negativeResponse(String code, String errorMessage) {
        return new NegativeResponse<>(code, errorMessage);
    }
}
