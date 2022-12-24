package com.smd.health.data;

import java.util.ArrayList;

public interface IMealDao {

    void saveMeal(Meal meal);
    ArrayList<Meal> loadMeals();
    void setMealsChangedListener(MealsChangeListener listener);


    public interface MealsChangeListener {
        void mealsChanged(ArrayList<Meal> meals);
    }
}
