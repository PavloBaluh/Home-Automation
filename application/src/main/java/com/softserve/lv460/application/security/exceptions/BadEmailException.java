package com.softserve.lv460.application.security.exceptions;

/**
 * Exception that we get when user by this email not found.
 */
public class BadEmailException extends RuntimeException {
    /**
     * Constructor.
     */
    public BadEmailException(String message) {
        super(message);
    }
}