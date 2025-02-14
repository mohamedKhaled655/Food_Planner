package com.example.mealsapp.favourite_fragment.view;

import com.example.mealsapp.data.local.MealEntity;

import java.util.List;

public interface FavoriteMealView {
    public void showFavData(List<MealEntity> mealEntities);
    public void showErrorMsg(String err);
}
