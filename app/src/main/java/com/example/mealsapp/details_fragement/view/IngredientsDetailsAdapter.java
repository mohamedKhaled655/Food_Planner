package com.example.mealsapp.details_fragement.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class IngredientsDetailsAdapter extends RecyclerView.Adapter<IngredientsDetailsAdapter.IngredientViewHolder> {
    private Context context;
    private List<MealDetailsModel.Ingredient> ingredients;

    public IngredientsDetailsAdapter(Context context, List<MealDetailsModel.Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        MealDetailsModel.Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
        int color = COLORS[position % COLORS.length];

        holder.cardView.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void updateIngredients(List<MealDetailsModel.Ingredient> newIngredients) {
        this.ingredients = newIngredients;
        notifyDataSetChanged();
    }
    private static final int[] COLORS = {
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#F8BBD0"),
            Color.parseColor("#E1BEE7"),
            Color.parseColor("#C5CAE9"),
            Color.parseColor("#B2DFDB")
    };
    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIngredientName;
        private TextView tvIngredientMeasure;
        private ImageView imgIngredient;
        MaterialCardView cardView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.txt_titleIngredient);
            tvIngredientMeasure = itemView.findViewById(R.id.txt_titleMeasure);
            imgIngredient = itemView.findViewById(R.id.img_ingredientItem);
            cardView = itemView.findViewById(R.id.card_ingredient_item);
        }

        public void bind(MealDetailsModel.Ingredient ingredient) {
            tvIngredientName.setText(ingredient.getName());
            tvIngredientMeasure.setText(ingredient.getMeasure());


            String ingredientImageUrl = "https://www.themealdb.com/images/ingredients/" +
                    ingredient.getName() + ".png";

            Glide.with(itemView.getContext())
                    .load(ingredientImageUrl)
                    .placeholder(R.drawable.loadimg)
                    .error(R.drawable.err_img)
                    .into(imgIngredient);
        }
    }
}