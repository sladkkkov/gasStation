package ru.sladkkov.gasstation.rest;

import java.io.Serializable;

public abstract class Response implements Serializable {

    private final ResponseCode result;

    Response(ResponseCode result) {
        this.result = result;
    }

    public ResponseCode getResult() {
        return result;
    }

}