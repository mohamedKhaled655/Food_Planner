package com.example.mealsapp.favourite_fragment.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.favourite_fragment.view.FavoriteMealView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavMealPresenterImpl implements FavMealPresenter{
    private static final String TAG = "FavMealPresenterImpl";
    private FavoriteMealView favoriteMealView;
    private MealRepository repository;

    public FavMealPresenterImpl(FavoriteMealView favoriteMealView, MealRepository repository) {
        this.favoriteMealView = favoriteMealView;
        this.repository = repository;
    }

    @Override
    public void getFavMeals() {

        repository.getStoredFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favMeal -> {
                            if (favoriteMealView != null) {
                                favoriteMealView.showFavData(favMeal);
                            }
                        },
                        error -> {
                            if (favoriteMealView != null) {
                                favoriteMealView.showErrorMsg("Error loading favorites: " + error.getMessage());
                                Log.e(TAG, "Error loading favorites", error);
                            }
                        }
                );
    }

    @Override
    public void removeFromFav(MealEntity meal) {
        repository.removeMealToFav(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d(TAG, "Successfully removed: " + meal.getId());
                        },
                        error -> {
                            favoriteMealView.showErrorMsg("Failed to remove: " + error.getMessage());
                            Log.e(TAG, "Error removing Meal", error);
                        }
                );

    }

    @Override
    public void addMealFromFav(MealEntity meal) {
      repository.addMealToFav(meal)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      () -> {
                          Log.d(TAG, "Successfully Added: " + meal.getId());
                      },
                      error -> {
                          favoriteMealView.showErrorMsg("Failed to Added: " + error.getMessage());
                          Log.e(TAG, "Error Added Meal", error);
                      }
              );
    }
}
