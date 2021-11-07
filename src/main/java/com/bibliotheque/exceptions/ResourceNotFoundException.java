package com.bibliotheque.exceptions;

import java.util.NoSuchElementException;

public class ResourceNotFoundException extends NoSuchElementException {
    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
