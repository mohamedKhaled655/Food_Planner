package com.example.mealsapp.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.db.MealDao;
import com.example.mealsapp.data.local.db.MealDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private final MealDao mealDao;
    private Context context;
    private Flowable<List<MealEntity>> storedMeals;
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
    public Completable addMealToFavourites(MealEntity meal) {
       // new Thread(() -> mealDao.insertMeal(meal)).start();
        return mealDao.insertMeal(meal);
    }

    @Override
    public Completable removeMealToFavourites(MealEntity meal) {
        //new Thread(() -> mealDao.deleteMeal(meal)).start();
        return mealDao.deleteMeal(meal);
    }

    @Override
    public Flowable<List<MealEntity>> getAllFavoriteMeals() {
        return storedMeals;
    }


}
