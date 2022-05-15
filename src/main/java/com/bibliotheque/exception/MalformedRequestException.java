package com.bibliotheque.exception;

public class MalformedRequestException extends Exception {
    private static final Long serialVersionUID = 1L;
    public MalformedRequestException(String message) {
        super(message);
    }
}
