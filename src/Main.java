package com.calorietracker;

import com.calorietracker.core.storage.FileStorageImpl;
import com.calorietracker.core.storage.IFileStorage;
import com.calorietracker.repository.FoodRepository;
import com.calorietracker.repository.MealRepository;
import com.calorietracker.service.ConsoleMenuService;
import com.calorietracker.service.FoodDatabaseService;
import com.calorietracker.service.MealManager;
import com.calorietracker.service.MealService;

/**
 * Main entry point for the Calorie Tracker application.
 * Initializes required services and starts the application.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Initialize storage and repositories
            IFileStorage fileStorage = new FileStorageImpl();
            FoodRepository foodRepository = new FoodRepository(fileStorage);
            MealRepository mealRepository = new MealRepository(fileStorage);

            // Initialize services
            FoodDatabaseService foodDatabaseService = new FoodDatabaseService(foodRepository);
            MealService mealService = new MealService(mealRepository, foodDatabaseService);
            ConsoleMenuService menuService = new ConsoleMenuService(mealService);
            MealManager mealManager = new MealManager(mealService);

            // Start the application
            System.out.println("=== Welcome to Calorie Tracker ===\n");
            menuService.start();

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}