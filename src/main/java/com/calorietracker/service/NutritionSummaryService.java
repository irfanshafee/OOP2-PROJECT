package com.calorietracker.service;

import com.calorietracker.model.MealEntry;
import java.time.LocalDateTime;
import java.util.List;

public class NutritionSummaryService {
    private final MealService mealService;
    private final NutritionConfigService configService;
    private final NutritionDisplayService displayService;

    public NutritionSummaryService(MealService mealService, NutritionConfigService configService, 
            NutritionDisplayService displayService) {
        this.mealService = mealService;
        this.configService = configService;
        this.displayService = displayService;
    }

    public void displayDailySummary(LocalDateTime date) {
        List<MealEntry> dailyMeals = mealService.getMealsByDate(date);
        NutritionTotals totals = calculateDailyTotals(dailyMeals);
        displayService.displayNutritionSummary(date, totals.calories, totals.protein, 
            totals.carbs, totals.fats);
    }

    private NutritionTotals calculateDailyTotals(List<MealEntry> meals) {
        NutritionTotals totals = new NutritionTotals();
        for (MealEntry meal : meals) {
            totals.calories += meal.getCalories();
            totals.protein += meal.getProtein();
            totals.carbs += meal.getCarbohydrates();
            totals.fats += meal.getFats();
        }
        return totals;
    }

    private static class NutritionTotals {
        double calories = 0;
        double protein = 0;
        double carbs = 0;
        double fats = 0;
    }
}