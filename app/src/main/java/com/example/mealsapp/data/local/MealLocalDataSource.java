package com.example.mealsapp.data.local;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface MealLocalDataSource {
    void addMealToFavourites(MealEntity meal);
    void removeMealToFavourites(MealEntity meal);
    LiveData<List<MealEntity>>getAllFavoriteMeals();

}
