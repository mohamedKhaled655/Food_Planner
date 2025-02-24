package com.example.mealsapp.data.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.FirebaseSyncService;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.MealLocalDataSource;
import com.example.mealsapp.data.local.PlannedMealEntity;
import com.example.mealsapp.data.network.MealRemoteDataSource;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallNBackForArea;
import com.example.mealsapp.data.network.NetworkCallNBackForIngredient;
import com.example.mealsapp.data.network.NetworkCallNBackForSearchCategory;
import com.example.mealsapp.data.network.NetworkCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealRepositoryImpl implements MealRepository{
    private static final String TAG = "MealRepositoryImpl";
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;

    private FirebaseSyncService syncService;
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
    public Single<List<MealModel>> getAllMealsByCategory(String category) {
        return mealRemoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<List<MealModel>> getAllMealsByArea(String area) {
        return mealRemoteDataSource.getMealsByArea(area);
    }

    @Override
    public Single<List<MealModel>> getAllMealsByIngredient(String ingredient) {
        return mealRemoteDataSource.getMealsByIngredient(ingredient);
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

    @Override
    public Completable insertPlannedMeal(PlannedMealEntity plannedMeal) {
        return mealLocalDataSource.insertPlannedMeal(plannedMeal);
    }

    @Override
    public Completable deletePlannedMeal(PlannedMealEntity plannedMeal) {
        return mealLocalDataSource.deletePlannedMeal(plannedMeal);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getPlannedMealsByDate(String date) {
        return mealLocalDataSource.getPlannedMealsByDate(date);
    }

    @Override
    public Flowable<List<PlannedMealEntity>> getAllPlannedMeals() {
        return mealLocalDataSource.getAllPlannedMeals();
    }

    @Override
    public void setUserIdToSharedPref(String userId) {
        mealLocalDataSource.setUserIdToSharedPref(userId);
        syncService = new FirebaseSyncService(userId);
    }

    @Override
    public String getUserIdFromSharedPref() {
        return mealLocalDataSource.getUserIdFromSharedPref();
    }

    @Override
    public Completable syncFavoritesToCloud() {
        final String userId = getUserIdFromSharedPref();
        if (userId.isEmpty()) {
            return Completable.error(new IllegalStateException("User not logged in"));
        }

        if (syncService == null) {
            syncService = new FirebaseSyncService(userId);
        }
        return getStoredFavMeals()
                .firstElement()
                .flatMapCompletable(allMeals -> {

                    List<MealEntity> userMeals = new ArrayList<>();
                    for (MealEntity meal : allMeals) {
                        if (userId.equals(meal.getUserId()) && meal.isFavorite()) {
                            userMeals.add(meal);
                        }
                    }
                    return syncService.backupUserFavorites(userMeals);
                });
    }

    @Override
    public Completable restoreFavoritesFromCloud() {
        final String userId = getUserIdFromSharedPref();
        if (userId.isEmpty()) {
            return Completable.error(new IllegalStateException("User not logged in"));
        }

        if (syncService == null) {
            syncService = new FirebaseSyncService(userId);
        }
        return syncService.restoreUserFavorites()
                .flatMapCompletable(cloudMeals -> {
                    if (cloudMeals.isEmpty()) {
                        Log.d(TAG, "No meals to restore from cloud");
                        return Completable.complete();
                    }

                    return Completable.fromAction(() -> {
                        for (MealEntity meal : cloudMeals) {

                            meal.setUserId(userId);

                            meal.setFavorite(true);

                            mealLocalDataSource.addMealToFavourites(meal);
                        }
                        Log.d(TAG, "Restored " + cloudMeals.size() + " meals from cloud");
                    });
                });
    }
}
