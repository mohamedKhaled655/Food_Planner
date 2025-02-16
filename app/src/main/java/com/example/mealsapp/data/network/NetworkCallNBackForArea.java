package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface NetworkCallNBackForArea {
    public void onSuccessForAreaResult(List<AreaModel> areaModels);
    public void onFailureForAreaResult(String errMessage);
}
