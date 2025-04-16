package com.calorietracker;

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<MealItem> getItems() {
        return new ArrayList<>(items);
    }

    private static class MealItem {
        private Food food;
        private double quantity;

        public MealItem(Food food, double quantity) {
            this.food = food;
            this.quantity = quantity;
        }

        public Food getFood() {
            return food;
        }

        public double getQuantity() {
            return quantity;
        }
    }
}