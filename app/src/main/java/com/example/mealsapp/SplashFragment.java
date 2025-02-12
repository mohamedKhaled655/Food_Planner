package com.example.mealsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends Fragment {


    private FirebaseAuth mAuth;

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

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomFragment);
            /*FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {

                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
            } else {

                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_welcomFragment);
            }*/
        }, 3000);
    }
}
