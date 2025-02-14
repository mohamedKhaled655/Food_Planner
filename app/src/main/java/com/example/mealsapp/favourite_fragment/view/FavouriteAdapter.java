package com.example.mealsapp.favourite_fragment.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.home_fragment.view.OnFavClickLisenter;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private List<MealEntity> mealEntities ;
    private final OnFavClickLisenter listener;

    private Context context;

    public FavouriteAdapter(Context context, List<MealEntity> mealEntities, OnFavClickLisenter listener) {
        this.context = context;
        this.mealEntities = mealEntities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fav, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        MealEntity mealEntity=mealEntities.get(position);
        holder.favTitle.setText(mealEntity.getName());
        holder.favCatView.setText(mealEntity.getCategory());
        final String imgUrl=mealEntity.getThumbnail();
        Glide.with(context).load(imgUrl).circleCrop().into(holder.favImage);
        holder.favActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnRemoveFavClickListener)listener).onRemoveFromFavorite(mealEntity);
            }
        });
    }
    public void updateData(List<MealEntity>mealEntities){
        this.mealEntities=mealEntities;
        notifyDataSetChanged();
    }
    public void setProducts(List<MealEntity>mealEntities) {
        this.mealEntities=mealEntities;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mealEntities.size();
    }


    public static class FavouriteViewHolder extends RecyclerView.ViewHolder{
        private final TextView favTitle;
        private final TextView favCatView;
        private final ImageView favImage;
        private final Button favActionButton;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);

            favTitle=itemView.findViewById(R.id.fav_title);
            favCatView=itemView.findViewById(R.id.fav_category);
            favImage=itemView.findViewById(R.id.fav_thumbnail);
            favActionButton=itemView.findViewById(R.id.fav_button);

        }
    }
}
