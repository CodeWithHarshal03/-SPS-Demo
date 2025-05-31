package com.example.smartparking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartparking.R;
import com.google.android.material.textfield.TextInputLayout;

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

public class LoginRegisterActivity extends AppCompatActivity {

private boolean isLogin = true; // Flag to check if it's Login or SignUp
private TextView loginText, signUpText;
private TextInputLayout nameInputLayout, emailInputLayout, passwordInputLayout;
private Button signUpButton;
private FirebaseAuth mAuth; // Firebase Authentication instance

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login_register);
	mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
	// Find views by their ID
	loginText = findViewById(R.id.loginText);
	signUpText = findViewById(R.id.signUpText);
	nameInputLayout = findViewById(R.id.nameInputLayout);
	emailInputLayout = findViewById(R.id.emailInputLayout);
	passwordInputLayout = findViewById(R.id.passwordInputLayout);
	signUpButton = findViewById(R.id.signUpButton);
	
	// Click listeners for Login and SignUp text toggling
	loginText.setOnClickListener(v -> showLoginView());
	signUpText.setOnClickListener(v -> showSignUpView());
	
	// Initializing the form in "Login" mode by default
	showLoginView(); // By default, it shows Login view
}

// Method to switch to Login view
private void showLoginView() {
	isLogin = true;
	nameInputLayout.setVisibility(View.GONE); // Hide Full Name input in Login mode
	signUpButton.setText("LOGIN"); // Change button text to LOGIN
	loginText.setTextColor(getResources().getColor(R.color.primary_color)); // Highlight login
	signUpText.setTextColor(getResources().getColor(R.color.black)); // Dim SignUp
}

// Method to switch to SignUp view
private void showSignUpView() {
	isLogin = false;
	nameInputLayout.setVisibility(View.VISIBLE); // Show Full Name input in SignUp mode
	signUpButton.setText("SIGN UP"); // Change button text to SIGN UP
	signUpText.setTextColor(getResources().getColor(R.color.primary_color)); // Highlight SignUp
	loginText.setTextColor(getResources().getColor(R.color.black)); // Dim Login
}

// Here you can add logic for handling button clicks for login or sign-up
public void onSignUpButtonClick(View view) {
	if (isLogin) {
		// Handle login logic
		performLogin();
	} else {
		// Handle sign-up logic
		performSignUp();
	}
}

// Logic for performing login
private void performLogin() {
	// Add the logic for logging in the user (e.g., API call or authentication)
	String email = emailInputLayout.getEditText().getText().toString().trim();
	String password = passwordInputLayout.getEditText().getText().toString().trim();
	
	if (email.isEmpty() || password.isEmpty()) {
		// Handle validation for empty fields
		emailInputLayout.setError("Please enter your email");
		passwordInputLayout.setError("Please enter your password");
	} else {
		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, task -> {
					if (task.isSuccessful()) {
						// Sign in success, update UI with the signed-in user's information
						FirebaseUser user = mAuth.getCurrentUser();
						goToDashboard();
					} else {
						// If sign in fails, display a message to the user.
						Toast.makeText(LoginRegisterActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
					}
				});
	}
}

// Logic for performing sign-up
private void performSignUp() {
	// Add the logic for signing up the user
	String name = nameInputLayout.getEditText().getText().toString().trim();
	String email = emailInputLayout.getEditText().getText().toString().trim();
	String password = passwordInputLayout.getEditText().getText().toString().trim();
	
	if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
		// Handle validation for empty fields
		nameInputLayout.setError("Please enter your full name");
		emailInputLayout.setError("Please enter your email");
		passwordInputLayout.setError("Please enter your password");
	} else {
		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, task -> {
					if (task.isSuccessful()) {
						// Sign up success, update UI with the signed-in user's information
						FirebaseUser user = mAuth.getCurrentUser();
						goToDashboard();
					} else {
						// If sign up fails, display a message to the user.
						Toast.makeText(LoginRegisterActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
					}
				});
	}
	
}

private void goToDashboard() {
	Intent intent = new Intent(LoginRegisterActivity.this, Adminstratoravailable.class);
	startActivity(intent);
	finish(); // Optional: close this activity
}

public void showLoginView(View view) {
}

public void showSignUpView(View view) {
}
}
