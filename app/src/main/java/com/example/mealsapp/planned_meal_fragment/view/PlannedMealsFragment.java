package com.example.mealsapp.planned_meal_fragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsapp.R;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.local.PlannedMealEntity;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.planned_meal_fragment.presenter.PlannedMealPresenter;
import com.example.mealsapp.planned_meal_fragment.presenter.PlannedMealPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class PlannedMealsFragment extends Fragment implements OnRemovePlannedClickListener, PlannedMealView {

    private static final String TAG = "PlannedMealsFragment";
    private CalendarView calendar;
    private TextView dateView;
    private PlannedMealAdapter adapter;
    private RecyclerView recyclerView;
    private PlannedMealPresenter plannedMealPresenter;
    private String date;

    public PlannedMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planned_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = view.findViewById(R.id.calendar);
        dateView = view.findViewById(R.id.date_view);
        recyclerView = view.findViewById(R.id.rv_planned_meal);


        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());


        date = sdf.format(new Date(calendar.getDate()));
        dateView.setText(date);

        calendar.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            date = dayOfMonth + "/" + (month + 1) + "/" + year;
            dateView.setText(date);
            showPlannedMealsByDate(date);
        });

        adapter = new PlannedMealAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setUpPresenter();
        showPlannedMealsByDate(date);

    }

    private void setUpPresenter() {
        MealRepository mealRepository = MealRepositoryImpl.getInstance(
                MealRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext())
        );
        plannedMealPresenter = new PlannedMealPresenterImpl(this, mealRepository);
    }

    @Override
    public void onRemoveFromPlannedMeal(PlannedMealEntity plannedMealEntity) {
        plannedMealPresenter.removeFromFav(plannedMealEntity);
    }

    @Override
    public void showAllPlannedMeals(List<PlannedMealEntity> plannedMealEntities) {
        adapter.setProducts(plannedMealEntities);
    }

    @Override
    public void showPlannedMealsByDate(String date) {
        plannedMealPresenter.getPlannedMealsByData(date);
    }

    @Override
    public void showErrorMsg(String err) {
        if (getContext() != null) {
            Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (plannedMealPresenter != null) {
           // plannedMealPresenter.detachView();
        }
    }
}
