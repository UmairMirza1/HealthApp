package com.smd.health.data;

public class Meal {

    String name;
    String calories;

    Meal(){}

    public Meal(String name, String calories) {
        this.name = name;
        this.calories = calories;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
