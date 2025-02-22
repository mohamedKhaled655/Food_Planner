package com.example.mealsapp.planned_meal_fragment.presenter;

import android.util.Log;

import com.example.mealsapp.data.local.PlannedMealEntity;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.planned_meal_fragment.view.PlannedMealView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannedMealPresenterImpl implements PlannedMealPresenter{
    private static final String TAG = "PlannedMealPresenterImp";
    private PlannedMealView plannedMealView;
    private MealRepository repository;

    public PlannedMealPresenterImpl(PlannedMealView plannedMealView, MealRepository repository) {
        this.plannedMealView = plannedMealView;
        this.repository = repository;
    }

    @Override
    public void getPlannedMealsByData(String data) {
        repository.getPlannedMealsByDate(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        plannedMealEntities -> {
                            Log.d(TAG, "Found " + plannedMealEntities.size() + " meals for date: '" + data + "'");
                            for (PlannedMealEntity meal : plannedMealEntities) {
                                Log.d(TAG, "Meal: " + meal.getMealName() + " - Stored Date: '" + meal.getPlannedDate() + "'");
                            }
                            if (plannedMealView != null) {
                                plannedMealView.showAllPlannedMeals(plannedMealEntities);
                            }
                        },
                        error -> {
                            if (plannedMealView != null) {
                                plannedMealView.showErrorMsg("Error loading Planned: " + error.getMessage());
                                Log.e(TAG, "Error loading Planned", error);
                            }
                        }
                );
    }

    @Override
    public void getPlannedMeals() {
        repository.getAllPlannedMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        plannedMealEntities -> {
                            if (plannedMealView != null) {
                                plannedMealView.showAllPlannedMeals(plannedMealEntities);
                                Log.i(TAG, "showAllPlannedMeals: "+plannedMealEntities.get(0).getPlannedDate());
                            }
                        },
                        error -> {
                            if (plannedMealView != null) {
                                plannedMealView.showErrorMsg("Error loading Planned: " + error.getMessage());
                                Log.e(TAG, "Error loading Planned", error);
                            }
                        }
                );
    }

    @Override
    public void removeFromFav(PlannedMealEntity meal) {
        repository.deletePlannedMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d(TAG, "Successfully removed: " + meal.getId());
                        },
                        error -> {
                            plannedMealView.showErrorMsg("Failed to remove: " + error.getMessage());
                            Log.e(TAG, "Error removing Meal", error);
                        }
                );
    }
}
