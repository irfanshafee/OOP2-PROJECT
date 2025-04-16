package com.calorietracker.core.meal;

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
     * Retrieves a meal entry by its ID.
     * @param mealId ID of the meal to retrieve
     * @return The meal entry if found
     */
    MealEntry getMeal(String mealId);

    /**
     * Updates an existing meal entry.
     * @param mealId ID of the meal to update
     * @param foodName Updated food name
     * @param quantity Updated quantity
     * @param mealType Updated meal type
     * @param dateTime Updated date and time
     */
    void updateMeal(String mealId, String foodName, double quantity, MealType mealType, LocalDateTime dateTime);

    /**
     * Removes a meal entry.
     * @param mealId ID of the meal to remove
     */
    void removeMeal(String mealId);

    /**
     * Gets all meals for a specific date.
     * @param date The date to get meals for
     * @return List of meal entries for the specified date
     */
    List<MealEntry> getMealsByDate(LocalDateTime date);

    /**
     * Gets meals of a specific type for a date.
     * @param date The date to get meals for
     * @param mealType The type of meals to retrieve
     * @return List of meal entries matching the criteria
     */
    List<MealEntry> getMealsByDateAndType(LocalDateTime date, MealType mealType);
}