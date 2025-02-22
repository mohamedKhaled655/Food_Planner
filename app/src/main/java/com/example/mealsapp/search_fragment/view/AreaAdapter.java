package com.example.mealsapp.search_fragment.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.AreaModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {
    private Context context;
    private List<AreaModel> areaModels;

    public AreaAdapter(Context context, List<AreaModel> areaModels) {
        this.context = context;
        this.areaModels = areaModels;
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chip, parent, false);
        return new AreaViewHolder(view);
    }
    private static final int[] COLORS = {
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#F8BBD0"),
            Color.parseColor("#E1BEE7"),
            Color.parseColor("#C5CAE9"),
            Color.parseColor("#B2DFDB")
    };
    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        AreaModel areaModel = areaModels.get(position);
        holder.txtAreaTitle.setText(areaModel.getStrArea());
        int color = COLORS[position % COLORS.length];

        holder.cardView.setBackgroundTintList(ColorStateList.valueOf(color));
        String url=areaModel.getStrArea().substring(0, 2).toUpperCase();

        Glide.with(context)
                .load("https://flagsapi.com/"+url+"/shiny/64.png")
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.err_img)
                .into(holder.areaImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment2);
                SearchFragmentDirections.ActionSearchFragmentToMealsFragment2 action=SearchFragmentDirections.actionSearchFragmentToMealsFragment2(areaModel.getStrArea(),"area");
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaModels.size();
    }

    public void setAreas(List<AreaModel> areaModels) {
        this.areaModels.clear();
        this.areaModels.addAll(areaModels);
        notifyDataSetChanged();
    }

    public static class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView txtAreaTitle;
        ImageView areaImage;
        MaterialCardView cardView;
        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAreaTitle = itemView.findViewById(R.id.txt_titleSearch);
            areaImage = itemView.findViewById(R.id.img_searchItem);
            cardView=itemView.findViewById(R.id.card_chip_item);
        }
    }
}
