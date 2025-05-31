package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public class LoginRegisterUser extends AppCompatActivity {
private boolean isLogin = true; // Flag to check if it's Login or SignUp
private TextView loginText, signUpText;
private TextInputLayout nameInputLayout, emailInputLayout, passwordInputLayout;
private Button signUpButton;
private FirebaseAuth mAuth; // Firebase Authentication instance
private TextInputLayout mobileNumberInputLayout;
private Button sendVerificationCodeButton;
private String verificationId; // To store the verification ID
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_register_user);
    
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
    
    loginText = findViewById(R.id.loginText2);
    signUpText = findViewById(R.id.signUpText2);
    nameInputLayout = findViewById(R.id.nameInputLayout2);
    emailInputLayout = findViewById(R.id.emailInputLayout2);
    passwordInputLayout = findViewById(R.id.passwordInputLayout12);
    signUpButton = findViewById(R.id.signUpButton2);
    
    // Click listeners for Login and SignUp text toggling
    loginText.setOnClickListener(v -> showLoginView());
    signUpText.setOnClickListener(v -> showSignUpView());
    
    // Initializing the form in "Login" mode by default
    showLoginView(); // By default, it shows Login view
}

private void showLoginView() {
    isLogin = true;
    nameInputLayout.setVisibility(View.GONE); // Hide Full Name input in Login mode
    signUpButton.setText("LOGIN"); // Change button text to LOGIN
    loginText.setTextColor(getResources().getColor(R.color.primary_color)); // Highlight login
    signUpText.setTextColor(getResources().getColor(R.color.black)); // Dim SignUp
}

private void showSignUpView() {
    isLogin = false;
    nameInputLayout.setVisibility(View.VISIBLE); // Show Full Name input in SignUp mode
    signUpButton.setText("SIGN UP"); // Change button text to SIGN UP
    signUpText.setTextColor(getResources().getColor(R.color.primary_color)); // Highlight SignUp
    loginText.setTextColor(getResources().getColor(R.color.black)); // Dim Login
}

public void onSignUpButtonClick(View view) {
    if (isLogin) {
        // Handle login logic
        performLogin();
    } else {
        // Handle sign-up logic
        performSignUp();
    }
}

private void performLogin() {
    String email = emailInputLayout.getEditText().getText().toString().trim();
    String password = passwordInputLayout.getEditText().getText().toString().trim();
    
    if (email.isEmpty() || password.isEmpty()) {
        emailInputLayout.setError("Please enter your email");
        passwordInputLayout.setError("Please enter your password");
    } else {
        // Authenticate with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        goToDashboard();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginRegisterUser.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

private void performSignUp() {
    String name = nameInputLayout.getEditText().getText().toString().trim();
    String email = emailInputLayout.getEditText().getText().toString().trim();
    String password = passwordInputLayout.getEditText().getText().toString().trim();
    
    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
        nameInputLayout.setError("Please enter your full name");
        emailInputLayout.setError("Please enter your email");
        passwordInputLayout.setError("Please enter your password");
    } else {
        // Create a new user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        goToDashboard();
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(LoginRegisterUser.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

private void goToDashboard() {
    Intent intent = new Intent(LoginRegisterUser.this, Dashboard.class);
    startActivity(intent);
    finish(); // Optional: close this activity
}
}
