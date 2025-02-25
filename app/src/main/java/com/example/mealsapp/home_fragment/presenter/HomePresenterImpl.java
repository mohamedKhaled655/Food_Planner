package com.example.mealsapp.home_fragment.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;
import com.example.mealsapp.data.network.NetworkCallBackForCategory;
import com.example.mealsapp.data.network.NetworkCallback;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.home_fragment.view.HomeMealView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenterImpl";
   private HomeMealView homeMealView;
   private MealRepository repository;
    private FirebaseAuth mAuth;
    private ConnectivityManager.NetworkCallback networkCallback;

    public HomePresenterImpl(HomeMealView homeMealView, MealRepository repository) {
        this.homeMealView = homeMealView;
        this.repository = repository;
        mAuth = FirebaseAuth.getInstance();
        repository.setUserIdToSharedPref(mAuth.getUid());
    }


    @Override
    public void getMeals() {
        Single<List<MealModel>> remoteMeals = repository.getAllMeals();
        Flowable<List<MealEntity>> localFavorites = repository.getStoredFavMeals();
        String currentUserId = getUserId();
        Single.zip(
                        remoteMeals,
                        localFavorites.firstOrError(),
                        (meals, favorites) -> {

                            for (MealModel meal : meals) {

                                for (MealEntity favMeal : favorites) {
                                    if (meal.getIdMeal() != null && favMeal.getId() != null && meal.getIdMeal().equals(favMeal.getId()) &&
                                            currentUserId != null && currentUserId.equals(favMeal.getUserId())) {
                                        meal.setFavorite(true);
                                        break;
                                    }
                                }
                            }
                            return meals;
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModels -> {
                            if (homeMealView != null) {
                                homeMealView.showAllMealsData(mealModels);
                            }
                        },
                        error -> {
                            if (homeMealView != null) {
                                homeMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading meals", error);
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
    public void addToPlannedMeal(PlannedMealEntity plannedMealEntity) {
        repository.insertPlannedMeal(plannedMealEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "Meal added to plannedMeal"),
                        error -> Log.e(TAG, "Error adding Meal to plannedMeal", error)
                );
    }

    @Override
    public String getUserId() {
        return repository.getUserIdFromSharedPref();
    }

    @Override
    public void setUserId(String userId) {
        repository.setUserIdToSharedPref(userId);
    }

    @Override
    public void startNetworkMonitoring() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                homeMealView.getViewContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {

                new Handler(Looper.getMainLooper()).post(() -> {
                    homeMealView.hideNoInternetLayout();
                    loadData();
                });
            }

            @Override
            public void onLost(Network network) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    homeMealView.showNoInternetLayout();
                });
            }
        };

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }
    }

    @Override
    public void stopNetworkMonitoring() {
        if (networkCallback != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    homeMealView.getViewContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                homeMealView.getViewContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    public void loadData() {
        if (isNetworkAvailable()) {
            homeMealView.showLoading();
            getMeals();
            getCategories();
        } else {
            homeMealView.showNoInternetLayout();
        }
    }


}
