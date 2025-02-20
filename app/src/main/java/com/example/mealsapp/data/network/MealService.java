package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.AreaResponse;
import com.example.mealsapp.data.Models.CategoryResponse;
import com.example.mealsapp.data.Models.CategorySearchResponse;
import com.example.mealsapp.data.Models.IngredientResponse;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealDetailsResponse;
import com.example.mealsapp.data.Models.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("search.php?s=")
    Single<MealResponse>getMeals();

    @GET("list.php?i=list")
    Single<IngredientResponse>getIngredientsForSearch();

    @GET("list.php?a=list")
    Single<AreaResponse>getAreasForSearch();

    @GET("list.php?c=list")
    Single<CategorySearchResponse>getCategoryForSearch();

    @GET("lookup.php")
    Single<MealDetailsResponse> getMealDetails(@Query("i") String id);
}
