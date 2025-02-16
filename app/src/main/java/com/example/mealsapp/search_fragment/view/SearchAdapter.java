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
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.home_fragment.view.CategoryAdapter;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<CategorySearchModel> categorySearchModels;

    public SearchAdapter(Context context, List<CategorySearchModel> searchModelList) {
        this.context = context;
        this.categorySearchModels = searchModelList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_chip,parent,false);

        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        CategorySearchModel categoryModel=categorySearchModels.get(position);
        holder.txtCatTitle.setText(categoryModel.getStrCategory());


        //Glide.with(context).load(imgUrl).circleCrop().into(holder.catImage);
        Glide.with(context)
                .load("https://www.themealdb.com/images/category/"+categoryModel.getStrCategory()+".png")
                .placeholder(R.drawable.lock)
                .error(R.drawable.lock)
                .circleCrop()
                .into(holder.catImage);
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
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatTitle=itemView.findViewById(R.id.txt_titleSearch);

            catImage=itemView.findViewById(R.id.img_searchItem);

        }
    }
}
