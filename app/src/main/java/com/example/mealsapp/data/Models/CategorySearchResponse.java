package com.example.mealsapp.data.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategorySearchResponse {
    @SerializedName("meals")
    private List<CategorySearchModel> categories;

    public List<CategorySearchModel> getCategorySearchModels() {
        return categories;
    }
}
