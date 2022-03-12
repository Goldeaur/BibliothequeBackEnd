package com.bibliotheque.exception;

public class UnauthorizedRequestException extends Exception {
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
