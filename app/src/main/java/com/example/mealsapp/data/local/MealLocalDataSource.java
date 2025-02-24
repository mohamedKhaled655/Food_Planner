package com.example.mealsapp.data.local;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealLocalDataSource {
    Completable addMealToFavourites(MealEntity meal);
    Completable removeMealToFavourites(MealEntity meal);
    Flowable<List<MealEntity>> getAllFavoriteMeals();


    Completable insertPlannedMeal(PlannedMealEntity plannedMeal);
    Completable deletePlannedMeal(PlannedMealEntity plannedMeal);
    Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date);
    Flowable<List<PlannedMealEntity>> getAllPlannedMeals();

    //////

    void setUserIdToSharedPref(String userId);
    String getUserIdFromSharedPref();

}
