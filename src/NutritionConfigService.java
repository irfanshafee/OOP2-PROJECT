package com.calorietracker.service;

public class NutritionConfigService {
    private double dailyCalorieTarget = 2000.0;
    private double dailyProteinTarget = 150.0;  // in grams
    private double dailyCarbsTarget = 250.0;    // in grams
    private double dailyFatTarget = 70.0;       // in grams

    public double getDailyCalorieTarget() {
        return dailyCalorieTarget;
    }

    public void setDailyCalorieTarget(double dailyCalorieTarget) {
        this.dailyCalorieTarget = dailyCalorieTarget;
    }

    public double getDailyProteinTarget() {
        return dailyProteinTarget;
    }

    public void setDailyProteinTarget(double dailyProteinTarget) {
        this.dailyProteinTarget = dailyProteinTarget;
    }

    public double getDailyCarbsTarget() {
        return dailyCarbsTarget;
    }

    public void setDailyCarbsTarget(double dailyCarbsTarget) {
        this.dailyCarbsTarget = dailyCarbsTarget;
    }

    public double getDailyFatTarget() {
        return dailyFatTarget;
    }

    public void setDailyFatTarget(double dailyFatTarget) {
        this.dailyFatTarget = dailyFatTarget;
    }
}