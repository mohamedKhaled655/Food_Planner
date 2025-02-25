package com.example.mealsapp.planned_meal_fragment.view;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

public interface OnRemovePlannedClickListener {
    void onRemoveFromPlannedMeal(PlannedMealEntity plannedMealEntity);
    String getUserId();
}
