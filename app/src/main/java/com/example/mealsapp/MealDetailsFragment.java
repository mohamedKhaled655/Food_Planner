package com.example.mealsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealsapp.data.Models.MealModel;


public class MealDetailsFragment extends Fragment {

    ImageView imageView;
    TextView title,desc;
    ImageButton img_arrowBack;
    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.item_info_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView=view.findViewById(R.id.img_info_image);
        title=view.findViewById(R.id.txt_tilte_detail);
        desc=view.findViewById(R.id.txt_description_details);
        img_arrowBack=view.findViewById(R.id.btn_arr_back);
        img_arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();
            }
        });

        MealModel mealInfo=MealDetailsFragmentArgs.fromBundle(getArguments()).getMealInfo();
        Toast.makeText(getContext(), mealInfo.getIdMeal(), Toast.LENGTH_SHORT).show();
        title.setText(mealInfo.getStrMeal());
        desc.setText(mealInfo.getStrInstructions());
        Glide.with(this)
                .load(mealInfo.getStrMealThumb())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);

    }
}