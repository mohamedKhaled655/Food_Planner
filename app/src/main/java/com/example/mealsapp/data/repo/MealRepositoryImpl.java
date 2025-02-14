package com.example.mealsapp.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.MealLocalDataSource;
import com.example.mealsapp.data.network.MealRemoteDataSource;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallback;

import java.util.List;

public class MealRepositoryImpl implements MealRepository{
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    private static MealRepositoryImpl repo=null;
    public static MealRepositoryImpl getInstance(MealRemoteDataSource remoteDataSource,MealLocalDataSource localDataSource){
        if(repo==null){
            repo=new MealRepositoryImpl(remoteDataSource,localDataSource);
        }
        return repo;
    }

    private MealRepositoryImpl(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public LiveData<List<MealEntity>> getStoredFavMeals() {
        return mealLocalDataSource.getAllFavoriteMeals();
    }

    @Override
    public void getAllMeals(NetworkCallback networkCallback) {
        mealRemoteDataSource.makeNetworkCall(networkCallback);
    }

    @Override
    public void getCategoryMeals(NetworkCallBackForCategory networkCallback) {
        mealRemoteDataSource.makeNetworkCallForCategory(networkCallback);
    }

    @Override
    public void addMealToFav(MealEntity meal) {
        mealLocalDataSource.addMealToFavourites(meal);
    }

    @Override
    public void removeMealToFav(MealEntity meal) {
        mealLocalDataSource.removeMealToFavourites(meal);
    }
}
