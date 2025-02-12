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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";
    private FirebaseAuth mAuth;
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    public SignUpFragment() {
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
        return inflater.inflate(R.layout.sign_up_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etName = view.findViewById(R.id.et_userName_register);
        etEmail = view.findViewById(R.id.et_email_register);
        etPassword = view.findViewById(R.id.et_pass_register);
       // etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnSignUp = view.findViewById(R.id.btn_login_toHome);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Creating account...");

        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
           // String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (validateInputs(name, email, password)) {
                registerUser(name, email, password, view);
            }
        });
    }

    private boolean validateInputs(String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(getContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registerUser(String name, String email, String password, View view) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail: success");
                        FirebaseUser user = mAuth.getCurrentUser();


                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates);
                        }

                        Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();


                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_LoginFragment);
                    } else {
                        Log.w(TAG, "createUserWithEmail: failure", task.getException());
                        Toast.makeText(getContext(), "Sign-up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
