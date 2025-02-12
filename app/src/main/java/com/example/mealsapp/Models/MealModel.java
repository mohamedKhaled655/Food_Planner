package com.example.mealsapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MealModel  implements Parcelable {
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strArea;
    private String strCategory;
    private String strInstructions;
    private String strYoutube;

    public MealModel() {
    }

    public MealModel(String idMeal, String strMeal, String strMealThumb, String strArea, String strCategory, String strInstructions, String strYoutube) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strArea = strArea;
        this.strCategory = strCategory;
        this.strInstructions = strInstructions;
        this.strYoutube = strYoutube;
    }

    protected MealModel(Parcel in) {
        idMeal = in.readString();
        strMeal = in.readString();
        strMealThumb = in.readString();
        strArea = in.readString();
        strCategory = in.readString();
        strInstructions = in.readString();
        strYoutube = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idMeal);
        dest.writeString(strMeal);
        dest.writeString(strMealThumb);
        dest.writeString(strArea);
        dest.writeString(strCategory);
        dest.writeString(strInstructions);
        dest.writeString(strYoutube);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MealModel> CREATOR = new Creator<MealModel>() {
        @Override
        public MealModel createFromParcel(Parcel in) {
            return new MealModel(in);
        }

        @Override
        public MealModel[] newArray(int size) {
            return new MealModel[size];
        }
    };

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    @Override
    public String toString() {
        return "MealModel{" +
                "idMeal='" + idMeal + '\'' +
                ", strMeal='" + strMeal + '\'' +
                ", strMealThumb='" + strMealThumb + '\'' +
                ", strArea='" + strArea + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strInstructions='" + strInstructions + '\'' +
                ", strYoutube='" + strYoutube + '\'' +
                '}';
    }
}
