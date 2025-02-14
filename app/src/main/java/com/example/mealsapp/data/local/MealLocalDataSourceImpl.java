package com.example.mealsapp.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.db.MealDao;
import com.example.mealsapp.data.local.db.MealDatabase;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private final MealDao mealDao;
    private Context context;
    private LiveData<List<MealEntity>>storedMeals;
    private static MealLocalDataSourceImpl mealLocalDataSource=null;
    public MealLocalDataSourceImpl(Context _context){
        this.context=_context;
        MealDatabase db=MealDatabase.getInstance(context.getApplicationContext());
        mealDao= db.mealDao();
        storedMeals=mealDao.getAllMeals();
    }

    public static MealLocalDataSourceImpl getInstance(Context _context){
        if(mealLocalDataSource==null){
            mealLocalDataSource=new MealLocalDataSourceImpl(_context);
        }
        return mealLocalDataSource;
    }
    @Override
    public void addMealToFavourites(MealEntity meal) {
        new Thread(() -> mealDao.insertMeal(meal)).start();
    }

    @Override
    public void removeMealToFavourites(MealEntity meal) {
        new Thread(() -> mealDao.deleteMeal(meal)).start();

    }

    @Override
    public LiveData<List<MealEntity>> getAllFavoriteMeals() {
        return storedMeals;
    }


}
