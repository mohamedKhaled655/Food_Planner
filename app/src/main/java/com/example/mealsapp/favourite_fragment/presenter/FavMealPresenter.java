package com.example.mealsapp.favourite_fragment.presenter;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;

import java.util.List;

public interface FavMealPresenter {
    public void getFavMeals();
    public void removeFromFav(MealEntity meal);
    public void addMealFromFav(MealEntity meal);

    String getUserId();
    //////
    public void syncFavoritesToCloud();
    public void restoreFavoritesFromCloud();

}
