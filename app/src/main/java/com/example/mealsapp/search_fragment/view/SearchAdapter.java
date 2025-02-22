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
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.home_fragment.view.CategoryAdapter;
import com.example.mealsapp.home_fragment.view.HomeFragmentDirections;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Random;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<CategorySearchModel> categorySearchModels;
    private Random random;

    public SearchAdapter(Context context, List<CategorySearchModel> searchModelList) {
        this.context = context;
        this.categorySearchModels = searchModelList;
        this.random = new Random();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_chip,parent,false);

        return new SearchAdapter.SearchViewHolder(view);
    }
    private static final int[] COLORS = {
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#F8BBD0"),
            Color.parseColor("#E1BEE7"),
            Color.parseColor("#C5CAE9"),
            Color.parseColor("#B2DFDB")
    };

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        CategorySearchModel categoryModel=categorySearchModels.get(position);
        holder.txtCatTitle.setText(categoryModel.getStrCategory());

        int color = COLORS[position % COLORS.length];

        holder.cardView.setBackgroundTintList(ColorStateList.valueOf(color));
        //Glide.with(context).load(imgUrl).circleCrop().into(holder.catImage);
        Glide.with(context)
                .load("https://www.themealdb.com/images/category/"+categoryModel.getStrCategory()+".png")
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.err_img)
                .into(holder.catImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment2);
                SearchFragmentDirections.ActionSearchFragmentToMealsFragment2 action=SearchFragmentDirections.actionSearchFragmentToMealsFragment2(categoryModel.getStrCategory(),"category");
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorySearchModels.size();
    }
    public void setCategories(List<CategorySearchModel> categoryModels) {
        this.categorySearchModels.clear();
        this.categorySearchModels.addAll(categoryModels);
        notifyDataSetChanged();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView txtCatTitle,txtCatId,txtCatDesc;
        ImageView catImage;
        MaterialCardView cardView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatTitle=itemView.findViewById(R.id.txt_titleSearch);

            catImage=itemView.findViewById(R.id.img_searchItem);
            cardView=itemView.findViewById(R.id.card_chip_item);

        }
    }
}
