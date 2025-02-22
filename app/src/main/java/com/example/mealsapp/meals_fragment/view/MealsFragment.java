package com.example.mealsapp.meals_fragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.mealsapp.R;

import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.local.PlannedMealEntity;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.home_fragment.presenter.HomePresenterImpl;
import com.example.mealsapp.home_fragment.view.MealAdapter;
import com.example.mealsapp.home_fragment.view.OnAddFavClickListener;
import com.example.mealsapp.meals_fragment.presenter.MealPresenter;
import com.example.mealsapp.meals_fragment.presenter.MealPresenterImpl;

import java.util.ArrayList;
import java.util.List;


public class MealsFragment extends Fragment implements MealsView , OnAddFavClickListener {

    private static final String TAG = "MealsFragment";
    TextView name;
    ImageButton arrowBtn;
    RecyclerView recyclerView;
    private MealPresenter mealPresenter;
    CustomMealAdapter mealAdapter;

    public MealsFragment() {
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
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=view.findViewById(R.id.txt_mealName_appbar);
        arrowBtn=view.findViewById(R.id.btn_arr_back_meal);
        String mealName = MealsFragmentArgs.fromBundle(getArguments()).getStrName();
        String filterType = MealsFragmentArgs.fromBundle(getArguments()).getFilterType();
        name.setText(mealName);
        arrowBtn.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view1);
            navController.navigateUp();
        });

        recyclerView = view.findViewById(R.id.rv_card_meal);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        mealAdapter = new CustomMealAdapter(requireContext(), new ArrayList<>(),this);
        recyclerView.setAdapter(mealAdapter);
        setUpPresenter();
        switch (filterType) {
            case "category":
                mealPresenter.getMealsByCat(mealName);
                break;
            case "ingredient":
                mealPresenter.getMealsByIngr(mealName);
                break;
            case "area":
                mealPresenter.getMealsByArea(mealName);
                break;
            default:
                mealPresenter.getMealsByCat(mealName);
                break;
        }

    }
    private void setUpPresenter() {

        MealRepository mealRepository= MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(requireContext()));
        mealPresenter=new MealPresenterImpl(this,mealRepository);
    }

    @Override
    public void onAddToFavorite(MealEntity mealEntity) {
        mealPresenter.addMealToFav(mealEntity);
    }

    @Override
    public void onRemoveFromFavorite(MealEntity mealEntity) {
        mealPresenter.removeMealToFav(mealEntity);
    }

    @Override
    public void onAddToPlannedMeal(PlannedMealEntity plannedMealEntity) {

    }

    @Override
    public void showAllMeals(List<MealModel> models) {
        mealAdapter.setMeals(models);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String err) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage(err).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}