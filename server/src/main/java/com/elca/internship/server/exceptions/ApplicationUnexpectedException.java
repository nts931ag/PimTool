package com.elca.internship.server.exceptions;

public class ApplicationUnexpectedException extends RuntimeException {
    public ApplicationUnexpectedException(Throwable e) {
        super("Unexpected exception", e);
    }
}
