package com.example.mealsapp.home_fragment.view;

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
import com.example.mealsapp.data.Models.CategoryModel;

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

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel=categoryModelList.get(position);
        holder.txtCatTitle.setText(categoryModel.getStrCategory());

        final String imgUrl=categoryModel.getStrCategoryThumb();
        //Glide.with(context).load(imgUrl).circleCrop().into(holder.catImage);
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.lock)
                .error(R.drawable.lock)
                .circleCrop()
                .into(holder.catImage);
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
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatTitle=itemView.findViewById(R.id.cat_title);

            catImage=itemView.findViewById(R.id.img_category);

        }
    }
}


