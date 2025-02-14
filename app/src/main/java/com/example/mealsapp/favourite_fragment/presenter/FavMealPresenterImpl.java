package com.example.mealsapp.favourite_fragment.presenter;

import androidx.lifecycle.LiveData;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.favourite_fragment.view.FavoriteMealView;

import java.util.List;

public class FavMealPresenterImpl implements FavMealPresenter{
    private FavoriteMealView favoriteMealView;
    private MealRepository repository;

    public FavMealPresenterImpl(FavoriteMealView favoriteMealView, MealRepository repository) {
        this.favoriteMealView = favoriteMealView;
        this.repository = repository;
    }

    @Override
    public LiveData<List<MealEntity>> getFavMealss() {
        return repository.getStoredFavMeals();
    }

    @Override
    public void removeFromFav(MealEntity meal) {
        repository.removeMealToFav(meal);
    }

    @Override
    public void addMealFromFav(MealEntity meal) {
      repository.addMealToFav(meal);
    }
}
