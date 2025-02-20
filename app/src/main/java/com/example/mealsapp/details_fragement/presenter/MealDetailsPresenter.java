package com.example.mealsapp.details_fragement.presenter;

import com.example.mealsapp.data.local.MealEntity;

public interface MealDetailsPresenter {
    public void getMealDetails(String mealId);

    public void addMealToFav(MealEntity meal);
}
