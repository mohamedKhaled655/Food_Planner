package com.example.mealsapp.search_fragment.presenter;

import android.util.Log;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.network.NetworkCallNBackForArea;
import com.example.mealsapp.data.network.NetworkCallNBackForIngredient;
import com.example.mealsapp.data.network.NetworkCallNBackForSearchCategory;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.search_fragment.view.SearchMealView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private static final String TAG = "SearchPresenterImpl";
    private SearchMealView searchMealView;
    private MealRepository repository;

    public SearchPresenterImpl(SearchMealView searchMealView, MealRepository repository) {
        this.searchMealView = searchMealView;
        this.repository = repository;
    }

    @Override
    public void getSearchedArea() {

        repository.getAreaForSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        areaModels -> {
                            searchMealView.showSearchByArea(areaModels);
                            Log.i(TAG, "getCountries: " + areaModels.size() + " Countries loaded");
                        },
                        error -> {
                            if (searchMealView != null) {
                                searchMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );
    }

    @Override
    public void getSearchedIngredient() {
        repository.getForIngredientSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredientModels -> {
                            searchMealView.showSearchByIngredient(ingredientModels);
                            Log.i(TAG, "get ingredientModels: " + ingredientModels.size() + " ingredient loaded");
                        },
                        error -> {
                            if (searchMealView != null) {
                                searchMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );
    }

    @Override
    public void getSearchedCategories() {
        repository.getCategoryForSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categorySearchModels -> {
                            searchMealView.showSearchByCategory(categorySearchModels);
                            Log.i(TAG, "get categorySearchModels: " + categorySearchModels.size() + " categorySearchModels loaded");
                        },
                        error -> {
                            if (searchMealView != null) {
                                searchMealView.showErrorMsg(error.getMessage());
                                Log.e(TAG, "Error loading Areas", error);
                            }
                        }
                );
    }


}
