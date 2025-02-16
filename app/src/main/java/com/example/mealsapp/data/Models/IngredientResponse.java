package com.example.mealsapp.data.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<IngredientModel> ingredientModels;

    public List<IngredientModel> getIngredientModels() {
        return ingredientModels;
    }
}
