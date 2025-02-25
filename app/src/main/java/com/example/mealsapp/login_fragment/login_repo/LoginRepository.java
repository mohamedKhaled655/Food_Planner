package com.example.mealsapp.login_fragment.login_repo;

import android.content.Intent;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface LoginRepository {
    Task<AuthResult> signInWithEmail(String email, String password);
    Task<AuthResult> signInWithCredential(String idToken);
    Intent getGoogleSignInIntent();
    void startGoogleSignIn(Intent signInIntent, int requestCode);
    FirebaseUser getCurrentUser();
}
