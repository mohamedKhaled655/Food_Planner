package com.example.mealsapp.home_fragment.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealsapp.R;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.Models.UserModel;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.local.PlannedMealEntity;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.home_fragment.presenter.HomePresenter;
import com.example.mealsapp.home_fragment.presenter.HomePresenterImpl;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;


import java.util.ArrayList;
import java.util.List;




public class HomeFragment extends Fragment implements HomeMealView, OnAddFavClickListener {
    private static final String TAG = "HomeFragment";

    private RecyclerView categoryRV, mealRV;
    private CategoryAdapter categoryAdapter;
    private MealOfDayAdapter mealOfDayAdapter;
    private CarouselRecyclerview carouselRecyclerview;
    private MealAdapter mealAdapter;
    private HomePresenter homePresenter;
    private View contentLayout;
    private View noInternetLayout;
    private Button btnRetry;
    private LottieAnimationView animationView,loaderView;
    private TextView txtConnection;
    private TextView txt_insp,txt_cat,txt_meal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);
        setUpPresenter();
        homePresenter.startNetworkMonitoring();
        homePresenter.loadData();
    }

    private void initViews(View view) {
        txt_insp=view.findViewById(R.id.txt_insp_name);
        txt_cat=view.findViewById(R.id.txt_cat_name);
        txt_meal=view.findViewById(R.id.txt_meal_name);
        contentLayout = view.findViewById(R.id.content_layout);
        animationView = view.findViewById(R.id.img_no_internet);
        loaderView = view.findViewById(R.id.home_loader);
        loaderView.setVisibility(GONE);
        txtConnection = view.findViewById(R.id.txt_no_internet);
        noInternetLayout = view.findViewById(R.id.no_internet_view);
        btnRetry = view.findViewById(R.id.btn_retry);
        txtConnection.setText("Connection Lost");
        btnRetry.setOnClickListener(v -> {
            if (homePresenter.isNetworkAvailable()) {
                loaderView.setVisibility(VISIBLE);
                hideNoInternetLayout();
                Toast.makeText(getContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
                homePresenter.loadData();
            }
        });

        initRecyclerViews(view);
    }

    private void initRecyclerViews(View view) {
        carouselRecyclerview=view.findViewById(R.id.randomRecycler);

        mealOfDayAdapter=new MealOfDayAdapter(requireContext(), new ArrayList<>(), this);
        carouselRecyclerview.setAdapter(mealOfDayAdapter);
        categoryRV = view.findViewById(R.id.re_category);
        categoryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        categoryRV.setAdapter(categoryAdapter);

        mealRV = view.findViewById(R.id.rv_gride_items);
        mealRV.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mealAdapter = new MealAdapter(requireContext(), new ArrayList<>(), this);
        mealRV.setAdapter(mealAdapter);
    }

    private void setUpPresenter() {
        MealRepository mealRepository = MealRepositoryImpl.getInstance(
                MealRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(requireContext())
        );
        homePresenter = new HomePresenterImpl(this, mealRepository);
    }





    public void showNoInternetLayout() {
        if (contentLayout != null && noInternetLayout != null) {
            Log.d(TAG, "Showing no internet layout");
            contentLayout.setVisibility(GONE);
            noInternetLayout.setVisibility(VISIBLE);
        }
    }

    public void hideNoInternetLayout() {
        if (contentLayout != null && noInternetLayout != null) {
            contentLayout.setVisibility(VISIBLE);
            noInternetLayout.setVisibility(GONE);
        }
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }

    @Override
    public void showAllMealsData(List<MealModel> models) {

        hideLoading();
        mealAdapter.setMeals(models);
        mealOfDayAdapter.setMeals(models);
        mealAdapter.notifyDataSetChanged();
        mealOfDayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCategoryData(List<CategoryModel> categoryModels) {

        Log.d(TAG, "Categories received: " + categoryModels.size());
        categoryAdapter.setCategories(categoryModels);
    }

    @Override
    public void showErrorMsg(String err) {

        loaderView.setVisibility(GONE);
        if (!homePresenter.isNetworkAvailable()) {
            showNoInternetLayout();
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("An Error Occurred")
                .setMessage(err)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void showLoading() {
        loaderView.setVisibility(VISIBLE);
        txt_insp.setVisibility(GONE);
        txt_cat.setVisibility(GONE);
        txt_meal.setVisibility(GONE);
    }

    @Override
    public void hideLoading() {
        loaderView.setVisibility(GONE);
        txt_insp.setVisibility(VISIBLE);
        txt_cat.setVisibility(VISIBLE);
        txt_meal.setVisibility(VISIBLE);
    }

    @Override
    public void onAddToFavorite(MealEntity mealEntity) {
        homePresenter.addMealToFav(mealEntity);
    }

    @Override
    public void onRemoveFromFavorite(MealEntity mealEntity) {
        homePresenter.removeMealToFav(mealEntity);
    }

    @Override
    public void onAddToPlannedMeal(PlannedMealEntity plannedMealEntity) {
        homePresenter.addToPlannedMeal(plannedMealEntity);
        Toast.makeText(getContext(),
                "plannedMealEntity" + plannedMealEntity.getPlannedDate(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public String userId() {
        return homePresenter.getUserId();
    }

    @Override
    public void onStart() {
        super.onStart();
        homePresenter.startNetworkMonitoring();
    }

    @Override
    public void onStop() {
        super.onStop();
        homePresenter.stopNetworkMonitoring();
    }
}