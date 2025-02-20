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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenterImpl";
   private HomeMealView homeMealView;
   private MealRepository repository;

    public HomePresenterImpl(HomeMealView homeMealView, MealRepository repository) {
        this.homeMealView = homeMealView;
        this.repository = repository;
    }


    @Override
    public void getMeals() {
        repository.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModels -> {
                            homeMealView.showAllMealsData(mealModels);
                            Log.i(TAG, "get mealModels: " + mealModels.size() + " mealModels loaded");
                        },
                        error -> {
                            if (homeMealView != null) {
                                homeMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );

    }

    @Override
    public void getCategories() {
        repository.getCategoryMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryModels -> {
                            homeMealView.showAllCategoryData(categoryModels);
                            Log.i(TAG, "get categoryModels: " + categoryModels.size() + " categoryModels loaded");
                        },
                        error -> {
                            if (homeMealView != null) {
                                homeMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );
    }

    @Override
    public void addMealToFav(MealEntity meal) {

        repository.addMealToFav(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "Meal added to favorites"),
                        error -> Log.e(TAG, "Error adding Meal to favorites", error)
                );
    }



}
