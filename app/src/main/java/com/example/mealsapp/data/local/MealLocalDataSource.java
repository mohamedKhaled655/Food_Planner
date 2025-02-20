package com.example.mealsapp.data.local;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealLocalDataSource {
    Completable addMealToFavourites(MealEntity meal);
    Completable removeMealToFavourites(MealEntity meal);
    Flowable<List<MealEntity>> getAllFavoriteMeals();

}
