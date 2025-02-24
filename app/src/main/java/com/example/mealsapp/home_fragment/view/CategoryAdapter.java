package com.example.mealsapp.home_fragment.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.search_fragment.view.SearchFragmentDirections;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);

        return new CategoryViewHolder(view);

    }
    private static final int[] COLORS = {
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#F8BBD0"),
            Color.parseColor("#E1BEE7"),
            Color.parseColor("#C5CAE9"),
            Color.parseColor("#B2DFDB")
    };

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel=categoryModelList.get(position);
        holder.txtCatTitle.setText(categoryModel.getStrCategory());

        final String imgUrl=categoryModel.getStrCategoryThumb();
        //Glide.with(context).load(imgUrl).circleCrop().into(holder.catImage);
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.err_img)
                .into(holder.catImage);

        int color = COLORS[position % COLORS.length];
        holder.backgroundCircle.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        holder.catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_mealsFragment2);
                HomeFragmentDirections.ActionHomeFragmentToMealsFragment action=HomeFragmentDirections.actionHomeFragmentToMealsFragment(categoryModel.getStrCategory(),"category");
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
    public void setCategories(List<CategoryModel> categoryModels) {
        this.categoryModelList.clear();
        this.categoryModelList.addAll(categoryModels);
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView txtCatTitle,txtCatId,txtCatDesc;
        ImageView catImage;
        ImageView backgroundCircle;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatTitle=itemView.findViewById(R.id.cat_title);

            catImage=itemView.findViewById(R.id.img_category);
            backgroundCircle=itemView.findViewById(R.id.background_circle);

        }
    }
}


