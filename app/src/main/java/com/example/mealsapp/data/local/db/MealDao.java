package com.example.mealsapp.data.local.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsapp.data.local.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Delete
    Completable deleteMeal(MealEntity meal);

    @Query("SELECT * FROM meals")
    Flowable<List<MealEntity>> getAllMeals();
}
