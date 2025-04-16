`package com.calorietracker.service;

import com.calorietracker.core.health.IHealthTipService;
import com.calorietracker.model.HealthTip;
import com.calorietracker.model.HealthTip.TipSeverity;
import com.calorietracker.model.MealEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthTipService implements IHealthTipService {
    private final MealService mealService;
    private final NutritionConfigService configService;

    private final double PROTEIN_LOW_THRESHOLD = 0.5;  // 50% of daily target
    private final double FAT_HIGH_THRESHOLD = 0.8;     // 80% of daily target
    private final double CARBS_HIGH_THRESHOLD = 0.8;   // 80% of daily target
    private final double CALORIE_HIGH_THRESHOLD = 0.9; // 90% of daily target

    public HealthTipService(MealService mealService, NutritionConfigService configService) {
        this.mealService = mealService;
        this.configService = configService;
    }

    @Override
    public List<HealthTip> generateDailyTips(LocalDateTime date) {
        List<HealthTip> tips = new ArrayList<>();
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

        // Check protein intake
        if (totalProtein < configService.getDailyProteinTarget() * PROTEIN_LOW_THRESHOLD) {
            tips.add(new HealthTip(
                UUID.randomUUID().toString(),
                "You are low on protein today. Consider adding lean meats, eggs, or legumes to your next meal.",
                TipSeverity.HIGH,
                "protein"
            ));
        }

        // Check fat intake
        if (totalFats > configService.getDailyFatTarget() * FAT_HIGH_THRESHOLD) {
            tips.add(new HealthTip(
                UUID.randomUUID().toString(),
                String.format("You've consumed %.1f%% of your daily fat goal. Consider lighter options for your remaining meals.",
                    (totalFats/configService.getDailyFatTarget()) * 100),
                TipSeverity.MEDIUM,
                "fat"
            ));
        }

        // Check carbs intake
        if (totalCarbs > configService.getDailyCarbsTarget() * CARBS_HIGH_THRESHOLD) {
            tips.add(new HealthTip(
                UUID.randomUUID().toString(),
                "Your carbohydrate intake is high. Try to include more vegetables instead of refined carbs.",
                TipSeverity.MEDIUM,
                "carbs"
            ));
        }

        // Check calorie intake
        if (totalCalories > configService.getDailyCalorieTarget() * CALORIE_HIGH_THRESHOLD) {
            tips.add(new HealthTip(
                UUID.randomUUID().toString(),
                String.format("You're at %.1f%% of your daily calorie target. Consider lighter options for remaining meals.",
                    (totalCalories/configService.getDailyCalorieTarget()) * 100),
                TipSeverity.HIGH,
                "calories"
            ));
        }

        return tips;
    }

    @Override
    public List<HealthTip> getGeneralTips() {
        List<HealthTip> generalTips = new ArrayList<>();
        generalTips.add(new HealthTip(
            UUID.randomUUID().toString(),
            "Remember to stay hydrated throughout the day!",
            TipSeverity.LOW,
            "general"
        ));
        generalTips.add(new HealthTip(
            UUID.randomUUID().toString(),
            "Try to eat a variety of colorful vegetables for better nutrition.",
            TipSeverity.LOW,
            "general"
        ));
        return generalTips;
    }

    @Override
    public void processTipFeedback(String tipId, TipFeedback feedback) {
        // Implementation for processing user feedback on tips
        // This could be used to improve tip relevance in the future
    }
}