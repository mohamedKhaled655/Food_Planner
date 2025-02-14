package com.example.mealsapp.data.network;

public interface MealRemoteDataSource {
    void makeNetworkCall(NetworkCallback networkCallback);
    void makeNetworkCallForCategory(NetworkCallBackForCategory networkCallBackForCategory);
}
