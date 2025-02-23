package com.example.mealsapp.planned_meal_fragment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;

import com.example.mealsapp.data.local.PlannedMealEntity;


import java.util.List;

public class PlannedMealAdapter extends RecyclerView.Adapter<PlannedMealAdapter.PlannedMealViewHolder>{
    private List<PlannedMealEntity> plannedMealEntities ;
    private final OnRemovePlannedClickListener listener;

    private Context context;

    public PlannedMealAdapter(Context context, List<PlannedMealEntity> plannedMealEntities, OnRemovePlannedClickListener listener) {
        this.context = context;
        this.plannedMealEntities = plannedMealEntities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlannedMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fav, parent, false);
        return new PlannedMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannedMealViewHolder holder, int position) {
        PlannedMealEntity mealEntity=plannedMealEntities.get(position);
        holder.planTitle.setText(mealEntity.getMealName());
        holder.planCatView.setText(mealEntity.getCategory());
        final String imgUrl=mealEntity.getThumbUrl();
        Glide.with(context).load(imgUrl).circleCrop().into(holder.planImage);
        holder.planActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRemoveFromPlannedMeal(mealEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plannedMealEntities.size();
    }
    public void setProducts(List<PlannedMealEntity>plannedMealEntities) {
        this.plannedMealEntities=plannedMealEntities;
        notifyDataSetChanged();
    }

    public static class PlannedMealViewHolder extends RecyclerView.ViewHolder{
        private final TextView planTitle;
        private final TextView planCatView;
        private final ImageView planImage;
        private final ImageButton planActionButton;
        public PlannedMealViewHolder(@NonNull View itemView) {
            super(itemView);

            planTitle=itemView.findViewById(R.id.fav_title);
            planCatView=itemView.findViewById(R.id.fav_category);
            planImage=itemView.findViewById(R.id.fav_thumbnail);
            planActionButton=itemView.findViewById(R.id.fav_button);

        }
    }
}
