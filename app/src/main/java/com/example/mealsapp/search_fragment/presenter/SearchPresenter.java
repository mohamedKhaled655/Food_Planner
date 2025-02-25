package com.example.mealsapp.search_fragment.presenter;

public interface SearchPresenter {

    public void getSearchedArea();
    public void getSearchedIngredient();
    public void getSearchedCategories();

    ////////
    void filterCategoriesByQuery(String query);
    void filterAreasByQuery(String query);
    void filterIngredientsByQuery(String query);

    ////
    void onDestroy();
}
