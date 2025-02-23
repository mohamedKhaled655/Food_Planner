package com.example.mealsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Arrays;

public class SplashFragment extends Fragment {

    private static final String TAG = "SplashFragment";
    private FirebaseAuth mAuth;
    private LottieAnimationView animationView;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationView = view.findViewById(R.id.animation_view);
        try {
            String[] files = getContext().getAssets().list("");
            Log.d(TAG, "Files in assets: " + Arrays.toString(files));
        } catch (IOException e) {
            Log.e(TAG, "Error listing assets", e);
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomFragment);
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
            } else {

                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomFragment);
            }
        }, 3500);
    }
}
