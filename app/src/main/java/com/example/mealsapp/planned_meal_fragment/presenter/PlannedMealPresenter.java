package com.example.mealsapp.planned_meal_fragment.presenter;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

public interface PlannedMealPresenter {
    public void getPlannedMealsByData(String data);
    public void getPlannedMeals();
    public void removeFromFav(PlannedMealEntity meal);

    String getUserId();
}
