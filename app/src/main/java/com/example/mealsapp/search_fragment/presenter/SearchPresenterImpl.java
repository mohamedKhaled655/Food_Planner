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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {
    private static final String TAG = "SearchPresenterImpl";
    private SearchMealView searchMealView;
    private MealRepository repository;

    private List<CategorySearchModel> originalCategoryList = new ArrayList<>();
    private List<AreaModel> originalAreaList = new ArrayList<>();
    private List<IngredientModel> originalIngredientList = new ArrayList<>();

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
                            originalAreaList = areaModels;
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
                            originalIngredientList = ingredientModels;
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
                            originalCategoryList = categorySearchModels;
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

    @Override
    public void filterCategoriesByQuery(String query) {
        List<CategorySearchModel> filteredList = new ArrayList<>();
        for (CategorySearchModel category : originalCategoryList) {
            if (category.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }
        if (searchMealView != null) {
            searchMealView.showSearchByCategory(filteredList);
        }
    }

    @Override
    public void filterAreasByQuery(String query) {
        List<AreaModel>filteredList = new ArrayList<>();
        for (AreaModel areaModel:originalAreaList){
            if(areaModel.getStrArea().toLowerCase().contains(query.toLowerCase())){
                filteredList.add(areaModel);
            }
        }
        if(searchMealView !=null){
            searchMealView.showSearchByArea(filteredList);
        }
    }

    @Override
    public void filterIngredientsByQuery(String query) {
        List<IngredientModel> filteredList = new ArrayList<>();
        for (IngredientModel ingredient : originalIngredientList) {
            if (ingredient.getStrIngredient().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ingredient);
            }
        }
        if (searchMealView != null) {
            searchMealView.showSearchByIngredient(filteredList);
        }
    }

    @Override
    public void onDestroy() {
        searchMealView = null;
    }


}
