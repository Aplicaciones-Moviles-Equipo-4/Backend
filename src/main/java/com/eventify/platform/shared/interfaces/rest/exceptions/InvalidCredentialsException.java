package com.eventify.platform.shared.interfaces.rest.exceptions;

/**
 * Thrown when authentication fails because the username does not exist or the password is wrong.
 * Mapped to HTTP 401 Unauthorized by {@link GlobalExceptionHandler}. The message is intentionally
 * generic so the response cannot be used to enumerate valid usernames.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
