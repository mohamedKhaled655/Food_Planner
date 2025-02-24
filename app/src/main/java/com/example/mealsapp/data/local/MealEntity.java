package com.example.mealsapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "meals")
public class MealEntity {
    @NonNull
    @PrimaryKey

    private String id;
    private String userId;
    private String name;
    private String category;
    private String area;
    private String instructions;
    private String thumbnail;
    private String youtubeUrl;
    private boolean isFavorite;

    public MealEntity(String id,String userId, String name, String category, String area, String instructions, String thumbnail, String youtubeUrl, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.thumbnail = thumbnail;
        this.youtubeUrl = youtubeUrl;
        this.isFavorite = isFavorite;
        this.userId=userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "MealEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", area='" + area + '\'' +
                ", instructions='" + instructions + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", youtubeUrl='" + youtubeUrl + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
