package com.example.mealsapp.data.local.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsapp.data.local.MealEntity;

import java.util.List;
@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealEntity meal);

    @Delete
    void deleteMeal(MealEntity meal);

    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>> getAllMeals();
}
