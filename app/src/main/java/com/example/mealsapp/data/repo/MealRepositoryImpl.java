package com.example.mealsapp.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.MealLocalDataSource;
import com.example.mealsapp.data.network.MealRemoteDataSource;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallNBackForArea;
import com.example.mealsapp.data.network.NetworkCallNBackForIngredient;
import com.example.mealsapp.data.network.NetworkCallNBackForSearchCategory;
import com.example.mealsapp.data.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

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
    public Single<List<MealModel>> getAllMeals() {
        return mealRemoteDataSource.getMeals();
    }

    @Override
    public Single<List<CategoryModel>> getCategoryMeals() {
        return mealRemoteDataSource.getCategories();
    }

    @Override
    public Single<List<CategorySearchModel>> getCategoryForSearch() {
        return mealRemoteDataSource.getCategoryForSearch();
    }

    @Override
    public Single<List<AreaModel>> getAreaForSearch() {
        return mealRemoteDataSource.getCountries();
    }

    @Override
    public Single<List<IngredientModel>> getForIngredientSearch() {
        return mealRemoteDataSource.getIngredientForSearch();
    }

    @Override
    public Single<List<MealDetailsModel>> getMealDetails(String mealId) {
       return mealRemoteDataSource.getMealDetails(mealId);
    }

    @Override
    public Flowable<List<MealEntity>> getStoredFavMeals() {
        return mealLocalDataSource.getAllFavoriteMeals();
    }

   /*
    @Override
    public void getAllMeals(NetworkCallback networkCallback) {
        mealRemoteDataSource.makeNetworkCall(networkCallback);
    }

    @Override
    public void getCategoryMeals(NetworkCallBackForCategory networkCallback) {
        mealRemoteDataSource.makeNetworkCallForCategory(networkCallback);
    }

    @Override
    public void getCategoryForSearch(NetworkCallNBackForSearchCategory networkCallback) {
        mealRemoteDataSource.makeNetworkCallForCategorySearch(networkCallback);
    }

    @Override
    public void getAreaForSearch(NetworkCallNBackForArea networkCallback) {
        mealRemoteDataSource.makeNetworkCallForArea(networkCallback);
    }

    @Override
    public void getForIngredientSearch(NetworkCallNBackForIngredient networkCallback) {
        mealRemoteDataSource.makeNetworkCallForIngredientSearch(networkCallback);
    }
    */

    @Override
    public Completable addMealToFav(MealEntity meal) {
       return mealLocalDataSource.addMealToFavourites(meal);
    }

    @Override
    public Completable removeMealToFav(MealEntity meal) {
        return mealLocalDataSource.removeMealToFavourites(meal);
    }
}
