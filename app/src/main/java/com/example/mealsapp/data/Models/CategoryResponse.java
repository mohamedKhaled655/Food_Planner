package com.example.mealsapp.data.Models;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    private List<CategoryModel> categories;

    public List<CategoryModel> getCategoryList() {
        return categories != null ? categories : new ArrayList<>();
    }
}
