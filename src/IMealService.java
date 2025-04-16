package com.calorietracker;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for handling meal-related operations.
 * Follows Interface Segregation Principle by focusing only on meal management.
 */
public interface IMealService {
    /**
     * Logs a new meal entry.
     * @param foodName Name of the food item
     * @param quantity Quantity in serving size units
     * @param mealType Type of meal (Breakfast, Lunch, Dinner, Snack)
     * @param dateTime Date and time of the meal
     * @return ID of the logged meal
     */
    String logMeal(String foodName, double quantity, MealType mealType, LocalDateTime dateTime);

    /**
     * Gets all meal entries for a specific date.
     * @param date The date to get meals for
     * @return List of meal entries
     */
    List<MealEntry> getMealsForDate(LocalDateTime date);

    /**
     * Gets all meal entries within a date range.
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @return List of meal entries
     */
    List<MealEntry> getMealsInDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Deletes a meal entry.
     * @param mealId ID of the meal to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteMeal(String mealId);
}