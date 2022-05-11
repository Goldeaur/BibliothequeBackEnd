package com.bibliotheque.exception;

import java.util.NoSuchElementException;

public class ResourceNotFoundException extends NoSuchElementException {
    private static final Long serialVersionUID = 1L;
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
