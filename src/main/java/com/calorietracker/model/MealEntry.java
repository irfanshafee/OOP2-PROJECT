package com.calorietracker.model;

import com.calorietracker.core.meal.MealType;
import java.time.LocalDateTime;

public class MealEntry {
    private String id;
    private String foodName;
    private double quantity;
    private MealType mealType;
    private LocalDateTime dateTime;
    private double calories;
    private double protein;
    private double carbohydrates;
    private double fats;

    public MealEntry(String id, String foodName, double quantity, MealType mealType, 
                     LocalDateTime dateTime, double calories, double protein, 
                     double carbohydrates, double fats) {
        this.id = id;
        this.foodName = foodName;
        this.quantity = quantity;
        this.mealType = mealType;
        this.dateTime = dateTime;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    // Getters
    public String getId() { return id; }
    public String getFoodName() { return foodName; }
    public double getQuantity() { return quantity; }
    public MealType getMealType() { return mealType; }
    public LocalDateTime getDateTime() { return dateTime; }
    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbohydrates() { return carbohydrates; }
    public double getFats() { return fats; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setCalories(double calories) { this.calories = calories; }
    public void setProtein(double protein) { this.protein = protein; }
    public void setCarbohydrates(double carbohydrates) { this.carbohydrates = carbohydrates; }
    public void setFats(double fats) { this.fats = fats; }

    @Override
    public String toString() {
        return String.format("%s - %s (%.2f) - Calories: %.2f, Protein: %.2f, Carbs: %.2f, Fats: %.2f",
            mealType, foodName, quantity, calories, protein, carbohydrates, fats);
    }
}