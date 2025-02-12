package com.example.mealsapp;

import com.example.mealsapp.Models.CategoryResponse;
import com.example.mealsapp.Models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("search.php?s=")
    Call<MealResponse>getMeals();
}
