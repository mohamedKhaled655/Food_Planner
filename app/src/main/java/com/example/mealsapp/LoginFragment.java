package com.example.mealsapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsapp.data.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword;
    private TextView txtSignUp;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    FirebaseUser currentUser;
    public LoginFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_screen, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
         currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //navigateToMain();
            Toast.makeText(getContext(), "on start : "+currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseApp.initializeApp(getContext());
        // Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        etEmail = view.findViewById(R.id.et_user_login);
        etPassword = view.findViewById(R.id.et_password_login);

        txtSignUp = view.findViewById(R.id.txt_sign_up);
        btnLogin=view.findViewById(R.id.btn_login_toHome);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Logging in...");

        if (getArguments() != null) {
            String data = LoginFragmentArgs.fromBundle(getArguments()).getTilte();
            Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                signIn(email, password, view);

                //////////////////
               // Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                /*UserModel userModel = new UserModel("Mohamed", "mohamed@example.com");
                LoginFragmentDirections.ActionLoginFragmentToHomeFragment action=LoginFragmentDirections.actionLoginFragmentToHomeFragment(userModel);
                Navigation.findNavController(view).navigate(action);*/
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

    }
    private void signIn(String email, String password, View view) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail: success");
                            UserModel userModel = new UserModel(password, email);
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                            /*LoginFragmentDirections.ActionLoginFragmentToHomeFragment action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(userModel);
                            Navigation.findNavController(view).navigate(action);*/
                        } else {
                            Log.w(TAG, "signInWithEmail: failure", task.getException());
                            Toast.makeText(getContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}