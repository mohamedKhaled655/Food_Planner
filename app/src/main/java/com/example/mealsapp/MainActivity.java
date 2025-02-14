package com.example.mealsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategoryResponse;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.Models.MealResponse;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.network.MealService;
import com.example.mealsapp.home_fragment.view.CategoryAdapter;
import com.example.mealsapp.home_fragment.view.MealAdapter;
import com.example.mealsapp.home_fragment.view.OnAddFavClickListener;
import com.example.mealsapp.home_fragment.view.OnFavClickLisenter;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnAddFavClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerView categoryRV, mealRV;
    private CategoryAdapter categoryAdapter;
    private MealAdapter mealAdapter;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private List<MealModel> meals = new ArrayList<>();
    private MealService mealService;

    NavController navController;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: Activity started");

       /* setContentView(R.layout.home_page);
        Log.i(TAG, "onCreate: Activity started");

        initRecyclerViews();
        mealService = MealClient.getInstance().create(MealService.class);

        //fetchCategories();
        fetchMeals();
*/

        navigationView=findViewById(R.id.nav_drawer);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        NavHostFragment navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        navController=navHostFragment.getNavController();
        //to show app bar
        NavigationUI.setupWithNavController(navigationView, navController);
        /// to use Nav Controller in main activity
        //to use bottom nav bar
        /*
        NavHostFragment navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
         navController=navHostFragment.getNavController();
        //to show app bar
         NavigationUI.setupActionBarWithNavController(this, navController);
         */

       ///
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {

                if(navDestination.getId()==R.id.mealDetailsFragment){
                    //getSupportActionBar().show();
                    getSupportActionBar().hide();
                }
                /*else if (navDestination.getId()==R.id.homeFragment) {
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }*/
                else{
                    getSupportActionBar().hide();
                }

            }

        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }



    private void initRecyclerViews() {
        /*categoryRV = findViewById(R.id.rv_category_item);
        categoryRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, categoryModelList);
        categoryRV.setAdapter(categoryAdapter);*/

        mealRV = findViewById(R.id.rv_gride_items);
        mealRV.setLayoutManager(new GridLayoutManager(this, 2));
        mealAdapter = new MealAdapter(this, meals,this);
        mealRV.setAdapter(mealAdapter);
    }

    private void fetchCategories() {
        mealService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryModelList.clear();
                    categoryModelList.addAll(response.body().getCategoryList());
                    categoryAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Categories fetched successfully");
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                handleFailure(throwable);
            }
        });
    }

    private void fetchMeals() {
        mealService.getMeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    meals.clear();
                    meals.addAll(response.body().getMeals());
                    mealAdapter.notifyDataSetChanged();
                    Log.i(TAG, meals.size()+"");
                    Log.i(TAG, "Meals fetched successfully");
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                handleFailure(throwable);
            }
        });
    }

    private void handleError(Response<?> response) {
        try {
            Log.e(TAG, "Response error: " + response.code() + " - " + response.errorBody().string());
        } catch (IOException e) {
            Log.e(TAG, "Error parsing error body", e);
        }
        Toast.makeText(MainActivity.this, "Error fetching data: " + response.code(), Toast.LENGTH_SHORT).show();
    }

    private void handleFailure(Throwable throwable) {
        Log.e(TAG, "Request failed", throwable);
        Toast.makeText(MainActivity.this, "Failed to connect. Check your internet!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToFavorite(MealEntity mealEntity) {

    }
}
