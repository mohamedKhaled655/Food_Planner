package com.example.mealsapp.home_fragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mealsapp.R;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.Models.UserModel;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.home_fragment.presenter.HomePresenter;
import com.example.mealsapp.home_fragment.presenter.HomePresenterImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class HomeFragment extends Fragment implements HomeMealView, OnAddFavClickListener {
    private static final String TAG = "HomeFragment";
    private RecyclerView categoryRV, mealRV;

    private CategoryAdapter categoryAdapter;
    private MealAdapter mealAdapter;

    private HomePresenter homePresenter;

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

        setUpPresenter();
        homePresenter.getMeals();
        homePresenter.getCategories();

        /*UserModel user= HomeFragmentArgs.fromBundle(getArguments()).getUserModel();
        if (user != null) {
            Toast.makeText(getContext(), "User: " + user.getName(), Toast.LENGTH_SHORT).show();
        }*/


    }
    private void setUpPresenter() {

        MealRepository mealRepository= MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(requireContext()));
        homePresenter=new HomePresenterImpl(this,mealRepository);
    }
    private void initRecyclerViews(View view) {
        categoryRV = view.findViewById(R.id.re_category);
        categoryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        categoryRV.setAdapter(categoryAdapter);

        mealRV = view.findViewById(R.id.rv_gride_items);
        mealRV.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mealAdapter = new MealAdapter(requireContext(), new ArrayList<>(),this);
        mealRV.setAdapter(mealAdapter);
    }



    @Override
    public void showAllMealsData(List<MealModel> models) {
       mealAdapter.setMeals(models);
       mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCategoryData(List<CategoryModel> categoryModels) {
        Log.d("HomeFragment", "Categories received: " + categoryModels.size());
        categoryAdapter.setCategories(categoryModels);

    }

    @Override
    public void showErrorMsg(String err) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage(err).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onAddToFavorite(MealEntity mealEntity) {
        homePresenter.addMealToFav(mealEntity);
    }
}