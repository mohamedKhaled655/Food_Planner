package com.example.mealsapp.home_fragment.view;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

public interface OnAddFavClickListener extends  OnFavClickLisenter{
    void onAddToFavorite(MealEntity mealEntity);
    void onRemoveFromFavorite(MealEntity mealEntity);

    void onAddToPlannedMeal(PlannedMealEntity plannedMealEntity);
}
