package com.example.mealsapp.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

@Database(entities = {MealEntity.class, PlannedMealEntity.class},version = 6)
public abstract class MealDatabase extends RoomDatabase {
    private static MealDatabase instance;
   public abstract MealDao mealDao();
    public abstract PlannedMealDao plannedMealDao();
    public static synchronized MealDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),MealDatabase.class,"meal_database")
                    //.fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
