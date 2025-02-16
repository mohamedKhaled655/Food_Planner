package com.example.mealsapp.data.network;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategorySearchModel;

import java.util.List;

public interface NetworkCallNBackForSearchCategory {
    public void onSuccessForCategorySearchResult(List<CategorySearchModel> categorySearchModels);
    public void onFailureForCategorySearchResult(String errMessage);
}
