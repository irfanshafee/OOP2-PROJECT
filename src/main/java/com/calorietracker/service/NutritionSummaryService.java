package com.calorietracker.service;

import com.calorietracker.model.MealEntry;
import java.time.LocalDateTime;
import java.util.List;

public class NutritionSummaryService {
    private final MealService mealService;
    private final double DAILY_CALORIE_TARGET = 2000.0;
    private final double DAILY_PROTEIN_TARGET = 150.0;  // in grams
    private final double DAILY_CARBS_TARGET = 250.0;    // in grams
    private final double DAILY_FAT_TARGET = 70.0;       // in grams

    public NutritionSummaryService(MealService mealService) {
        this.mealService = mealService;
    }

    public void displayDailySummary(LocalDateTime date) {
        List<MealEntry> dailyMeals = mealService.getMealsByDate(date);
        
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;

        for (MealEntry meal : dailyMeals) {
            totalCalories += meal.getCalories();
            totalProtein += meal.getProtein();
            totalCarbs += meal.getCarbohydrates();
            totalFats += meal.getFats();
        }

        System.out.println("\n=== Nutrition Summary for " + date.toLocalDate() + " ===");
        System.out.println("\nTotal Intake:");
        System.out.printf("Calories: %.2f kcal\n", totalCalories);
        System.out.printf("Protein: %.2f g\n", totalProtein);
        System.out.printf("Carbohydrates: %.2f g\n", totalCarbs);
        System.out.printf("Fats: %.2f g\n", totalFats);

        System.out.println("\nDaily Targets Comparison:");
        System.out.printf("Calories: %.2f%% (%.2f/%.2f kcal)\n", 
            (totalCalories/DAILY_CALORIE_TARGET) * 100, totalCalories, DAILY_CALORIE_TARGET);
        System.out.printf("Protein: %.2f%% (%.2f/%.2f g)\n", 
            (totalProtein/DAILY_PROTEIN_TARGET) * 100, totalProtein, DAILY_PROTEIN_TARGET);
        System.out.printf("Carbohydrates: %.2f%% (%.2f/%.2f g)\n", 
            (totalCarbs/DAILY_CARBS_TARGET) * 100, totalCarbs, DAILY_CARBS_TARGET);
        System.out.printf("Fats: %.2f%% (%.2f/%.2f g)\n", 
            (totalFats/DAILY_FAT_TARGET) * 100, totalFats, DAILY_FAT_TARGET);
        System.out.println("\n==================================\n");
    }
}