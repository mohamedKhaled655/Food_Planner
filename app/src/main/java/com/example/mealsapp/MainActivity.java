package com.example.mealsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";
    private RecyclerView categoryRV, mealRV;
    private CategoryAdapter categoryAdapter;
    private MealAdapter mealAdapter;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private List<MealModel> meals = new ArrayList<>();
    private MealService mealService;

    NavController navController;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: Activity started");


        bottomNavigationView=findViewById(R.id.bottomNav);
        navigationView=findViewById(R.id.nav_drawer);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        NavHostFragment navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        navController=navHostFragment.getNavController();
        //to show app bar
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
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

                if(navDestination.getId()==R.id.homeFragment||navDestination.getId()==R.id.searchFragment||navDestination.getId()==R.id.favouritesFragment||navDestination.getId()==R.id.profileFragment|| navDestination.getId()==R.id.plannedMealsFragment){
                    //getSupportActionBar().show();
                    getSupportActionBar().hide();
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                /*else if (navDestination.getId()==R.id.homeFragment) {
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }*/
                else{
                    bottomNavigationView.setVisibility(View.GONE);
                    getSupportActionBar().hide();
                }

            }

        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp() || super.onSupportNavigateUp();
    }












}
