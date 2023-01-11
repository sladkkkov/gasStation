package ru.sladkkov.gasstation.exception;

public class TopologyNotFoundException extends RuntimeException {
    public TopologyNotFoundException() {
        super();
    }

    public TopologyNotFoundException(String message) {
        super(message);
    }
}
