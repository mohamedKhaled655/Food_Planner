package com.example.mealsapp.home_fragment.view;

import com.example.mealsapp.data.local.MealEntity;

public interface OnAddFavClickListener extends  OnFavClickLisenter{
    void onAddToFavorite(MealEntity mealEntity);
}
