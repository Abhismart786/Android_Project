package com.example.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(signInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in to your account");

        // Sign-in Button Click Listener
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etemail.getText().toString();
                String password = binding.etpassword.getText().toString();

                // Validate if either email or password is empty
                if (email.isEmpty() || password.isEmpty()) {
                    // Show a toast message if any field is empty
                    Toast.makeText(signInActivity.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                    return; // Exit early if validation fails
                }

                // Show progress dialog while signing in
                progressDialog.show();

                // Attempt to sign in with email and password
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // If sign-in is successful, navigate to MainActivity
                            Intent intent = new Intent(signInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Optionally, finish this activity to prevent back navigation
                        } else {
                            // If sign-in fails, show the error message
                            Toast.makeText(signInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Sign-up TextView Click Listener (to navigate to SignUpActivity)
        binding.tvClickSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // If user is already logged in, directly navigate to MainActivity
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(signInActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close sign-in activity
        }
    }
}
