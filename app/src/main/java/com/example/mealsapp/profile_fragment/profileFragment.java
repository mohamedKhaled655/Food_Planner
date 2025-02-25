package com.example.mealsapp.profile_fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsapp.R;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private FirebaseAuth mAuth;
    private Button btnLogout;
    private TextView name,email;
    private View rootView;

    public profileFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.logout);
        name = view.findViewById(R.id.txt_profile);
        email = view.findViewById(R.id.txt_email);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.i(TAG, "onViewCreated: " + currentUser.getUid()+"name"+currentUser.getDisplayName());
            name.setText(currentUser.getDisplayName());
            email.setText(currentUser.getEmail());
            Toast.makeText(getContext(),
                    currentUser.getEmail() + " id : " + currentUser.getUid(),
                    Toast.LENGTH_SHORT).show();
        }

        setupLogoutButton();
    }

    private void setupLogoutButton() {

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Log out")
                        .setMessage("Are You Sure !")
                        .setPositiveButton("Yes", (dialog, which) -> performLogout())
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }



    private void performLogout() {
        mAuth.signOut();
        GoogleSignIn.getClient(
                        requireContext(),
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut()
                .addOnCompleteListener(task -> {
                    if (isAdded()) {
                        navigateToLogin();
                    }
                });
    }

    private void navigateToLogin() {
      MealRepository repository= MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));
        repository.setUserIdToSharedPref(null);
        Navigation.findNavController(rootView)
                    .navigate(
                            R.id.action_profileFragment_to_welcomFragment,
                            null,
                            new androidx.navigation.NavOptions.Builder()
                                    .setPopUpTo(R.id.profileFragment, true)
                                    .build()
                    );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
        btnLogout = null;
        name = null;
    }
}