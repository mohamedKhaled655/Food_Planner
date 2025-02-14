package com.example.mealsapp.data.Models;

import java.util.ArrayList;
import java.util.List;

public class MealResponse {
    private List<MealModel> meals;

    public List<MealModel> getMeals() {
        return meals != null ? meals : new ArrayList<>();
    }
}
