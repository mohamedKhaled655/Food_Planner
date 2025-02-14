package com.example.mealsapp.home_fragment.presenter;

import android.util.Log;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallback;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.home_fragment.view.HomeMealView;

import java.util.List;

public class HomePresenterImpl implements HomePresenter, NetworkCallback , NetworkCallBackForCategory {
    private static final String TAG = "HomePresenterImpl";
   private HomeMealView homeMealView;
   private MealRepository repository;

    public HomePresenterImpl(HomeMealView homeMealView, MealRepository repository) {
        this.homeMealView = homeMealView;
        this.repository = repository;
    }


    @Override
    public void getMeals() {
        repository.getAllMeals(this);
        Log.i(TAG, "getMeals: ");
    }

    @Override
    public void getCategories() {
        repository.getCategoryMeals(this);
    }

    @Override
    public void addMealToFav(MealEntity meal) {
        repository.addMealToFav(meal);
    }

    @Override
    public void onSuccessResult(List<MealModel> products) {
        homeMealView.showAllMealsData(products);
        //Log.i(TAG, "onSuccessResult: "+products);
       // homeMealView.showAllCategoryData(products);
    }

    @Override
    public void onFailureResult(String errMessage) {
        homeMealView.showErrorMsg(errMessage);
        Log.i(TAG, "onFailureResult: "+errMessage);
    }

    @Override
    public void onSuccessResultForCategory(List<CategoryModel> categoryModels) {
        homeMealView.showAllCategoryData(categoryModels);
    }

    @Override
    public void onFailureResultForCategory(String errMessage) {
        homeMealView.showErrorMsg(errMessage);
        Log.i(TAG, "onFailureResultForCategory: "+errMessage);
    }
}
