package ru.sladkkov.gasstation.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class NegativeResponse<T> extends Response {

    protected final T code;

    private final String message;


    NegativeResponse(T code, String message) {
        super(ResponseCode.FAIL);
        this.code = code;
        this.message = message;
    }

    public ResponseCode getResult() {
        return super.getResult();

    }
}

