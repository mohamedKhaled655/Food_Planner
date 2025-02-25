package com.example.mealsapp.sign_up_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mealsapp.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";
    private static final int REQ_ONE_TAP = 2;


    private FirebaseAuth mAuth;


    private SignInClient oneTapClient;
    private GoogleSignInClient googleSignInClient;


    private EditText etName, etEmail, etPassword;
    private Button btnSignUp, btnGoogle;
    private ProgressDialog progressDialog;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        initializeCredentialsApi();
    }

    private void initializeCredentialsApi() {
        oneTapClient = Identity.getSignInClient(requireActivity());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupClickListeners();
    }

    private void initializeViews(View view) {
        etName = view.findViewById(R.id.et_userName_register);
        etEmail = view.findViewById(R.id.et_email_register);
        etPassword = view.findViewById(R.id.et_pass_register);
        btnSignUp = view.findViewById(R.id.btn_login_toHome);
        btnGoogle = view.findViewById(R.id.btn_google_toHome);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Creating account...");
        progressDialog.setCancelable(false);
    }

    private void setupClickListeners() {
        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInputs(name, email, password)) {
                registerUser(name, email, password);
            }
        });

        btnGoogle.setOnClickListener(v -> startGoogleSignIn());
    }

    private void startGoogleSignIn() {
        progressDialog.setMessage("Signing in with Google...");
        progressDialog.show();

        BeginSignInRequest signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)

                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(result -> {
                    try {
                        startIntentSenderForResult(
                                result.getPendingIntent().getIntentSender(),
                                REQ_ONE_TAP,
                                null, 0, 0, 0, null
                        );
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Google Sign-in failed: " + e.getLocalizedMessage());
                    progressDialog.dismiss();
                    showError("Google Sign-in failed: " + e.getLocalizedMessage());
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ONE_TAP) {
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                if (idToken != null) {
                    firebaseAuthWithGoogle(idToken);
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google Sign-in failed", e);
                progressDialog.dismiss();
                showError("Google sign in failed: " + e.getStatusMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        handleSuccessfulGoogleSignIn(user);
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        showError("Authentication failed: " + task.getException().getMessage());
                    }
                });
    }

    private boolean validateInputs(String name, String email, String password) {
        if (name.isEmpty()) {
            showError("Name is required");
            return false;
        }
        if (email.isEmpty()) {
            showError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Invalid email format");
            return false;
        }
        if (password.isEmpty()) {
            showError("Password is required");
            return false;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private void registerUser(String name, String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            updateUserProfile(user, name);
                        }
                    } else {
                        progressDialog.dismiss();
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        showError("Sign-up failed: " + task.getException().getMessage());
                    }
                });
    }

    private void updateUserProfile(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        showSuccess("Account created successfully");
                        navigateToLogin();
                    } else {
                        Log.w(TAG, "updateProfile:failure", task.getException());
                        showError("Profile update failed");
                    }
                });
    }

    private void handleSuccessfulGoogleSignIn(FirebaseUser user) {
        if (user != null) {
            showSuccess("Signed in as " + user.getDisplayName());
            navigateToHome();
        }
    }

    private void navigateToLogin() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_signUpFragment_to_LoginFragment);
    }

    private void navigateToHome() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_signUpFragment_to_LoginFragment);
    }

    private void showError(String message) {
        Log.i(TAG, "showError: "+message);
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showSuccess(String message) {
        Log.i(TAG, "showSuccess: "+message);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}