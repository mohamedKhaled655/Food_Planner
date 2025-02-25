package com.example.mealsapp.home_fragment.view;

import android.content.Context;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;

import java.util.List;

public interface HomeMealView {
     void showAllMealsData(List<MealModel> models);
     void showAllCategoryData(List<CategoryModel> models);
     void showErrorMsg(String err);

    void showLoading();
    void hideLoading();
    void showNoInternetLayout();
    void hideNoInternetLayout();
    Context getViewContext();


}
