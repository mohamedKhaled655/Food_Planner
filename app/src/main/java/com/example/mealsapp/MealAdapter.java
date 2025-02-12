package com.example.mealsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.mealsapp.Models.MealModel;
import com.example.mealsapp.Models.UserModel;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{
    private Context context;
    private List<MealModel> mealModelList;

    public MealAdapter(Context context, List<MealModel> mealModelList) {
        this.context = context;
        this.mealModelList = mealModelList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view= LayoutInflater.from(context).inflate(R.layout.meal_list,parent,false);
        View view= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new MealViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealModel mealModel=mealModelList.get(position);
        holder.txtMealTitle.setText(mealModel.getStrMeal());
        holder.txtMealId.setText(mealModel.getIdMeal());
       // holder.txtCatMeal.setText(mealModel.getStrCategory());
        final String imgUrl=mealModel.getStrMealThumb();
        Glide.with(context).load(imgUrl).circleCrop().into(holder.mealImage);
        holder.mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "nav to detail"+mealModel.getStrMeal(), Toast.LENGTH_SHORT).show();
               // Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_mealDetailsFragment);

                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealModel);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mealModelList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder{
        TextView txtMealTitle,txtMealId,txtCatMeal;
        ImageView mealImage;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            /*
            txtMealTitle=itemView.findViewById(R.id.txt_mealTitle);
            txtMealId=itemView.findViewById(R.id.txt_mealId);
            txtCatMeal=itemView.findViewById(R.id.txt_mealCategory);
            mealImage=itemView.findViewById(R.id.img_mealImage);
            */
            txtMealTitle=itemView.findViewById(R.id.txt_title);
            txtMealId=itemView.findViewById(R.id.price);
            mealImage=itemView.findViewById(R.id.img_item);

        }
    }
}


