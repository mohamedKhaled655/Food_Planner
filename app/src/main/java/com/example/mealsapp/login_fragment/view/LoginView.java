package com.example.mealsapp.login_fragment.view;

public interface LoginView {
    void showLoading(String message);
    void hideLoading();
    void showError(String errorMessage);
    void showMessage(String message);
    void navigateToHome();
    void navigateToSignUp();
    String getEmail();
    String getPassword();
}
