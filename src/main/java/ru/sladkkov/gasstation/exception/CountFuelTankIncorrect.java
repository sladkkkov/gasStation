package ru.sladkkov.gasstation.exception;

public class CountFuelTankIncorrect extends RuntimeException {
    public CountFuelTankIncorrect() {
        super();
    }

    public CountFuelTankIncorrect(String message) {
        super(message);
    }
}
