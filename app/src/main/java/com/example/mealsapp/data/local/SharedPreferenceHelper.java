package com.example.mealsapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceHelper {
    private static final String TAG = "SharedPreferenceHelper";
    private static final String P_NAME = "PlannerPrefs";
    private static final String USER_ID = "user";
    private static final String FIRST_LAUNCH = "firstLaunch";
    private static SharedPreferenceHelper instance;
    private final SharedPreferences sharedPreferences;

    private SharedPreferenceHelper(Context context) {

        Context appContext = context.getApplicationContext();
        sharedPreferences = appContext.getSharedPreferences(P_NAME, Context.MODE_PRIVATE);
    }
    public static synchronized SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper(context);
        }
        return instance;
    }
    public static SharedPreferenceHelper getInstance() {
        if (instance == null) {
            Log.i(TAG, "getInstance: "+"SharedPreferenceHelper must be initialized with getInstance(Context context) first");
        }
        return instance;
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID, userId).apply();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(FIRST_LAUNCH, true);
    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        sharedPreferences.edit().putBoolean(FIRST_LAUNCH, isFirstLaunch).apply();
    }
}
