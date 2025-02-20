package com.example.mealsapp.data.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MealDetailsModel{
    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strDrinkAlternate")
    private String drinkAlternate;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strArea")
    private String area;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strMealThumb")
    private String imageUrl;

    @SerializedName("strTags")
    private String tags;

    @SerializedName("strYoutube")
    private String youtubeUrl;

    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strIngredient3")
    private String ingredient3;
    @SerializedName("strIngredient4")
    private String ingredient4;
    @SerializedName("strIngredient5")
    private String ingredient5;
    @SerializedName("strIngredient6")
    private String ingredient6;
    @SerializedName("strIngredient7")
    private String ingredient7;
    @SerializedName("strIngredient8")
    private String ingredient8;
    @SerializedName("strIngredient9")
    private String ingredient9;
    @SerializedName("strIngredient10")
    private String ingredient10;
    @SerializedName("strIngredient11")
    private String ingredient11;
    @SerializedName("strIngredient12")
    private String ingredient12;
    @SerializedName("strIngredient13")
    private String ingredient13;
    @SerializedName("strIngredient14")
    private String ingredient14;
    @SerializedName("strIngredient15")
    private String ingredient15;
    @SerializedName("strIngredient16")
    private String ingredient16;
    @SerializedName("strIngredient17")
    private String ingredient17;
    @SerializedName("strIngredient18")
    private String ingredient18;
    @SerializedName("strIngredient19")
    private String ingredient19;
    @SerializedName("strIngredient20")
    private String ingredient20;

    @SerializedName("strMeasure1")
    private String measure1;
    @SerializedName("strMeasure2")
    private String measure2;
    @SerializedName("strMeasure3")
    private String measure3;
    @SerializedName("strMeasure4")
    private String measure4;
    @SerializedName("strMeasure5")
    private String measure5;
    @SerializedName("strMeasure6")
    private String measure6;
    @SerializedName("strMeasure7")
    private String measure7;
    @SerializedName("strMeasure8")
    private String measure8;
    @SerializedName("strMeasure9")
    private String measure9;
    @SerializedName("strMeasure10")
    private String measure10;
    @SerializedName("strMeasure11")
    private String measure11;
    @SerializedName("strMeasure12")
    private String measure12;
    @SerializedName("strMeasure13")
    private String measure13;
    @SerializedName("strMeasure14")
    private String measure14;
    @SerializedName("strMeasure15")
    private String measure15;
    @SerializedName("strMeasure16")
    private String measure16;
    @SerializedName("strMeasure17")
    private String measure17;
    @SerializedName("strMeasure18")
    private String measure18;
    @SerializedName("strMeasure19")
    private String measure19;
    @SerializedName("strMeasure20")
    private String measure20;

    @SerializedName("strSource")
    private String source;

    @SerializedName("strImageSource")
    private String imageSource;

    @SerializedName("strCreativeCommonsConfirmed")
    private String creativeCommonsConfirmed;

    @SerializedName("dateModified")
    private String dateModified;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDrinkAlternate() { return drinkAlternate; }
    public void setDrinkAlternate(String drinkAlternate) { this.drinkAlternate = drinkAlternate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getYoutubeUrl() { return youtubeUrl; }
    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }

    // Helper methods
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        addIngredient(ingredients, ingredient1, measure1);
        addIngredient(ingredients, ingredient2, measure2);
        addIngredient(ingredients, ingredient3, measure3);
        addIngredient(ingredients, ingredient4, measure4);
        addIngredient(ingredients, ingredient5, measure5);
        addIngredient(ingredients, ingredient6, measure6);
        addIngredient(ingredients, ingredient7, measure7);
        addIngredient(ingredients, ingredient8, measure8);
        addIngredient(ingredients, ingredient9, measure9);
        addIngredient(ingredients, ingredient10, measure10);
        addIngredient(ingredients, ingredient11, measure11);
        addIngredient(ingredients, ingredient12, measure12);
        addIngredient(ingredients, ingredient13, measure13);
        addIngredient(ingredients, ingredient14, measure14);
        addIngredient(ingredients, ingredient15, measure15);
        addIngredient(ingredients, ingredient16, measure16);
        addIngredient(ingredients, ingredient17, measure17);
        addIngredient(ingredients, ingredient18, measure18);
        addIngredient(ingredients, ingredient19, measure19);
        addIngredient(ingredients, ingredient20, measure20);

        return ingredients;
    }

    private void addIngredient(List<Ingredient> list, String ingredient, String measure) {
        if (ingredient != null && !ingredient.trim().isEmpty()) {
            list.add(new Ingredient(ingredient.trim(), measure != null ? measure.trim() : ""));
        }
    }

    // Inner class to pair ingredients with measurements
    public static class Ingredient {
        private String name;
        private String measure;

        public Ingredient(String name, String measure) {
            this.name = name;
            this.measure = measure;
        }

        public String getName() { return name; }
        public String getMeasure() { return measure; }

        @Override
        public String toString() {
            return measure.isEmpty() ? name : measure + " " + name;
        }
    }
}
