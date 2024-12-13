package com.example.whatsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {

    private EditText etForgotEmail;
    private Button btnSendResetLink;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        etForgotEmail = findViewById(R.id.etForgotEmail);
        btnSendResetLink = findViewById(R.id.btnSendResetLink);

        // Set onClickListener for the reset password button
        btnSendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etForgotEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    etForgotEmail.setError("Please enter your email");
                    return;
                }

                // Send the password reset email
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(forgetpassword.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(forgetpassword.this, "Failed to send reset email. Try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
