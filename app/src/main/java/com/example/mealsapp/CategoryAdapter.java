package com.example.mealsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.Models.CategoryModel;

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
        View view= LayoutInflater.from(context).inflate(R.layout.catorgoy_list,parent,false);

        return new CategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel=categoryModelList.get(position);
        holder.txtCatTitle.setText(categoryModel.getStrCategory());
        holder.txtCatId.setText(categoryModel.getIdCategory());
        holder.txtCatDesc.setText(categoryModel.getStrCategoryDescription());
        final String imgUrl=categoryModel.getStrCategoryThumb();
        Glide.with(context).load(imgUrl).circleCrop().into(holder.catImage);
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
            txtCatTitle=itemView.findViewById(R.id.txt_catTitle);
            txtCatId=itemView.findViewById(R.id.txt_id);
            txtCatDesc=itemView.findViewById(R.id.txt_desc);
            catImage=itemView.findViewById(R.id.img_catImage);

        }
    }
}


