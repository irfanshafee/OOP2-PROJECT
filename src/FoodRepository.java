package com.calorietracker;

import java.util.*;
import java.io.*;

public class FoodRepository implements Repository<Food> {
    private final IFileStorage fileStorage;
    private final String dataFile = "data/foods.txt";
    private Map<String, Food> foods;

    public FoodRepository(IFileStorage fileStorage) {
        this.fileStorage = fileStorage;
        this.foods = new HashMap<>();
        loadFoods();
    }

    private void loadFoods() {
        if (!fileStorage.fileExists(dataFile)) {
            return;
        }

        List<String> lines = fileStorage.readAllLines(dataFile);
        for (String line : lines) {
            Food food = parseFood(line);
            foods.put(food.getId(), food);
        }
    }

    private Food parseFood(String line) {
        String[] parts = line.split(",");
        return new Food(
            parts[0],
            parts[1],
            Double.parseDouble(parts[2]),
            Double.parseDouble(parts[3]),
            Double.parseDouble(parts[4]),
            Double.parseDouble(parts[5]),
            parts[6]
        );
    }

    private String foodToString(Food food) {
        return String.format("%s,%s,%.2f,%.2f,%.2f,%.2f,%s",
            food.getId(),
            food.getName(),
            food.getCalories(),
            food.getProtein(),
            food.getCarbs(),
            food.getFat(),
            food.getServingSize());
    }

    @Override
    public Food save(Food food) {
        foods.put(food.getId(), food);
        saveFoods();
        return food;
    }

    private void saveFoods() {
        List<String> lines = new ArrayList<>();
        for (Food food : foods.values()) {
            lines.add(foodToString(food));
        }
        String content = String.join("\n", lines);
        fileStorage.saveToFile(content, dataFile);
    }

    @Override
    public Food findById(String id) {
        return foods.get(id);
    }

    @Override
    public List<Food> findAll() {
        return new ArrayList<>(foods.values());
    }

    @Override
    public boolean deleteById(String id) {
        if (foods.remove(id) != null) {
            saveFoods();
            return true;
        }
        return false;
    }

    public Optional<Food> findByName(String name) {
        return foods.values().stream()
            .filter(food -> food.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}