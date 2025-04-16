package com.calorietracker.service;

import com.calorietracker.core.meal.MealType;
import com.calorietracker.model.MealEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * A console-based user interface service for the Calorie Tracker application.
 * This class handles all user interactions through the command line, providing
 * functionality for meal logging, viewing summaries, and managing meal entries.
 */
public class ConsoleMenuService {
    private final MealService mealService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;

    /**
     * Constructs a new ConsoleMenuService with the specified MealService.
     *
     * @param mealService The service handling meal-related operations
     */
    public ConsoleMenuService(MealService mealService) {
        this.mealService = mealService;
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    /**
     * Starts the console menu interface. This method runs the main application loop,
     * displaying the menu and handling user input until the user chooses to exit.
     */
    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> logMeal();
                    case "2" -> viewDailySummary();
                    case "3" -> editMeal();
                    case "4" -> deleteMeal();
                    case "5" -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Displays the main menu options to the user.
     */
    private void displayMenu() {
        System.out.println("\n=== Calorie Tracker Menu ===");
        System.out.println("1. Log a Meal");
        System.out.println("2. View Daily Summary");
        System.out.println("3. Edit a Meal");
        System.out.println("4. Delete a Meal");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }

    /**
     * Handles the meal logging process by collecting meal information from the user
     * and saving it using the meal service.
     */
    private void logMeal() {
        System.out.println("\n=== Log a Meal ===");
        
        System.out.print("Enter food name: ");
        String foodName = scanner.nextLine().trim();
        
        double quantity = getValidQuantity();
        MealType mealType = getMealType();
        LocalDateTime dateTime = getDateTime();

        try {
            String mealId = mealService.logMeal(foodName, quantity, mealType, dateTime);
            System.out.println("Meal logged successfully! Meal ID: " + mealId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error logging meal: " + e.getMessage());
        }
    }

    /**
     * Displays a summary of all meals for a specific date, including individual meal
     * details and daily totals for calories and macronutrients.
     */
    private void viewDailySummary() {
        System.out.println("\n=== Daily Summary ===");
        LocalDateTime date = getDateTime();
        List<MealEntry> meals = mealService.getMealsByDate(date);

        if (meals.isEmpty()) {
            System.out.println("No meals found for this date.");
            return;
        }

        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;

        System.out.println("\nMeals for " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        for (MealEntry meal : meals) {
            System.out.printf("\nMeal ID: %s%n", meal.getId());
            System.out.printf("Food: %s%n", meal.getFoodName());
            System.out.printf("Type: %s%n", meal.getMealType());
            System.out.printf("Quantity: %.2fg%n", meal.getQuantity());
            System.out.printf("Calories: %.2f%n", meal.getCalories());
            System.out.printf("Protein: %.2fg%n", meal.getProtein());
            System.out.printf("Carbs: %.2fg%n", meal.getCarbohydrates());
            System.out.printf("Fats: %.2fg%n", meal.getFats());

            totalCalories += meal.getCalories();
            totalProtein += meal.getProtein();
            totalCarbs += meal.getCarbohydrates();
            totalFats += meal.getFats();
        }

        System.out.println("\nDaily Totals:");
        System.out.printf("Total Calories: %.2f%n", totalCalories);
        System.out.printf("Total Protein: %.2fg%n", totalProtein);
        System.out.printf("Total Carbs: %.2fg%n", totalCarbs);
        System.out.printf("Total Fats: %.2fg%n", totalFats);
    }

    /**
     * Handles the meal editing process by allowing users to modify the details
     * of an existing meal entry.
     */
    private void editMeal() {
        System.out.println("\n=== Edit a Meal ===");
        System.out.print("Enter meal ID: ");
        String mealId = scanner.nextLine().trim();

        try {
            MealEntry currentMeal = mealService.getMeal(mealId);
            System.out.println("\nCurrent meal details:");
            System.out.printf("Food: %s%n", currentMeal.getFoodName());
            System.out.printf("Quantity: %.2fg%n", currentMeal.getQuantity());
            System.out.printf("Type: %s%n", currentMeal.getMealType());

            System.out.print("\nEnter new food name (or press Enter to keep current): ");
            String foodName = scanner.nextLine().trim();
            if (foodName.isEmpty()) foodName = currentMeal.getFoodName();

            System.out.print("Enter new quantity in grams (or press Enter to keep current): ");
            String quantityStr = scanner.nextLine().trim();
            double quantity = quantityStr.isEmpty() ? currentMeal.getQuantity() : Double.parseDouble(quantityStr);

            System.out.println("Select new meal type (or press Enter to keep current):");
            String mealTypeStr = scanner.nextLine().trim();
            MealType mealType = mealTypeStr.isEmpty() ? currentMeal.getMealType() : MealType.valueOf(mealTypeStr.toUpperCase());

            mealService.updateMeal(mealId, foodName, quantity, mealType, currentMeal.getDateTime());
            System.out.println("Meal updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating meal: " + e.getMessage());
        }
    }

    /**
     * Handles the meal deletion process by removing a specified meal entry
     * from the system.
     */
    private void deleteMeal() {
        System.out.println("\n=== Delete a Meal ===");
        System.out.print("Enter meal ID: ");
        String mealId = scanner.nextLine().trim();

        try {
            mealService.removeMeal(mealId);
            System.out.println("Meal deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting meal: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for a valid quantity input in grams.
     *
     * @return A positive double value representing the quantity in grams
     * @throws IllegalArgumentException if the quantity is not positive
     */
    private double getValidQuantity() {
        while (true) {
            try {
                System.out.print("Enter quantity (in grams): ");
                double quantity = Double.parseDouble(scanner.nextLine().trim());
                if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
                return quantity;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prompts the user to select a meal type from the available options.
     *
     * @return The selected MealType enum value
     */
    private MealType getMealType() {
        while (true) {
            try {
                System.out.println("Select meal type:");
                for (MealType type : MealType.values()) {
                    System.out.println(type.ordinal() + 1 + ". " + type);
                }
                System.out.print("Enter number: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                return MealType.values()[choice - 1];
            } catch (Exception e) {
                System.out.println("Please enter a valid meal type number.");
            }
        }
    }

    /**
     * Prompts the user for a date and time input in the format "yyyy-MM-dd HH:mm".
     *
     * @return A LocalDateTime object representing the input date and time
     * @throws DateTimeParseException if the input format is invalid
     */
    private LocalDateTime getDateTime() {
        while (true) {
            try {
                System.out.print("Enter date and time (yyyy-MM-dd HH:mm): ");
                String dateStr = scanner.nextLine().trim();
                return LocalDateTime.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date and time in the correct format (yyyy-MM-dd HH:mm).");
            }
        }
    }
}