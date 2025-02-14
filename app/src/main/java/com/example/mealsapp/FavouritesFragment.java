package com.example.mealsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.favourite_fragment.presenter.FavMealPresenter;
import com.example.mealsapp.favourite_fragment.presenter.FavMealPresenterImpl;
import com.example.mealsapp.favourite_fragment.view.FavoriteMealView;
import com.example.mealsapp.favourite_fragment.view.FavouriteAdapter;
import com.example.mealsapp.favourite_fragment.view.OnRemoveFavClickListener;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment implements OnRemoveFavClickListener, FavoriteMealView {

    private static final String TAG = "FavouritesFragment";
    private FavouriteAdapter adapter;
    RecyclerView recyclerView;
    FavMealPresenter favMealPresenter;
    public FavouritesFragment() {
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
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycler_view_fav);
        adapter=new FavouriteAdapter(getContext(),new ArrayList<>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        setUpPresenter();
        favMealPresenter.getFavMealss().observe(getViewLifecycleOwner(), new Observer<List<MealEntity>>() {
            @Override
            public void onChanged(List<MealEntity> mealEntities) {
                if (mealEntities != null) {
                    adapter.setProducts(mealEntities);
                }
            }
        });
    }

    private void setUpPresenter() {
        MealRepository mealRepository= MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));
        favMealPresenter=new FavMealPresenterImpl(this,mealRepository);
    }

    @Override
    public void showFavData(List<MealEntity> mealEntities) {
        favMealPresenter.getFavMealss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String err) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage(err).setTitle("An Error Occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onRemoveFromFavorite(MealEntity meal) {

        favMealPresenter.removeFromFav(meal);
        List<MealEntity> updatedList = new ArrayList<>();
        updatedList.remove(meal);
        adapter.setProducts(updatedList);
    }
}