package com.calorietracker.core.nutrition;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Interface for calculating nutritional information.
 * Follows Interface Segregation Principle by focusing only on nutrition calculations.
 */
public interface INutritionCalculator {
    /**
     * Calculates total calories for a given date.
     * @param date The date to calculate for
     * @return Total calories consumed
     */
    double calculateDailyCalories(LocalDateTime date);

    /**
     * Calculates macronutrient breakdown for a given date.
     * @param date The date to calculate for
     * @return Map containing macronutrient values (protein, carbs, fats)
     */
    Map<String, Double> calculateMacronutrients(LocalDateTime date);

    /**
     * Calculates percentage of daily recommended values.
     * @param date The date to calculate for
     * @return Map containing percentages for each nutritional category
     */
    Map<String, Double> calculateDailyPercentages(LocalDateTime date);

    /**
     * Calculates nutritional values for a specific food and quantity.
     * @param foodName Name of the food item
     * @param quantity Quantity in serving size units
     * @return Map containing calculated nutritional values
     */
    Map<String, Double> calculateFoodNutrition(String foodName, double quantity);

    /**
     * Analyzes nutritional balance of daily intake.
     * @param date The date to analyze
     * @return Analysis results including any nutritional warnings or recommendations
     */
    NutritionAnalysis analyzeDailyBalance(LocalDateTime date);
}