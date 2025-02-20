package com.example.mealsapp.details_fragement.view;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface DetailsMealView {
    public void showAllMealsDetails(List<MealDetailsModel> models);

    public void showErrorMsg(String err);
}
