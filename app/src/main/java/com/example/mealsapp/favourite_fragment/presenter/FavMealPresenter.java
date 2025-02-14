package com.example.mealsapp.favourite_fragment.presenter;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;

import java.util.List;

public interface FavMealPresenter {
    public LiveData<List<MealEntity>> getFavMealss();
    public void removeFromFav(MealEntity meal);
    public void addMealFromFav(MealEntity meal);
}
