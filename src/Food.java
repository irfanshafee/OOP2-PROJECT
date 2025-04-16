package com.calorietracker;

/**
 * Represents a food item with its nutritional information.
 */
public class Food {
    private String id;
    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    private String servingSize;

    public Food(String id, String name, double calories, double protein, double carbs, double fat, String servingSize) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.servingSize = servingSize;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public String getServingSize() {
        return servingSize;
    }

    @Override
    public String toString() {
        return String.format("%s (per %s): %.1f cal, %.1fg protein, %.1fg carbs, %.1fg fat",
                name, servingSize, calories, protein, carbs, fat);
    }
}