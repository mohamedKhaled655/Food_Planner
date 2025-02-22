package com.example.mealsapp.data.local.db;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealsapp.data.local.PlannedMealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface PlannedMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlannedMeal(PlannedMealEntity plannedMeal);

    @Delete
    Completable deletePlannedMeal(PlannedMealEntity plannedMeal);

    @Query("SELECT * FROM planned_meals WHERE plannedDate = :date")
    Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date);

    @Query("SELECT * FROM planned_meals ORDER BY plannedDate ASC")
    Flowable<List<PlannedMealEntity>> getAllPlannedMeals();

    @Query("DELETE FROM planned_meals WHERE mealId = :mealId AND plannedDate = :date")
    Completable deletePlannedMealByIdAndDate(String mealId, String date);
}
