package com.example.mealsapp.home_fragment.view;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface HomeMealView {
    public void showAllMealsData(List<MealModel> models);
    public void showAllCategoryData(List<CategoryModel> models);
    public void showErrorMsg(String err);


}
