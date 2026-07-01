package com.eventify.platform.shared.interfaces.rest.exceptions;

/**
 * Thrown when a request conflicts with the current state of a resource,
 * e.g. trying to create a user with a username that already exists.
 * Mapped to HTTP 409 Conflict by {@link GlobalExceptionHandler}.
 */
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
