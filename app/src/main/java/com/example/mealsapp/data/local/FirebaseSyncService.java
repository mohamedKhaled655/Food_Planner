package com.example.mealsapp.data.local;

import android.util.Log;
import com.example.mealsapp.data.local.MealEntity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseSyncService {
    private static final String TAG = "FirebaseSyncService";
    private static final String FAVORITES_COLLECTION = "user_favorites";
    private static final String MEALS_FIELD = "meals";

    private final FirebaseFirestore db;
    private final String userId;

    public FirebaseSyncService(String userId) {
        this.userId = userId;
        this.db = FirebaseFirestore.getInstance();
    }


    public Completable backupUserFavorites(List<MealEntity> favorites) {
        return Completable.create(emitter -> {
            if (userId == null || userId.isEmpty()) {
                emitter.onError(new IllegalStateException("User ID is required for backup"));
                return;
            }

            List<Map<String, Object>> mealsList = new ArrayList<>();
            for (MealEntity meal : favorites) {
                mealsList.add(mealEntityToMap(meal));
            }

            Map<String, Object> userFavorites = new HashMap<>();
            userFavorites.put(MEALS_FIELD, mealsList);
            userFavorites.put("lastUpdated", System.currentTimeMillis());

            db.collection(FAVORITES_COLLECTION)
                    .document(userId)
                    .set(userFavorites)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Successfully backed up " + favorites.size() + " favorites for user " + userId);
                        emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error backing up favorites", e);
                        emitter.onError(e);
                    });
        });
    }


    public Single<List<MealEntity>> restoreUserFavorites() {
        return Single.create(emitter -> {
            if (userId == null || userId.isEmpty()) {
                emitter.onError(new IllegalStateException("User ID is required for restore"));
                return;
            }

            db.collection(FAVORITES_COLLECTION)
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        List<MealEntity> restoredMeals = new ArrayList<>();

                        if (documentSnapshot.exists() && documentSnapshot.contains(MEALS_FIELD)) {
                            List<Map<String, Object>> mealsData = (List<Map<String, Object>>) documentSnapshot.get(MEALS_FIELD);

                            if (mealsData != null) {
                                for (Map<String, Object> mealData : mealsData) {
                                    MealEntity meal = mapToMealEntity(mealData);
                                    if (meal != null) {
                                        restoredMeals.add(meal);
                                    }
                                }
                            }

                            Log.d(TAG, "Successfully restored " + restoredMeals.size() + " favorites for user " + userId);
                        } else {
                            Log.d(TAG, "No favorites found for user " + userId);
                        }

                        emitter.onSuccess(restoredMeals);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error restoring favorites", e);
                        emitter.onError(e);
                    });
        });
    }


    private Map<String, Object> mealEntityToMap(MealEntity meal) {
        Map<String, Object> mealMap = new HashMap<>();
        mealMap.put("id", meal.getId());
        mealMap.put("userId", meal.getUserId());
        mealMap.put("name", meal.getName());
        mealMap.put("category", meal.getCategory());
        mealMap.put("area", meal.getArea());
        mealMap.put("instructions", meal.getInstructions());
        mealMap.put("thumbnail", meal.getThumbnail());
        mealMap.put("youtubeUrl", meal.getYoutubeUrl());
        mealMap.put("isFavorite", meal.isFavorite());
        return mealMap;
    }


    private MealEntity mapToMealEntity(Map<String, Object> mealMap) {
        try {
            String id = (String) mealMap.get("id");
            String userId = (String) mealMap.get("userId");
            String name = (String) mealMap.get("name");
            String category = (String) mealMap.get("category");
            String area = (String) mealMap.get("area");
            String instructions = (String) mealMap.get("instructions");
            String thumbnail = (String) mealMap.get("thumbnail");
            String youtubeUrl = (String) mealMap.get("youtubeUrl");
            boolean isFavorite = (boolean) mealMap.get("isFavorite");

            return new MealEntity(id, userId, name, category, area,
                    instructions, thumbnail, youtubeUrl, isFavorite);
        } catch (Exception e) {
            Log.e(TAG, "Error converting map to MealEntity", e);
            return null;
        }
    }
}