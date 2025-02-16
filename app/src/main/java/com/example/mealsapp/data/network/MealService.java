package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.AreaResponse;
import com.example.mealsapp.data.Models.CategoryResponse;
import com.example.mealsapp.data.Models.CategorySearchResponse;
import com.example.mealsapp.data.Models.IngredientResponse;
import com.example.mealsapp.data.Models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("search.php?s=")
    Call<MealResponse>getMeals();

    @GET("list.php?i=list")
    Call<IngredientResponse>getIngredientsForSearch();

    @GET("list.php?a=list")
    Call<AreaResponse>getAreasForSearch();

    @GET("list.php?c=list")
    Call<CategorySearchResponse>getCategoryForSearch();
}
