package com.calorietracker.core.storage;

import java.util.List;
import java.util.Optional;

/**
 * Interface for handling file storage operations.
 * Follows Single Responsibility Principle by focusing only on file operations.
 */
public interface IFileStorage {
    /**
     * Saves data to a file.
     * @param data The data to save
     * @param filename The name of the file to save to
     * @throws StorageException if there's an error during save
     */
    void saveToFile(String data, String filename);

    /**
     * Reads data from a file.
     * @param filename The name of the file to read from
     * @return Optional containing the file contents if successful, empty if file doesn't exist
     * @throws StorageException if there's an error during read
     */
    Optional<String> readFromFile(String filename);

    /**
     * Appends data to an existing file.
     * @param data The data to append
     * @param filename The name of the file to append to
     * @throws StorageException if there's an error during append
     */
    void appendToFile(String data, String filename);

    /**
     * Reads all lines from a file.
     * @param filename The name of the file to read from
     * @return List of lines from the file
     * @throws StorageException if there's an error during read
     */
    List<String> readAllLines(String filename);

    /**
     * Checks if a file exists.
     * @param filename The name of the file to check
     * @return true if file exists, false otherwise
     */
    boolean fileExists(String filename);
}