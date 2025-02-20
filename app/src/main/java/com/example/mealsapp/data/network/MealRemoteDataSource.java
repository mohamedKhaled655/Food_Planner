package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealRemoteDataSource {
   /*
    void makeNetworkCall(NetworkCallback networkCallback);
    void makeNetworkCallForCategory(NetworkCallBackForCategory networkCallBackForCategory);
    void makeNetworkCallForArea(NetworkCallNBackForArea networkCallNBackForArea);
    void makeNetworkCallForCategorySearch(NetworkCallNBackForSearchCategory networkCallNBackForSearchCategory);
    void makeNetworkCallForIngredientSearch(NetworkCallNBackForIngredient networkCallNBackForIngredient);
    */

    Single<List<MealModel>> getMeals();
    Single<List<CategoryModel>> getCategories();
    Single<List<AreaModel>> getCountries();
    Single<List<CategorySearchModel>> getCategoryForSearch();
    Single<List<IngredientModel>> getIngredientForSearch();
}
