package com.example.mealsapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "planned_meals")
public class PlannedMealEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String mealId;
    private String mealName;
    private String category;
    private String area;
    private String instructions;
    private String thumbUrl;
    private String youtubeUrl;
    private String plannedDate;

    public PlannedMealEntity(@NonNull String id, String mealId, String mealName, String category,
                             String area, String instructions, String thumbUrl,
                             String youtubeUrl, String plannedDate) {
        this.id = id;
        this.mealId = mealId;
        this.mealName = mealName;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.thumbUrl = thumbUrl;
        this.youtubeUrl = youtubeUrl;
        this.plannedDate = plannedDate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }
}