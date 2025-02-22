package com.example.mealsapp.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.db.MealDao;
import com.example.mealsapp.data.local.db.MealDatabase;
import com.example.mealsapp.data.local.db.PlannedMealDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSourceImpl implements MealLocalDataSource{
    private final MealDao mealDao;
    private final PlannedMealDao plannedMealDao;
    private Context context;
    private Flowable<List<MealEntity>> storedMeals;
    private static MealLocalDataSourceImpl mealLocalDataSource=null;
    public MealLocalDataSourceImpl(Context _context){
        this.context=_context;
        MealDatabase db=MealDatabase.getInstance(context.getApplicationContext());
        mealDao= db.mealDao();
        plannedMealDao= db.plannedMealDao();
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

    @Override
    public Completable insertPlannedMeal(PlannedMealEntity plannedMeal) {
        return plannedMealDao.insertPlannedMeal(plannedMeal);
    }

    @Override
    public Completable deletePlannedMeal(PlannedMealEntity plannedMeal) {
        return plannedMealDao.deletePlannedMeal(plannedMeal);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date) {
        return plannedMealDao.getPlannedMealsByDate(date);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getAllPlannedMeals() {
        return plannedMealDao.getAllPlannedMeals();
    }


}
