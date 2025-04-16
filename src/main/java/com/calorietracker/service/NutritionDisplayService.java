package com.calorietracker.service;

import java.time.LocalDateTime;

public class NutritionDisplayService {
    private final NutritionConfigService configService;

    public NutritionDisplayService(NutritionConfigService configService) {
        this.configService = configService;
    }

    public void displayNutritionSummary(LocalDateTime date, double totalCalories, 
            double totalProtein, double totalCarbs, double totalFats) {
        System.out.println("\n=== Nutrition Summary for " + date.toLocalDate() + " ===");
        displayTotalIntake(totalCalories, totalProtein, totalCarbs, totalFats);
        displayTargetComparison(totalCalories, totalProtein, totalCarbs, totalFats);
        System.out.println("\n==================================\n");
    }

    private void displayTotalIntake(double calories, double protein, double carbs, double fats) {
        System.out.println("\nTotal Intake:");
        System.out.printf("Calories: %.2f kcal\n", calories);
        System.out.printf("Protein: %.2f g\n", protein);
        System.out.printf("Carbohydrates: %.2f g\n", carbs);
        System.out.printf("Fats: %.2f g\n", fats);
    }

    private void displayTargetComparison(double calories, double protein, double carbs, double fats) {
        System.out.println("\nDaily Targets Comparison:");
        System.out.printf("Calories: %.2f%% (%.2f/%.2f kcal)\n", 
            (calories/configService.getDailyCalorieTarget()) * 100, calories, configService.getDailyCalorieTarget());
        System.out.printf("Protein: %.2f%% (%.2f/%.2f g)\n", 
            (protein/configService.getDailyProteinTarget()) * 100, protein, configService.getDailyProteinTarget());
        System.out.printf("Carbohydrates: %.2f%% (%.2f/%.2f g)\n", 
            (carbs/configService.getDailyCarbsTarget()) * 100, carbs, configService.getDailyCarbsTarget());
        System.out.printf("Fats: %.2f%% (%.2f/%.2f g)\n", 
            (fats/configService.getDailyFatTarget()) * 100, fats, configService.getDailyFatTarget());
    }
}