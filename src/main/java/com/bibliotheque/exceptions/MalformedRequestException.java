package com.bibliotheque.exceptions;

public class MalformedRequestException extends Exception {
    private static final long serialVersionUID = 1L;
    public MalformedRequestException(String message) {
        super(message);
    }
}
