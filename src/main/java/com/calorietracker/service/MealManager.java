package com.calorietracker.service;

import com.calorietracker.core.meal.MealType;
import com.calorietracker.model.MealEntry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MealManager {
    private final MealService mealService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;

    public MealManager(MealService mealService) {
        this.mealService = mealService;
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public void displayMealManagementMenu() {
        while (true) {
            System.out.println("\n=== Meal Management Menu ===");
            System.out.println("1. View Today's Meals");
            System.out.println("2. View Meals by Date");
            System.out.println("3. Edit a Meal");
            System.out.println("4. Delete a Meal");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewTodaysMeals();
                    break;
                case 2:
                    viewMealsByDate();
                    break;
                case 3:
                    editMeal();
                    break;
                case 4:
                    deleteMeal();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewTodaysMeals() {
        List<MealEntry> meals = mealService.getMealsByDate(LocalDateTime.now());
        displayMeals(meals);
    }

    private void viewMealsByDate() {
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        try {
            LocalDateTime date = LocalDateTime.parse(dateStr + " 00:00", dateFormatter);
            List<MealEntry> meals = mealService.getMealsByDate(date);
            displayMeals(meals);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd");
        }
    }

    private void displayMeals(List<MealEntry> meals) {
        if (meals.isEmpty()) {
            System.out.println("No meals found for the specified date.");
            return;
        }

        System.out.println("\n=== Meals ===");
        for (MealEntry meal : meals) {
            System.out.printf("ID: %s - %s\n", meal.getId(), meal.toString());
        }
    }

    private void editMeal() {
        System.out.print("Enter meal ID to edit: ");
        String mealId = scanner.nextLine();

        try {
            MealEntry meal = mealService.getMeal(mealId);
            System.out.println("Current meal details: " + meal.toString());

            System.out.print("Enter new food name (or press Enter to keep current): ");
            String foodName = scanner.nextLine();
            if (foodName.isEmpty()) foodName = meal.getFoodName();

            System.out.print("Enter new quantity in grams (or -1 to keep current): ");
            double quantity = scanner.nextDouble();
            if (quantity == -1) quantity = meal.getQuantity();

            System.out.println("Select meal type:");
            for (MealType type : MealType.values()) {
                System.out.println(type.ordinal() + ". " + type);
            }
            System.out.print("Enter choice (-1 to keep current): ");
            int typeChoice = scanner.nextInt();
            MealType mealType = typeChoice == -1 ? meal.getMealType() : MealType.values()[typeChoice];

            mealService.updateMeal(mealId, foodName, quantity, mealType, meal.getDateTime());
            System.out.println("Meal updated successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteMeal() {
        System.out.print("Enter meal ID to delete: ");
        String mealId = scanner.nextLine();

        try {
            MealEntry meal = mealService.getMeal(mealId);
            System.out.println("Are you sure you want to delete this meal?");
            System.out.println(meal.toString());
            System.out.print("Enter 'yes' to confirm: ");

            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                mealService.removeMeal(mealId);
                System.out.println("Meal deleted successfully!");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}