package com.example.mealsapp.meals_fragment.presenter;

import com.example.mealsapp.data.local.MealEntity;

public interface MealPresenter {
    public void getMealsByCat(String mealName);
    public void getMealsByArea(String mealName);
    public void getMealsByIngr(String mealName);

    public void addMealToFav(MealEntity meal);
    public void removeMealToFav(MealEntity meal);
}
