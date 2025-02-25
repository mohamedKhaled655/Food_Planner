package com.example.mealsapp.planned_meal_fragment.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    ConstraintLayout constraintLayoutForLogin;

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
        constraintLayoutForLogin=view.findViewById(R.id.cons_login_content_for_plan);

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());


        date = sdf.format(new Date(calendar.getDate()));
        dateView.setText(date);

        calendar.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            date = dayOfMonth + "/" + (month + 1) + "/" + year;
            dateView.setText(date);
            showPlannedMealsByDate(date);
        });

        setUpPresenter();
        String userId = plannedMealPresenter.getUserId();
        if(userId == null || userId.isEmpty()){
            constraintLayoutForLogin.setVisibility(VISIBLE);
            calendar.setVisibility(GONE);
            recyclerView.setVisibility(GONE);
            dateView.setVisibility(GONE);
            Button btnLogin=view.findViewById(R.id.btn_needLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_plannedMealsFragment_to_LoginFragment,null,
                            new androidx.navigation.NavOptions.Builder()
                                    .setPopUpTo(R.id.plannedMealsFragment, true)
                                    .build());
                }
            });
            Toast.makeText(getContext(), "Needed To Login to show fav", Toast.LENGTH_SHORT).show();
        }else{
            constraintLayoutForLogin.setVisibility(GONE);
            calendar.setVisibility(VISIBLE);
            recyclerView.setVisibility(VISIBLE);
            dateView.setVisibility(VISIBLE);
            adapter = new PlannedMealAdapter(getContext(), new ArrayList<>(), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);


            showPlannedMealsByDate(date);
            Toast.makeText(getContext(), "token :" + userId, Toast.LENGTH_SHORT).show();

        }

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
    public String getUserId() {
        return plannedMealPresenter.getUserId();
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
