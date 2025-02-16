package com.example.mealsapp.data.Models;

public class AreaModel {
    private String strArea;

    public AreaModel(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public String toString() {
        return "AreaModel{" +
                "strArea='" + strArea + '\'' +
                '}';
    }
}
