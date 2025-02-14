package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessResult(List<MealModel> products);
    public void onFailureResult(String errMessage);
}
