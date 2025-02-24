package com.example.mealsapp.home_fragment.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


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
    private ConnectivityManager.NetworkCallback networkCallback;

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
        setupNetworkCallback();
        loadData();
    }

    private void initViews(View view) {
        contentLayout = view.findViewById(R.id.content_layout);
        animationView = view.findViewById(R.id.img_no_internet);
        loaderView = view.findViewById(R.id.home_loader);
        loaderView.setVisibility(View.GONE);
        txtConnection = view.findViewById(R.id.txt_no_internet);
        noInternetLayout = view.findViewById(R.id.no_internet_view);
        btnRetry = view.findViewById(R.id.btn_retry);
        txtConnection.setText("Connection Lost");
        btnRetry.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                loaderView.setVisibility(View.VISIBLE);
                hideNoInternetLayout();
                Toast.makeText(getContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
                loadData();
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

     public void setupNetworkCallback() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                if (getActivity() == null) return;
                getActivity().runOnUiThread(() -> {
                    hideNoInternetLayout();
                    loadData();
                });
            }

            @Override
            public void onLost(Network network) {
                if (getActivity() == null) return;
                Log.d(TAG, "Network lost, calling showNoInternetLayout");
                getActivity().runOnUiThread(() -> {
                    showNoInternetLayout();
                });
            }
        };

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void loadData() {
        if (isNetworkAvailable()) {
            loaderView.setVisibility(View.VISIBLE);
            homePresenter.getMeals();
            homePresenter.getCategories();
        } else {
            showNoInternetLayout();
        }
    }

    private void showNoInternetLayout() {
        if (contentLayout != null && noInternetLayout != null) {
            Log.d(TAG, "Showing no internet layout");
            contentLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    private void hideNoInternetLayout() {
        if (contentLayout != null && noInternetLayout != null) {
            contentLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAllMealsData(List<MealModel> models) {
        if (!isAdded()) return;
        loaderView.setVisibility(View.GONE);
        mealAdapter.setMeals(models);
        mealOfDayAdapter.setMeals(models);
        mealAdapter.notifyDataSetChanged();
        mealOfDayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCategoryData(List<CategoryModel> categoryModels) {
        if (!isAdded()) return;
        Log.d(TAG, "Categories received: " + categoryModels.size());
        categoryAdapter.setCategories(categoryModels);
    }

    @Override
    public void showErrorMsg(String err) {
        if (!isAdded()) return;
        loaderView.setVisibility(View.GONE);
        if (!isNetworkAvailable()) {
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
    public void onStart() {
        super.onStart();
        setupNetworkCallback();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (networkCallback != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
}