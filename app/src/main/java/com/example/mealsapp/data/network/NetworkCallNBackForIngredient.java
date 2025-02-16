package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.IngredientModel;

import java.util.List;

public interface NetworkCallNBackForIngredient {
    public void onSuccessForIngredientResult(List<IngredientModel> ingredientModels);
    public void onFailureForIngredientResult(String errMessage);
}
