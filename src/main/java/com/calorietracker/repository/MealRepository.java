package com.calorietracker.repository;

import com.calorietracker.model.Meal;
import com.calorietracker.model.Food;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealRepository implements Repository<Meal> {
    private final String dataFile = "data/meals.txt";
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final FoodRepository foodRepository;

    public MealRepository(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
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
    public void save(Meal meal) {
        List<Meal> meals = findAll();
        meals.add(meal);
        writeToFile(meals);
    }

    @Override
    public Optional<Meal> findById(String id) {
        return findAll().stream()
                .filter(meal -> meal.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|\\|\\|");
                if (parts.length >= 3) {
                    Meal meal = new Meal(
                        parts[0],
                        parts[1],
                        LocalDateTime.parse(parts[2], formatter)
                    );
                    
                    if (parts.length > 3) {
                        String[] items = parts[3].split(",");
                        for (String item : items) {
                            String[] itemParts = item.split(":");
                            if (itemParts.length == 2) {
                                String foodId = itemParts[0];
                                double quantity = Double.parseDouble(itemParts[1]);
                                foodRepository.findById(foodId).ifPresent(food ->
                                    meal.addItem(food, quantity));
                            }
                        }
                    }
                    meals.add(meal);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file", e);
        }
        return meals;
    }

    @Override
    public void delete(String id) {
        List<Meal> meals = findAll();
        meals.removeIf(meal -> meal.getId().equals(id));
        writeToFile(meals);
    }

    @Override
    public void update(Meal updatedMeal) {
        List<Meal> meals = findAll();
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId().equals(updatedMeal.getId())) {
                meals.set(i, updatedMeal);
                break;
            }
        }
        writeToFile(meals);
    }

    private void writeToFile(List<Meal> meals) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Meal meal : meals) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s|||%s|||%s",
                    meal.getId(),
                    meal.getName(),
                    meal.getDateTime().format(formatter)));
                
                if (!meal.getItems().isEmpty()) {
                    sb.append("|||");
                    StringJoiner itemJoiner = new StringJoiner(",");
                    meal.getItems().forEach(item ->
                        itemJoiner.add(item.getFood().getId() + ":" + item.getQuantity()));
                    sb.append(itemJoiner.toString());
                }
                writer.write(sb.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file", e);
        }
    }
}