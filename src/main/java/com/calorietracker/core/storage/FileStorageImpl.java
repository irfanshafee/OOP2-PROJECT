package com.calorietracker.core.storage;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * Implementation of IFileStorage interface for handling file operations.
 */
public class FileStorageImpl implements IFileStorage {
    private final String dataDirectory;

    public FileStorageImpl(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        createDataDirectoryIfNotExists();
    }

    private void createDataDirectoryIfNotExists() {
        try {
            Files.createDirectories(Paths.get(dataDirectory));
        } catch (IOException e) {
            throw new StorageException("Failed to create data directory", e);
        }
    }

    private Path getFilePath(String filename) {
        return Paths.get(dataDirectory, filename);
    }

    @Override
    public void saveToFile(String data, String filename) {
        try {
            Files.write(getFilePath(filename), data.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to save file: " + filename, e);
        }
    }

    @Override
    public Optional<String> readFromFile(String filename) {
        Path filePath = getFilePath(filename);
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        try {
            byte[] bytes = Files.readAllBytes(filePath);
            return Optional.of(new String(bytes));
        } catch (IOException e) {
            throw new StorageException("Failed to read file: " + filename, e);
        }
    }

    @Override
    public void appendToFile(String data, String filename) {
        try {
            Files.write(getFilePath(filename), data.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new StorageException("Failed to append to file: " + filename, e);
        }
    }

    @Override
    public List<String> readAllLines(String filename) {
        try {
            return Files.readAllLines(getFilePath(filename));
        } catch (IOException e) {
            throw new StorageException("Failed to read lines from file: " + filename, e);
        }
    }

    @Override
    public boolean fileExists(String filename) {
        return Files.exists(getFilePath(filename));
    }
}