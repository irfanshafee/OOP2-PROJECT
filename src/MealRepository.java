
package com.calorietracker;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealRepository implements Repository<Meal> {
    private final String dataFile = "data/meals.txt";
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final FoodRepository foodRepository;
    private final IFileStorage fileStorage;
    private Map<String, Meal> meals;

    public MealRepository(IFileStorage fileStorage, FoodRepository foodRepository) {
        this.fileStorage = fileStorage;
        this.foodRepository = foodRepository;
        this.meals = new HashMap<>();
        loadMeals();
    }

    private void loadMeals() {
        if (!fileStorage.fileExists(dataFile)) {
            return;
        }

        List<String> lines = fileStorage.readAllLines(dataFile);
        for (String line : lines) {
            Meal meal = parseMeal(line);
            meals.put(meal.getId(), meal);
        }
    }

    private Meal parseMeal(String line) {
        String[] parts = line.split(",");
        String id = parts[0];
        String name = parts[1];
        LocalDateTime dateTime = LocalDateTime.parse(parts[2], formatter);
        
        Meal meal = new Meal(id, name, dateTime);
        
        // Parse meal items starting from index 3
        for (int i = 3; i < parts.length; i += 2) {
            String foodId = parts[i];
            double quantity = Double.parseDouble(parts[i + 1]);
            Food food = foodRepository.findById(foodId);
            if (food != null) {
                meal.addItem(food, quantity);
            }
        }
        
        return meal;
    }

    private String mealToString(Meal meal) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s,%s,%s",
            meal.getId(),
            meal.getName(),
            meal.getDateTime().format(formatter)));
        
        for (Meal.MealItem item : meal.getItems()) {
            sb.append(String.format(",%s,%.2f",
                item.getFood().getId(),
                item.getQuantity()));
        }
        
        return sb.toString();
    }

    @Override
    public Meal save(Meal meal) {
        meals.put(meal.getId(), meal);
        saveMeals();
        return meal;
    }

    private void saveMeals() {
        List<String> lines = new ArrayList<>();
        for (Meal meal : meals.values()) {
            lines.add(mealToString(meal));
        }
        String content = String.join("\n", lines);
        fileStorage.saveToFile(content, dataFile);
    }

    @Override
    public Meal findById(String id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public boolean deleteById(String id) {
        if (meals.remove(id) != null) {
            saveMeals();
            return true;
        }
        return false;
    }

    public List<Meal> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return meals.values().stream()
            .filter(meal -> !meal.getDateTime().isBefore(startDate) && 
                           !meal.getDateTime().isAfter(endDate))
            .sorted(Comparator.comparing(Meal::getDateTime))
            .toList();
    }
}