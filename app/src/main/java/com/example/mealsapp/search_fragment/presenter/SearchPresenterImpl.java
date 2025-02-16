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

public class SearchPresenterImpl implements SearchPresenter, NetworkCallNBackForIngredient, NetworkCallNBackForSearchCategory, NetworkCallNBackForArea {
    private static final String TAG = "SearchPresenterImpl";
    private SearchMealView searchMealView;
    private MealRepository repository;

    public SearchPresenterImpl(SearchMealView searchMealView, MealRepository repository) {
        this.searchMealView = searchMealView;
        this.repository = repository;
    }

    @Override
    public void getSearchedArea() {
        repository.getAreaForSearch(this);
    }

    @Override
    public void getSearchedIngredient() {
        repository.getForIngredientSearch(this);
    }

    @Override
    public void getSearchedCategories() {
        repository.getCategoryForSearch(this);
    }

    @Override
    public void onSuccessForIngredientResult(List<IngredientModel> ingredientModels) {
        searchMealView.showSearchByIngredient(ingredientModels);
    }

    @Override
    public void onFailureForIngredientResult(String errMessage) {
        searchMealView.showErrorMsg(errMessage);
    }

    @Override
    public void onSuccessForAreaResult(List<AreaModel> areaModels) {
        searchMealView.showSearchByArea(areaModels);
    }

    @Override
    public void onFailureForAreaResult(String errMessage) {
        searchMealView.showErrorMsg(errMessage);
    }

    @Override
    public void onSuccessForCategorySearchResult(List<CategorySearchModel> categorySearchModels) {

        searchMealView.showSearchByCategory(categorySearchModels);
    }

    @Override
    public void onFailureForCategorySearchResult(String errMessage) {
        searchMealView.showErrorMsg(errMessage);
    }
}
