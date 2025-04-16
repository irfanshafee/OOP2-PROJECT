=== Calorie Tracker Application ===

Welcome to the Calorie Tracker Application! This guide will help you get started with using the application effectively.

=== How to Run the Program ===

1. Ensure you have Java installed on your system (Java 11 or higher recommended)
2. Navigate to the project directory in your terminal
3. Compile the Java files:
   javac -d target/classes src/main/java/com/calorietracker/**/*.java
4. Run the application:
   java -cp target/classes com.calorietracker.Main

=== Features Guide ===

1. Log a Meal
   - Select option 1 from the main menu
   - Enter the food name
   - Specify the quantity in grams
   - Choose the meal type (Breakfast, Lunch, Dinner, or Snack)
   - Input the date and time in format: yyyy-MM-dd HH:mm

2. View Daily Summary
   - Select option 2 from the main menu
   - Enter the date to view summary
   - View detailed breakdown of meals and nutritional totals

3. Edit a Meal
   - Select option 3 from the main menu
   - Enter the meal ID you wish to edit
   - Update any of the following:
     * Food name
     * Quantity
     * Meal type
   - Press Enter to keep existing values

4. Delete a Meal
   - Select option 4 from the main menu
   - Enter the meal ID to delete
   - Confirm deletion by typing 'yes'

=== Data Storage ===

1. Food Database
   - Located in: data/default_foods.txt
   - Contains pre-defined food items with nutritional information
   - Format: food_name,calories,protein,carbs,fats

2. Meal Logs
   - Stored in the application's data directory
   - Each meal entry contains:
     * Unique ID
     * Food name
     * Quantity
     * Meal type
     * Date and time
     * Nutritional information

=== Tips ===

- Always use the correct date format (yyyy-MM-dd HH:mm) when entering dates
- Keep track of meal IDs for easy editing and deletion
- Review your daily summary regularly to track your nutritional goals
- Make sure to confirm before deleting any meal entries