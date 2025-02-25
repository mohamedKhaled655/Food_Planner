package com.example.mealsapp.login_fragment.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.mealsapp.login_fragment.login_repo.LoginRepository;
import com.example.mealsapp.login_fragment.view.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImpl implements LoginPresenter{
    private static final String TAG = "LoginPresenterImpl";
    private static final int RC_SIGN_IN = 9001;
    private LoginView loginView;
    private LoginRepository repository;

    public LoginPresenterImpl(LoginView loginView, LoginRepository repository) {
        this.loginView = loginView;
        this.repository = repository;
    }
    @Override
    public void signInWithEmail() {
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            loginView.showError("Please enter email and password");
            return;
        }

        loginView.showLoading("Logging in...");
        repository.signInWithEmail(email, password)
                .addOnCompleteListener(task -> {
                    loginView.hideLoading();
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail: success");
                        loginView.navigateToHome();
                    } else {
                        Log.w(TAG, "signInWithEmail: failure", task.getException());
                        loginView.showError("Login failed: " + task.getException().getMessage());
                    }
                });
    }

    @Override
    public void signInWithGoogle() {
        Intent signInIntent = repository.getGoogleSignInIntent();
        repository.startGoogleSignIn(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void handleGoogleSignInResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google sign-in failed", e);
                loginView.showError("Google Sign-In Failed!");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        repository.signInWithCredential(idToken)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = repository.getCurrentUser();
                        Log.d(TAG, "signInWithCredential: success " + user.getEmail());
                        loginView.showMessage("Welcome " + user.getEmail());
                        loginView.navigateToHome();
                    } else {
                        Log.w(TAG, "signInWithCredential: failure", task.getException());
                        loginView.showError("Authentication Failed!");
                    }
                });
    }

    @Override
    public void checkCurrentUser() {
        FirebaseUser currentUser = repository.getCurrentUser();
        if (currentUser != null) {
            loginView.showMessage("Already logged in: " + currentUser.getEmail());
        }
    }

    @Override
    public void onSignUpClicked(View view) {
        loginView.navigateToSignUp();
    }
}
