package com.calorietracker.repository;

import com.calorietracker.model.Food;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class FoodRepository implements Repository<Food> {
    private final String dataFile = "data/foods.txt";

    public FoodRepository() {
        createDataFileIfNotExists();
    }

    private void createDataFileIfNotExists() {
        try {
            File file = new File(dataFile);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Could not create data file", e);
        }
    }

    @Override
    public void save(Food food) {
        List<Food> foods = findAll();
        foods.add(food);
        writeToFile(foods);
    }

    @Override
    public Optional<Food> findById(String id) {
        return findAll().stream()
                .filter(food -> food.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Food> findAll() {
        List<Food> foods = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    Food food = new Food(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]),
                        parts[6]
                    );
                    foods.add(food);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file", e);
        }
        return foods;
    }

    @Override
    public void delete(String id) {
        List<Food> foods = findAll();
        foods.removeIf(food -> food.getId().equals(id));
        writeToFile(foods);
    }

    @Override
    public void update(Food updatedFood) {
        List<Food> foods = findAll();
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getId().equals(updatedFood.getId())) {
                foods.set(i, updatedFood);
                break;
            }
        }
        writeToFile(foods);
    }

    private void writeToFile(List<Food> foods) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Food food : foods) {
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f,%.2f,%s\n",
                    food.getId(),
                    food.getName(),
                    food.getCalories(),
                    food.getProtein(),
                    food.getCarbohydrates(),
                    food.getFats(),
                    food.getServingSize()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }
}