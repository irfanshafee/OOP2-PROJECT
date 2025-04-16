package com.calorietracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String id;
    private String name;
    private LocalDateTime dateTime;
    private List<MealItem> items;

    public Meal(String id, String name, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.items = new ArrayList<>();
    }

    public void addItem(Food food, double quantity) {
        items.add(new MealItem(food, quantity));
    }

    public double getTotalCalories() {
        return items.stream()
                .mapToDouble(item -> item.getFood().getCalories() * item.getQuantity())
                .sum();
    }

    public double getTotalProtein() {
        return items.stream()
                .mapToDouble(item -> item.getFood().getProtein() * item.getQuantity())
                .sum();
    }

    public double getTotalCarbohydrates() {
        return items.stream()
                .mapToDouble(item -> item.getFood().getCarbohydrates() * item.getQuantity())
                .sum();
    }

    public double getTotalFats() {
        return items.stream()
                .mapToDouble(item -> item.getFood().getFats() * item.getQuantity())
                .sum();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public List<MealItem> getItems() { return items; }

    private static class MealItem {
        private Food food;
        private double quantity;

        public MealItem(Food food, double quantity) {
            this.food = food;
            this.quantity = quantity;
        }

        public Food getFood() { return food; }
        public double getQuantity() { return quantity; }
    }
}