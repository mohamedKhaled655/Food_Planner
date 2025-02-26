package com.example.mealsapp.meals_fragment.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;

import com.example.mealsapp.home_fragment.view.HomeFragmentDirections;
import com.example.mealsapp.home_fragment.view.OnAddFavClickListener;
import com.example.mealsapp.search_fragment.view.SearchFragmentDirections;

import java.util.List;

public class CustomMealAdapter extends  RecyclerView.Adapter<CustomMealAdapter.CustomMealViewHolder> {
    private final Context context;
    private List<MealModel> mealModelList;
    private final OnAddFavClickListener listener;


    public CustomMealAdapter(Context context, List<MealModel> mealModelList, OnAddFavClickListener onFavClickLisenter) {
        this.context = context;
        this.mealModelList = mealModelList;
        this.listener = onFavClickLisenter;
    }

    @NonNull

    public void setMeals(List<MealModel> mealModels) {
        this.mealModelList = mealModels;
        notifyDataSetChanged();
    }

    public void updateData(List<MealModel> mealModels) {
        this.mealModelList = mealModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new CustomMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomMealViewHolder holder, int position) {
        MealModel mealModel = mealModelList.get(position);
        holder.txtMealTitle.setText(mealModel.getStrMeal());
        // holder.txtMealId.setText(mealModel.getIdMeal());

        String imgUrl = mealModel.getStrMealThumb();
        Glide.with(context).load(imgUrl).placeholder(R.drawable.loadimg)
                .error(R.drawable.meal_back).into(holder.mealImage);

        updateFavButton(holder.btnImgFav, mealModel.isFavorite());
        holder.mealImage.setOnClickListener(view -> {
            Toast.makeText(context, "Navigating to details: " + mealModel.getStrMeal(), Toast.LENGTH_SHORT).show();

                MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment action=MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment(mealModel.getIdMeal());
                Navigation.findNavController(view).navigate(action);

        });
        MealEntity mealEntity = new MealEntity(
                mealModel.getIdMeal(),
                listener.userId(),
                mealModel.getStrMeal(),
                mealModel.getStrCategory(),
                mealModel.getStrArea(),
                mealModel.getStrInstructions(),
                mealModel.getStrMealThumb(),
                mealModel.getStrYoutube(),
                true
        );

        holder.btnImgFav.setOnClickListener(view -> {
            if(mealModel.isFavorite()){
                listener.onRemoveFromFavorite(mealEntity);
                mealModel.setFavorite(false);
                Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }else{



                if (listener != null) {
                    listener.onAddToFavorite(mealEntity);
                    mealModel.setFavorite(true);
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
            updateFavButton(holder.btnImgFav, mealModel.isFavorite());

        });
    }

    @Override
    public int getItemCount() {
        return mealModelList.size();
    }

    public static class CustomMealViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealTitle, txtMealId;
        ImageView mealImage;
        ImageButton btnImgFav;

        public CustomMealViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.txt_meal_name_card);
            //txtMealId = itemView.findViewById(R.id.price);
            mealImage = itemView.findViewById(R.id.img_card);
            btnImgFav = itemView.findViewById(R.id.favoriteButtonCard);
        }
    }

    private void updateFavButton(ImageButton btnImgFav, boolean isFavorite) {
        if (isFavorite) {
            btnImgFav.setBackgroundResource(R.drawable.circle);

        } else {
            btnImgFav.setBackgroundResource(R.drawable.circle_background);
        }
    }
}

