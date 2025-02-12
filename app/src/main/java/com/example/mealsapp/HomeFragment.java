package com.example.mealsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mealsapp.Models.CategoryModel;
import com.example.mealsapp.Models.CategoryResponse;
import com.example.mealsapp.Models.MealModel;
import com.example.mealsapp.Models.MealResponse;
import com.example.mealsapp.Models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView categoryRV, mealRV;
    private CategoryAdapter categoryAdapter;
    private MealAdapter mealAdapter;
    private List<CategoryModel> categoryModelList = new ArrayList<>();
    private List<MealModel> meals = new ArrayList<>();
    private MealService mealService;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerViews(view);
        mealService = MealClient.getInstance().create(MealService.class);

        //fetchCategories();
        fetchMeals();
        UserModel user=HomeFragmentArgs.fromBundle(getArguments()).getUserModel();
        Toast.makeText(getContext(), user.toString(), Toast.LENGTH_SHORT).show();

    }
    private void initRecyclerViews(View view) {
        /*categoryRV = findViewById(R.id.rv_category_item);
        categoryRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this, categoryModelList);
        categoryRV.setAdapter(categoryAdapter);*/

        mealRV = view.findViewById(R.id.rv_gride_items);
        mealRV.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mealAdapter = new MealAdapter(requireContext(), meals);
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
        Toast.makeText(requireContext(), "Error fetching data: " + response.code(), Toast.LENGTH_SHORT).show();
    }

    private void handleFailure(Throwable throwable) {
        Log.e(TAG, "Request failed", throwable);
        Toast.makeText(requireContext(), "Failed to connect. Check your internet!", Toast.LENGTH_SHORT).show();
    }
}