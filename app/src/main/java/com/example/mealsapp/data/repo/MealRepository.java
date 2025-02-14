package com.example.mealsapp.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallback;

import java.util.List;

public interface MealRepository {
    public LiveData<List<MealEntity>>getStoredFavMeals();
    public void getAllMeals(NetworkCallback networkCallback);
    public void getCategoryMeals(NetworkCallBackForCategory networkCallback);
    public void addMealToFav(MealEntity meal);
    public void removeMealToFav(MealEntity meal);


}
