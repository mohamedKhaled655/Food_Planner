package com.example.mealsapp.favourite_fragment.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.favourite_fragment.view.FavoriteMealView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavMealPresenterImpl implements FavMealPresenter{
    private static final String TAG = "FavMealPresenterImpl";
    private FavoriteMealView favoriteMealView;
    private MealRepository repository;
    private  FirebaseAuth mAuth;

    public FavMealPresenterImpl(FavoriteMealView favoriteMealView, MealRepository repository) {
        this.favoriteMealView = favoriteMealView;
        this.repository = repository;
        mAuth = FirebaseAuth.getInstance();
        repository.setUserIdToSharedPref(mAuth.getUid());
    }

    @Override
    public void getFavMeals() {
        String currentUserId = getUserId();

        repository.getStoredFavMeals()
                .map(mealsList -> {
                    List<MealEntity> userFavMeals = new ArrayList<>();
                    for (MealEntity meal : mealsList) {
                        if (currentUserId.equals(meal.getUserId())) {
                            userFavMeals.add(meal);
                        }
                    }
                    return userFavMeals;
                })
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
                            syncFavoritesToCloud();
                        },
                        error -> {
                            favoriteMealView.showErrorMsg("Failed to remove: " + error.getMessage());
                            Log.e(TAG, "Error removing Meal", error);
                        }
                );

    }

    @Override
    public void addMealFromFav(MealEntity meal) {
        meal.setUserId(getUserId());
        meal.setFavorite(true);
        repository.addMealToFav(meal)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      () -> {
                          Log.d(TAG, "Successfully Added: " + meal.getId());
                          syncFavoritesToCloud();
                      },
                      error -> {
                          favoriteMealView.showErrorMsg("Failed to Added: " + error.getMessage());
                          Log.e(TAG, "Error Added Meal", error);
                      }
              );
    }

    @Override
    public String getUserId() {
        return repository.getUserIdFromSharedPref();
    }

    @Override
    public void syncFavoritesToCloud() {
        repository.syncFavoritesToCloud()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            if (favoriteMealView != null) {
                                favoriteMealView.showMessage("Favorites backed up successfully");
                            }
                        },
                        error -> {
                            if (favoriteMealView != null) {
                                favoriteMealView.showMessage("Backup failed: " + error.getMessage());
                            }
                            Log.e(TAG, "Failed to backup favorites", error);
                        }
                );
    }

    @Override
    public void restoreFavoritesFromCloud() {
        repository.restoreFavoritesFromCloud()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            if (favoriteMealView != null) {
                                favoriteMealView.showMessage("Favorites restored successfully");

                                getFavMeals();
                            }
                        },
                        error -> {
                            if (favoriteMealView != null) {
                                favoriteMealView.showErrorMsg("Restore failed: " + error.getMessage());
                            }
                            Log.e(TAG, "Failed to restore favorites", error);
                        }
                );
    }


}
