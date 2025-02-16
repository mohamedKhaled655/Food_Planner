package com.example.mealsapp.search_fragment.view;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;

import java.util.List;

public interface SearchMealView {
    public void showSearchByCategory(List<CategorySearchModel> models);
    public void showSearchByArea(List<AreaModel> models);
    public void showSearchByIngredient(List<IngredientModel> models);

    public void showErrorMsg(String err);
}
