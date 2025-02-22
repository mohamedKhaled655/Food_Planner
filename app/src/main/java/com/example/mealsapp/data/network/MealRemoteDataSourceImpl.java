package com.example.mealsapp.data.network;

import android.util.Log;

import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.AreaResponse;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategoryResponse;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.CategorySearchResponse;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.Models.IngredientResponse;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.Models.MealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {
    private static final String TAG = "MealRemoteDataSourceImp";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit;
    private static MealRemoteDataSourceImpl instance;
    private static MealService mealService ;

    private MealRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public static synchronized MealRemoteDataSourceImpl getInstance( ) {

        if (instance == null) {
            instance = new MealRemoteDataSourceImpl();
        }
        return instance;
    }

    @Override
    public Single<List<MealModel>> getMeals() {
        return mealService.getMeals().map(mealResponse ->mealResponse.getMeals());
    }

    @Override
    public Single<List<CategoryModel>> getCategories() {
        return mealService.getCategories().map(categoryResponse -> categoryResponse.getCategoryList());
    }

    @Override
    public Single<List<AreaModel>> getCountries() {
        return mealService.getAreasForSearch().map(areaResponse -> areaResponse.getAreaModels());
    }

    @Override
    public Single<List<CategorySearchModel>> getCategoryForSearch() {
        return mealService.getCategoryForSearch().map(categorySearchResponse -> categorySearchResponse.getCategorySearchModels());
    }

    @Override
    public Single<List<IngredientModel>> getIngredientForSearch() {
        return mealService.getIngredientsForSearch().map(ingredientResponse -> ingredientResponse.getIngredientModels());
    }

    @Override
    public Single<List<MealDetailsModel>> getMealDetails(String mealId) {
        return mealService.getMealDetails(mealId).map(mealDetailsResponse -> mealDetailsResponse.getMeals());
    }

    @Override
    public Single<List<MealModel>> getMealsByCategory(String category) {
        return mealService.getMealsByCategory(category).map(mealResponse -> mealResponse.getMeals());
    }

    @Override
    public Single<List<MealModel>> getMealsByArea(String area) {
        return mealService.getMealsByArea(area).map(mealResponse -> mealResponse.getMeals());
    }

    @Override
    public Single<List<MealModel>> getMealsByIngredient(String ingredient) {
        return mealService.getMealsByIngredient(ingredient).map(mealResponse -> mealResponse.getMeals());
    }

/*
    @Override
    public void makeNetworkCall(NetworkCallback networkCallback) {
        Call<MealResponse>call= mealService.getMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()){
                    networkCallback.onSuccessResult(response.body().getMeals());
                    Log.i(TAG, "onResponse: "+response.body());
                }else {
                    networkCallback.onFailureResult(response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: callback");
                networkCallback.onFailureResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }
    @Override
    public void makeNetworkCallForCategory(NetworkCallBackForCategory networkCallback) {
        Call<CategoryResponse>call= mealService.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()){
                    networkCallback.onSuccessResultForCategory(response.body().getCategoryList());
                    Log.i(TAG, "onResponse: "+response.body().getCategoryList());
                }else {
                    networkCallback.onFailureResultForCategory(response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: callback");
                networkCallback.onFailureResultForCategory(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallForArea(NetworkCallNBackForArea networkCallNBackForArea) {
        Call<AreaResponse>call= mealService.getAreasForSearch();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful())
                {
                    networkCallNBackForArea.onSuccessForAreaResult(response.body().getAreaModels());
                    Log.i(TAG, "onResponse: "+response.body().getAreaModels());
                }else{
                    networkCallNBackForArea.onFailureForAreaResult(response.message());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: call/back");
                networkCallNBackForArea.onFailureForAreaResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallForCategorySearch(NetworkCallNBackForSearchCategory networkCallNBackForSearchCategory) {
        Call<CategorySearchResponse>call= mealService.getCategoryForSearch();
        call.enqueue(new Callback<CategorySearchResponse>() {
            @Override
            public void onResponse(Call<CategorySearchResponse> call, Response<CategorySearchResponse> response) {
                if(response.isSuccessful()){
                    networkCallNBackForSearchCategory.onSuccessForCategorySearchResult(response.body().getCategorySearchModels());
                    Log.i(TAG, "onResponse:makeNetworkCallForCategorySearch :  "+response.body().getCategorySearchModels());
                }else{
                    Log.i(TAG, "onResponse:makeNetworkCallForCategorySearch :"+response.message());
                    networkCallNBackForSearchCategory.onFailureForCategorySearchResult(response.message());
                }
            }

            @Override
            public void onFailure(Call<CategorySearchResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: call category search");
                networkCallNBackForSearchCategory.onFailureForCategorySearchResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallForIngredientSearch(NetworkCallNBackForIngredient networkCallNBackForIngredient) {
        Call<IngredientResponse>call= mealService.getIngredientsForSearch();
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if(response.isSuccessful()){
                    networkCallNBackForIngredient.onSuccessForIngredientResult(response.body().getIngredientModels());
                    Log.i(TAG, "onResponse: "+response.body().getIngredientModels());
                }else{
                    networkCallNBackForIngredient.onFailureForIngredientResult(response.message());
                    Log.i(TAG, "onResponse error: ");
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: networkCallNBackForIngredient");
                networkCallNBackForIngredient.onFailureForIngredientResult(throwable.getMessage());
            }
        });
    }

    */
}
