package com.example.mealsapp.data.repo;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallNBackForArea;
import com.example.mealsapp.data.network.NetworkCallNBackForIngredient;
import com.example.mealsapp.data.network.NetworkCallNBackForSearchCategory;
import com.example.mealsapp.data.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    public Single<List<MealModel>> getAllMeals();
    public Single<List<CategoryModel>> getCategoryMeals();
    public Single<List<CategorySearchModel>> getCategoryForSearch();
    public Single<List<AreaModel>> getAreaForSearch();
    public Single<List<IngredientModel>> getForIngredientSearch();
    public Single<List<MealDetailsModel>> getMealDetails(String mealId);

    /*
    public void getAllMeals(NetworkCallback networkCallback);
    public void getCategoryMeals(NetworkCallBackForCategory networkCallback);
    public void getCategoryForSearch(NetworkCallNBackForSearchCategory networkCallback);
    public void getAreaForSearch(NetworkCallNBackForArea networkCallback);
    public void getForIngredientSearch(NetworkCallNBackForIngredient networkCallback);
    */
    public Flowable<List<MealEntity>> getStoredFavMeals();
    public Completable addMealToFav(MealEntity meal);
    public Completable removeMealToFav(MealEntity meal);


}
