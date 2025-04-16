package com.calorietracker;

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

    public FileStorageImpl() {
        this.dataDirectory = "data";
        createDataDirectoryIfNotExists();
    }

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

    @Override
    public void saveToFile(String data, String filename) {
        Path filePath = Paths.get(dataDirectory, filename);
        try {
            Files.write(filePath, data.getBytes());
        } catch (IOException e) {
            throw new StorageException("Failed to save file: " + filename, e);
        }
    }

    @Override
    public Optional<String> readFromFile(String filename) {
        Path filePath = Paths.get(dataDirectory, filename);
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
    public List<String> readAllLines(String filename) {
        Path filePath = Paths.get(dataDirectory, filename);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new StorageException("Failed to read lines from file: " + filename, e);
        }
    }

    @Override
    public boolean fileExists(String filename) {
        Path filePath = Paths.get(dataDirectory, filename);
        return Files.exists(filePath);
    }

    @Override
    public boolean deleteFile(String filename) {
        Path filePath = Paths.get(dataDirectory, filename);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + filename, e);
        }
    }
}