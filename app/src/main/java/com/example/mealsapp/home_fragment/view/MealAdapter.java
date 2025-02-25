package com.example.mealsapp.home_fragment.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealEntity;
import com.example.mealsapp.data.local.PlannedMealEntity;

import java.util.Calendar;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private final Context context;
    private List<MealModel> mealModelList;
    private final OnAddFavClickListener listener;


    public MealAdapter(Context context, List<MealModel> mealModelList, OnAddFavClickListener onFavClickLisenter) {
        this.context = context;
        this.mealModelList = mealModelList;
        this.listener = onFavClickLisenter;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealModel mealModel = mealModelList.get(position);
        holder.txtMealTitle.setText(mealModel.getStrMeal());
       // holder.txtMealId.setText(mealModel.getIdMeal());

        String imgUrl = mealModel.getStrMealThumb();
        Glide.with(context).load(imgUrl).placeholder(R.drawable.loadimg)
                .error(R.drawable.meal_back).into(holder.mealImage);

        updateFavButton(holder.btnImgFav, mealModel.isFavorite());
        holder.mealImage.setOnClickListener(view -> {
            Toast.makeText(context, "Navigating to details: " + mealModel.getStrMeal(), Toast.LENGTH_SHORT).show();
            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealModel.getIdMeal());
            Navigation.findNavController(view).navigate(action);
        });
        MealEntity mealEntity = new MealEntity(
                mealModel.getIdMeal(),
                listener.userId(),
                mealModel.getStrMeal(),
                mealModel.getStrCategory(),
                mealModel.getStrArea(),
                mealModel.getStrInstructions(),
                mealModel.getStrMealThumb(),
                mealModel.getStrYoutube(),
                true
        );

        holder.btnImgFav.setOnClickListener(view -> {
            if(mealModel.isFavorite()){
                listener.onRemoveFromFavorite(mealEntity);
                mealModel.setFavorite(false);
                Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }else{



                if (listener != null) {
                    listener.onAddToFavorite(mealEntity);
                    mealModel.setFavorite(true);
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
            updateFavButton(holder.btnImgFav, mealModel.isFavorite());

        });

        holder.btnCalender.setOnClickListener(view -> {
            // Create a DatePickerDialog
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        // Format the selected date
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        // Here you can save the meal with the selected date
                        // For example, save to a "planned meals" database
                        Toast.makeText(context,
                                "Added " + mealModel.getStrMeal() + " to your plan for " + selectedDate,
                                Toast.LENGTH_SHORT).show();
                        String plannedMealId = mealModel.getIdMeal() + "_" + selectedDate;

                        PlannedMealEntity plannedMeal = new PlannedMealEntity(
                                plannedMealId,
                                listener.userId(),
                                mealModel.getIdMeal(),
                                mealModel.getStrMeal(),
                                mealModel.getStrCategory(),
                                mealModel.getStrArea(),
                                mealModel.getStrInstructions(),
                                mealModel.getStrMealThumb(),
                                mealModel.getStrYoutube(),
                                selectedDate
                        );
                        listener.onAddToPlannedMeal(plannedMeal);
                        // If you have a repository for planned meals, you could call it here
                        // Example: planMealRepository.addPlannedMeal(mealEntity, selectedDate);
                    },
                    year, month, day);

            datePickerDialog.show();
        });
    }

    public void setMeals(List<MealModel> mealModels) {
        this.mealModelList = mealModels;
        notifyDataSetChanged();
    }

    public void updateData(List<MealModel> mealModels) {
        this.mealModelList = mealModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mealModelList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealTitle, txtMealId;
        ImageView mealImage;
        ImageButton btnImgFav,btnCalender;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.txt_meal_name_card);
            //txtMealId = itemView.findViewById(R.id.price);
            mealImage = itemView.findViewById(R.id.img_card);
            btnImgFav = itemView.findViewById(R.id.favoriteButtonCard);
            btnCalender = itemView.findViewById(R.id.calenderButtonCard);
        }
    }

    private void updateFavButton(ImageButton btnImgFav, boolean isFavorite) {
        if (isFavorite) {
            btnImgFav.setBackgroundResource(R.drawable.circle);
        } else {
            btnImgFav.setBackgroundResource(R.drawable.circle_background);
        }
    }
}
