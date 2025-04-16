package com.calorietracker.core.storage;

/**
 * Custom exception class for handling storage-related errors.
 */
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}