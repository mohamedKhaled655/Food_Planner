package com.example.mealsapp.home_fragment.presenter;

import com.example.mealsapp.data.local.MealEntity;

public interface HomePresenter {
    public void getMeals();
    public void getCategories();
    public void addMealToFav(MealEntity meal);

}
