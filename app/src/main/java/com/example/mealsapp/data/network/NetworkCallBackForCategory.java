package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface NetworkCallBackForCategory {
    public void onSuccessResultForCategory(List<CategoryModel> categoryModels);
    public void onFailureResultForCategory(String errMessage);
}
