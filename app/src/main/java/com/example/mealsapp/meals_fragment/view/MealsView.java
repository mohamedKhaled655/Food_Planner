package com.example.mealsapp.meals_fragment.view;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface MealsView {
    public void showAllMeals(List<MealModel> models);

    public void showErrorMsg(String err);
}
