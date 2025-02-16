package com.example.mealsapp.data.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<AreaModel> areaModels;

    public AreaResponse(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
    }

    public List<AreaModel> getAreaModels() {
        return areaModels;
    }

    public void setAreaModels(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
    }

    @Override
    public String toString() {
        return "AreaResponse{" +
                "areaModels=" + areaModels +
                '}';
    }
}
