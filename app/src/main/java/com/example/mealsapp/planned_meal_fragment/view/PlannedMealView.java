package com.example.mealsapp.planned_meal_fragment.view;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

import java.util.List;

public interface PlannedMealView {
    public void showAllPlannedMeals(List<PlannedMealEntity> plannedMealEntities);
    public void showPlannedMealsByDate(String date);
    public void showErrorMsg(String err);
}
