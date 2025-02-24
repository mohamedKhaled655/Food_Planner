package com.example.mealsapp.meals_fragment.presenter;

import android.util.Log;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.home_fragment.view.HomeMealView;
import com.example.mealsapp.meals_fragment.view.MealsView;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenterImpl implements MealPresenter{
    private static final String TAG = "MealPresenterImpl";
    private MealsView mealsView;
    private MealRepository repository;
    private FirebaseAuth mAuth;

    public MealPresenterImpl(MealsView mealsView, MealRepository repository) {
        this.mealsView = mealsView;
        this.repository = repository;
        mAuth = FirebaseAuth.getInstance();
        repository.setUserIdToSharedPref(mAuth.getUid());
    }

    @Override
    public void getMealsByCat(String mealName) {
        repository.getAllMealsByCategory(mealName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModels -> {
                            mealsView.showAllMeals(mealModels);
                            Log.i(TAG, "get mealModels: " + mealModels.size() + " mealModels loaded");
                        },
                        error -> {
                            if (mealsView != null) {
                                mealsView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );
    }

    @Override
    public void getMealsByArea(String mealName) {
        repository.getAllMealsByArea(mealName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModels -> {
                            mealsView.showAllMeals(mealModels);
                            Log.i(TAG, "get mealModels: " + mealModels.size() + " mealModels loaded");
                        },
                        error -> {
                            if (mealsView != null) {
                                mealsView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );
    }

    @Override
    public void getMealsByIngr(String mealName) {
        repository.getAllMealsByIngredient(mealName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModels -> {
                            mealsView.showAllMeals(mealModels);
                            Log.i(TAG, "get mealModels: " + mealModels.size() + " mealModels loaded");
                        },
                        error -> {
                            if (mealsView != null) {
                                mealsView.showErrorMsg(error.getMessage());
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

    @Override
    public void removeMealToFav(MealEntity meal) {
        repository.removeMealToFav(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "Meal remove from favorites"),
                        error -> Log.e(TAG, "Error removing Meal from favorites", error)
                );
    }

    @Override
    public String getUserId() {
        return repository.getUserIdFromSharedPref();
    }
}
