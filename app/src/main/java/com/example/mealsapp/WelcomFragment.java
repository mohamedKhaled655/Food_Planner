package com.example.mealsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class WelcomFragment extends Fragment {
    TextView btnLogin,btnSignUp;

    public WelcomFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.start_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin=view.findViewById(R.id.btn_wel_login);
        btnSignUp=view.findViewById(R.id.registerButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Navigation.findNavController(view).navigate(R.id.action_welcomFragment_to_loginFragment);
                //WelcomFragmentDirections.ActionWelcomFragmentToLoginFragment action=WelcomFragmentDirections.actionWelcomFragmentToLoginFragment();
               // Navigation.findNavController(view).navigate(action);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigation.findNavController(view).navigate(R.id.action_welcomFragment_to_loginFragment);

                Navigation.findNavController(view).navigate(R.id.action_welcomFragment_to_signUpFragment);
            }
        });
    }
}