package com.bibliotheque.exceptions;

import lombok.Getter;

public class HttpResponseErrorException extends Exception {
    @Getter
    int statusCode;
    public HttpResponseErrorException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
