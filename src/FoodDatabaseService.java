package com.calorietracker.service;

import com.calorietracker.core.storage.IFileStorage;
import com.calorietracker.model.Food;
import java.util.*;
import java.util.stream.Collectors;

public class FoodDatabaseService {
    private final IFileStorage fileStorage;
    private final String defaultFoodsFile = "data/default_foods.txt";
    private List<Food> foods;

    public FoodDatabaseService(IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
        this.foods = new ArrayList<>();
        loadDefaultFoods();
    }

    private void loadDefaultFoods() {
        if (!fileStorage.fileExists(defaultFoodsFile)) {
            throw new RuntimeException("Default foods file not found: " + defaultFoodsFile);
        }

        List<String> lines = fileStorage.readAllLines(defaultFoodsFile);
        foods = lines.stream()
            .filter(line -> !line.startsWith("#") && !line.trim().isEmpty())
            .map(this::parseFood)
            .collect(Collectors.toList());
    }

    private Food parseFood(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid food data format: " + line);
            }

            return new Food(
                UUID.randomUUID().toString(),
                parts[0].trim(),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]),
                "100g"
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in food data: " + line, e);
        }
    }

    public List<Food> getAllFoods() {
        return new ArrayList<>(foods);
    }

    public Optional<Food> getFoodByName(String name) {
        return foods.stream()
            .filter(food -> food.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}