package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartparking.R;

public class user_Switch extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	EdgeToEdge.enable(this);
	setContentView(com.example.smartparking.R.layout.activity_user_switch);

}
public void openUserLogin(View view) {
	// Switch to User Login Activity
	Intent intent = new Intent(user_Switch.this, LoginRegisterUser.class);
	startActivity(intent);
}

// Define the openAdminLogin method outside of onCreate
public void openAdminLogin(View view) {
	// Switch to Admin Login Activity
	Intent intent = new Intent(user_Switch.this, LoginRegisterActivity.class);
	startActivity(intent);
}
}