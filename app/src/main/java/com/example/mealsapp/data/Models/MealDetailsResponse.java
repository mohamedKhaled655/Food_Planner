package com.example.mealsapp.data.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealDetailsResponse {
    @SerializedName("meals")
    private List<MealDetailsModel> meals;

    public List<MealDetailsModel> getMeals() {
        return meals;
    }
}
