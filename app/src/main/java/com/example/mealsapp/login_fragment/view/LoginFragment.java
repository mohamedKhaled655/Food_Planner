package com.example.mealsapp.login_fragment.view;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsapp.R;
import com.example.mealsapp.login_fragment.login_repo.LoginRepositoryImpl;

import com.example.mealsapp.login_fragment.presenter.LoginPresenter;
import com.example.mealsapp.login_fragment.presenter.LoginPresenterImpl;
import com.google.firebase.FirebaseApp;

public class LoginFragment extends Fragment implements LoginView {

    private static final String TAG = "LoginFragment";
    private EditText etEmail, etPassword;
    private TextView txtSignUp;
    private Button btnLogin, btnGoogle;
    private ProgressDialog progressDialog;
    private LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_screen, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.checkCurrentUser();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etEmail = view.findViewById(R.id.et_user_login);
        etPassword = view.findViewById(R.id.et_password_login);
        txtSignUp = view.findViewById(R.id.txt_sign_up);
        btnLogin = view.findViewById(R.id.btn_login_toHome);
        btnGoogle = view.findViewById(R.id.btn_google);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Logging in...");


        setupPresenter();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signInWithEmail();
            }
        });
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signInWithGoogle();
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSignUpClicked(view);
            }
        });
    }

    private void setupPresenter() {
        presenter = new LoginPresenterImpl(this, LoginRepositoryImpl.getInstance(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.handleGoogleSignInResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoading(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void navigateToSignUp() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_signUpFragment);
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }
}