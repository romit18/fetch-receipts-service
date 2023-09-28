package com.fetch.exceptions;

public class FetchServiceBadRequestException extends RuntimeException {
    public FetchServiceBadRequestException(String message) {
        super(message);
    }
}

