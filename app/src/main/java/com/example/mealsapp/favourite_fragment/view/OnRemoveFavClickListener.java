package com.example.mealsapp.favourite_fragment.view;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.home_fragment.view.OnFavClickLisenter;

public interface OnRemoveFavClickListener extends OnFavClickLisenter {
    void onRemoveFromFavorite(MealEntity meal);
    String getUserId();
}
