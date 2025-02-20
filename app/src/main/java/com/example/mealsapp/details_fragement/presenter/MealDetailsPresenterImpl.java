package com.example.mealsapp.details_fragement.presenter;

import android.util.Log;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.details_fragement.view.DetailsMealView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter{
    private static final String TAG = "MealDetailsPresenterImp";
    private DetailsMealView detailsMealView;
    private MealRepository repository;

    public MealDetailsPresenterImpl(DetailsMealView detailsMealView, MealRepository repository) {
        this.detailsMealView = detailsMealView;
        this.repository = repository;
    }

    @Override
    public void getMealDetails(String mealId) {
        Disposable disposable= repository.getMealDetails(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealDetailsModels -> {
                            detailsMealView.showAllMealsDetails(mealDetailsModels);
                            Log.i(TAG, "get mealDetailsModels: " + mealDetailsModels.size() + " mealDetailsModels loaded");
                        },
                        error -> {
                            if (detailsMealView != null) {
                                detailsMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );

    }

    @Override
    public void addMealToFav(MealEntity meal) {
        Disposable disposable = repository.addMealToFav(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "Meal added to favorites successfully"),
                        error -> Log.e(TAG, "Error adding meal to favorites", error)
                );
    }
}
