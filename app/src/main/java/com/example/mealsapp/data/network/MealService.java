package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.CategoryResponse;
import com.example.mealsapp.data.Models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    @GET("search.php?s=")
    Call<MealResponse>getMeals();
}
