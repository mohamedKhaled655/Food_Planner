package com.example.mealsapp.favourite_fragment.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mealsapp.R;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.favourite_fragment.presenter.FavMealPresenter;
import com.example.mealsapp.favourite_fragment.presenter.FavMealPresenterImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment implements OnRemoveFavClickListener, FavoriteMealView {

    private static final String TAG = "FavouritesFragment";
    private FavouriteAdapter adapter;
    RecyclerView recyclerView;
    ConstraintLayout constraintLayoutForLogin;
    private ImageButton btnBackupFavorites;
    private ImageButton btnRestoreFavorites;
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
        btnBackupFavorites = view.findViewById(R.id.btnBackupFavorites);
        btnRestoreFavorites = view.findViewById(R.id.btnRestoreFavorites);
        constraintLayoutForLogin=view.findViewById(R.id.cons_login_content);
        recyclerView=view.findViewById(R.id.recycler_view_fav);
        setUpPresenter();
        String userId = favMealPresenter.getUserId();
        if (userId == null || userId.isEmpty()) {
            recyclerView.setVisibility(GONE);
            constraintLayoutForLogin.setVisibility(VISIBLE);
            Button btnLogin=view.findViewById(R.id.btn_needLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_favouritesFragment_to_LoginFragment,null,
                            new androidx.navigation.NavOptions.Builder()
                                    .setPopUpTo(R.id.favouritesFragment, true)
                                    .build());
                }
            });
            Toast.makeText(getContext(), "Needed To Login to show fav", Toast.LENGTH_SHORT).show();
        } else {
            constraintLayoutForLogin.setVisibility(GONE);
            adapter=new FavouriteAdapter(getContext(),new ArrayList<>(),this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            favMealPresenter.getFavMeals();
            Toast.makeText(getContext(), "token :" + userId, Toast.LENGTH_SHORT).show();

            btnBackupFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favMealPresenter.syncFavoritesToCloud();
                }
            });

            btnRestoreFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favMealPresenter.restoreFavoritesFromCloud();
                }
            });
        }



    }

    private void setUpPresenter() {
        MealRepository mealRepository= MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));
        favMealPresenter=new FavMealPresenterImpl(this,mealRepository);
    }

    @Override
    public void showFavData(List<MealEntity> mealEntities) {
        adapter.setProducts(mealEntities);
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
    public void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveFromFavorite(MealEntity meal) {

        favMealPresenter.removeFromFav(meal);
        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserId() {
        return favMealPresenter.getUserId();
    }
}