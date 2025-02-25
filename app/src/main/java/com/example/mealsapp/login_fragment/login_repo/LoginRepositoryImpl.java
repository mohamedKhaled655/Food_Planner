package com.example.mealsapp.login_fragment.login_repo;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.mealsapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
public class LoginRepositoryImpl implements LoginRepository{
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Fragment fragment;
    private Context context;

    private static LoginRepositoryImpl instance;

    private LoginRepositoryImpl(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public static synchronized LoginRepositoryImpl getInstance(Fragment fragment) {
        if (instance == null) {
            instance = new LoginRepositoryImpl(fragment);
        }
        return instance;
    }
    @Override
    public Task<AuthResult> signInWithEmail(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> signInWithCredential(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        return mAuth.signInWithCredential(credential);
    }

    @Override
    public Intent getGoogleSignInIntent() {
        return mGoogleSignInClient.getSignInIntent();
    }

    @Override
    public void startGoogleSignIn(Intent signInIntent, int requestCode) {
        fragment.startActivityForResult(signInIntent, requestCode);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}
