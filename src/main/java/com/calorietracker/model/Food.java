package com.calorietracker.model;

public class Food {
    private String id;
    private String name;
    private double calories;
    private double protein;
    private double carbohydrates;
    private double fats;
    private String servingSize;

    public Food(String id, String name, double calories, double protein, double carbohydrates, double fats, String servingSize) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.servingSize = servingSize;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getCarbohydrates() { return carbohydrates; }
    public void setCarbohydrates(double carbohydrates) { this.carbohydrates = carbohydrates; }

    public double getFats() { return fats; }
    public void setFats(double fats) { this.fats = fats; }

    public String getServingSize() { return servingSize; }
    public void setServingSize(String servingSize) { this.servingSize = servingSize; }

    @Override
    public String toString() {
        return String.format("%s (per %s) - Calories: %.2f, Protein: %.2f g, Carbs: %.2f g, Fats: %.2f g",
                name, servingSize, calories, protein, carbohydrates, fats);
    }
}