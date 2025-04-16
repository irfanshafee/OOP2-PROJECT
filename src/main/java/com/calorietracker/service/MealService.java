package com.calorietracker.service;

import com.calorietracker.core.meal.IMealService;
import com.calorietracker.core.meal.MealType;
import com.calorietracker.model.Food;
import com.calorietracker.model.MealEntry;
import com.calorietracker.repository.FoodRepository;
import com.calorietracker.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MealService implements IMealService {
    private final FoodRepository foodRepository;
    private final MealRepository mealRepository;
    private final FoodDatabaseService foodDatabaseService;

    public MealService(FoodRepository foodRepository, MealRepository mealRepository, 
                      FoodDatabaseService foodDatabaseService) {
        this.foodRepository = foodRepository;
        this.mealRepository = mealRepository;
        this.foodDatabaseService = foodDatabaseService;
    }

    @Override
    public String logMeal(String foodName, double quantity, MealType mealType, LocalDateTime dateTime) {
        Food food = foodDatabaseService.getFoodByName(foodName)
            .orElseThrow(() -> new IllegalArgumentException("Food not found: " + foodName));

        double servingRatio = quantity / 100.0; // Convert to ratio as food data is per 100g
        String mealId = UUID.randomUUID().toString();

        MealEntry mealEntry = new MealEntry(
            mealId,
            foodName,
            quantity,
            mealType,
            dateTime,
            food.getCalories() * servingRatio,
            food.getProtein() * servingRatio,
            food.getCarbohydrates() * servingRatio,
            food.getFats() * servingRatio
        );

        mealRepository.save(new Meal(mealId, mealType.toString(), dateTime));
        return mealId;
    }

    @Override
    public MealEntry getMeal(String mealId) {
        return mealRepository.findById(mealId)
            .map(this::convertToMealEntry)
            .orElseThrow(() -> new IllegalArgumentException("Meal not found: " + mealId));
    }

    @Override
    public void updateMeal(String mealId, String foodName, double quantity, 
                          MealType mealType, LocalDateTime dateTime) {
        Food food = foodDatabaseService.getFoodByName(foodName)
            .orElseThrow(() -> new IllegalArgumentException("Food not found: " + foodName));

        double servingRatio = quantity / 100.0;
        Meal meal = new Meal(mealId, mealType.toString(), dateTime);
        meal.addItem(food, quantity);
        
        mealRepository.update(meal);
    }

    @Override
    public void removeMeal(String mealId) {
        mealRepository.delete(mealId);
    }

    @Override
    public List<MealEntry> getMealsByDate(LocalDateTime date) {
        return mealRepository.findAll().stream()
            .filter(meal -> isSameDate(meal.getDateTime(), date))
            .map(this::convertToMealEntry)
            .collect(Collectors.toList());
    }

    @Override
    public List<MealEntry> getMealsByDateAndType(LocalDateTime date, MealType mealType) {
        return getMealsByDate(date).stream()
            .filter(meal -> meal.getMealType() == mealType)
            .collect(Collectors.toList());
    }

    private MealEntry convertToMealEntry(Meal meal) {
        double totalCalories = meal.getTotalCalories();
        double totalProtein = meal.getTotalProtein();
        double totalCarbs = meal.getTotalCarbohydrates();
        double totalFats = meal.getTotalFats();
        
        return new MealEntry(
            meal.getId(),
            meal.getName(),
            1.0, // Default quantity as the totals are already calculated
            MealType.valueOf(meal.getName()),
            meal.getDateTime(),
            totalCalories,
            totalProtein,
            totalCarbs,
            totalFats
        );
    }

    private boolean isSameDate(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().equals(date2.toLocalDate());
    }
}