package com.example.mealsapp.data.network;

public interface MealRemoteDataSource {
    void makeNetworkCall(NetworkCallback networkCallback);
    void makeNetworkCallForCategory(NetworkCallBackForCategory networkCallBackForCategory);
    void makeNetworkCallForArea(NetworkCallNBackForArea networkCallNBackForArea);
    void makeNetworkCallForCategorySearch(NetworkCallNBackForSearchCategory networkCallNBackForSearchCategory);
    void makeNetworkCallForIngredientSearch(NetworkCallNBackForIngredient networkCallNBackForIngredient);
}
