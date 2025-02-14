package com.example.mealsapp.home_fragment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private final Context context;
    private List<MealModel> mealModelList;
    private final OnAddFavClickListener listener;

    public MealAdapter(Context context, List<MealModel> mealModelList, OnAddFavClickListener onFavClickLisenter) {
        this.context = context;
        this.mealModelList = mealModelList;
        this.listener = onFavClickLisenter;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealModel mealModel = mealModelList.get(position);
        holder.txtMealTitle.setText(mealModel.getStrMeal());
        holder.txtMealId.setText(mealModel.getIdMeal());

        String imgUrl = mealModel.getStrMealThumb();
        Glide.with(context).load(imgUrl).circleCrop().into(holder.mealImage);

        holder.mealImage.setOnClickListener(view -> {
            Toast.makeText(context, "Navigating to details: " + mealModel.getStrMeal(), Toast.LENGTH_SHORT).show();
            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealModel);
            Navigation.findNavController(view).navigate(action);
        });

        holder.btnImgFav.setOnClickListener(view -> {
            boolean isFav = true;
            MealEntity mealEntity = new MealEntity(
                    mealModel.getIdMeal(),
                    mealModel.getStrMeal(),
                    mealModel.getStrCategory(),
                    mealModel.getStrArea(),
                    mealModel.getStrInstructions(),
                    mealModel.getStrMealThumb(),
                    mealModel.getStrYoutube(),
                    isFav
            );

            if (listener != null) {
                listener.onAddToFavorite(mealEntity);
                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMeals(List<MealModel> mealModels) {
        this.mealModelList = mealModels;
        notifyDataSetChanged();
    }

    public void updateData(List<MealModel> mealModels) {
        this.mealModelList = mealModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mealModelList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealTitle, txtMealId;
        ImageView mealImage;
        ImageButton btnImgFav;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.txt_title);
            txtMealId = itemView.findViewById(R.id.price);
            mealImage = itemView.findViewById(R.id.img_item);
            btnImgFav = itemView.findViewById(R.id.img_fav);
        }
    }
}
