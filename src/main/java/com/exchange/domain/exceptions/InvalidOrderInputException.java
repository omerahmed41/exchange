package com.exchange.domain.exceptions;

public class InvalidOrderInputException extends RuntimeException {
    public InvalidOrderInputException(String message) {
        super(message);
    }
}
