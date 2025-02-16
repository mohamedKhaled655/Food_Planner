package com.example.mealsapp.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallNBackForArea;
import com.example.mealsapp.data.network.NetworkCallNBackForIngredient;
import com.example.mealsapp.data.network.NetworkCallNBackForSearchCategory;
import com.example.mealsapp.data.network.NetworkCallback;

import java.util.List;

public interface MealRepository {
    public LiveData<List<MealEntity>>getStoredFavMeals();
    public void getAllMeals(NetworkCallback networkCallback);
    public void getCategoryMeals(NetworkCallBackForCategory networkCallback);
    public void getCategoryForSearch(NetworkCallNBackForSearchCategory networkCallback);
    public void getAreaForSearch(NetworkCallNBackForArea networkCallback);
    public void getForIngredientSearch(NetworkCallNBackForIngredient networkCallback);
    public void addMealToFav(MealEntity meal);
    public void removeMealToFav(MealEntity meal);


}
