package com.example.mealsapp.login_fragment.presenter;

import android.content.Intent;
import android.view.View;

public interface LoginPresenter {
    void signInWithEmail();
    void signInWithGoogle();
    void handleGoogleSignInResult(int requestCode, int resultCode, Intent data);
    void checkCurrentUser();
    void onSignUpClicked(View view);
}
