package com.example.mealsapp.home_fragment.presenter;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

public interface HomePresenter {
    public void getMeals();
    public void getCategories();
    public void addMealToFav(MealEntity meal);
    public void removeMealToFav(MealEntity meal);

    public void addToPlannedMeal(PlannedMealEntity plannedMealEntity);

    String getUserId();
    void setUserId(String userId);
////////////
void startNetworkMonitoring();
    void stopNetworkMonitoring();
    boolean isNetworkAvailable();
    void loadData();
}
