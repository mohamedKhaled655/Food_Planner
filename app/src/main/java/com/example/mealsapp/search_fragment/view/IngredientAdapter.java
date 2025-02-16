package com.example.mealsapp.search_fragment.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.IngredientModel;

import java.util.ArrayList;
import java.util.List;
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private Context context;
    private List<IngredientModel> ingredientModels;

    public IngredientAdapter(Context context, List<IngredientModel> ingredientModels) {
        this.context = context;
        this.ingredientModels = ingredientModels;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chip, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        IngredientModel ingredientModel = ingredientModels.get(position);
        holder.txtIngredientTitle.setText(ingredientModel.getStrIngredient());

        // Load ingredient image
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/" + ingredientModel.getStrIngredient() + ".png")
                .placeholder(R.drawable.lock)
                .error(R.drawable.lock)
                .circleCrop()
                .into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredientModels.size();
    }

    public void setIngredients(List<IngredientModel> ingredientModels) {
        if (this.ingredientModels == null) {
            this.ingredientModels = new ArrayList<>();
        }
        this.ingredientModels.clear();
        if (ingredientModels != null) {
            this.ingredientModels.addAll(ingredientModels);
        }
        notifyDataSetChanged();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientTitle;
        ImageView ingredientImage;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIngredientTitle = itemView.findViewById(R.id.txt_titleSearch);
            ingredientImage = itemView.findViewById(R.id.img_searchItem);
        }
    }
}
