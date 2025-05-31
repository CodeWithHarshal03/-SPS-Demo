package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartparking.R;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        findViewById(R.id.viewParkingSlotButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Parking Slot Activity
                Intent intent = new Intent(Dashboard.this, ParkingAvailable.class);
                startActivity(intent);
            }
        });
        
        findViewById(R.id.profilePageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Profile Page Activity
                Intent intent = new Intent(Dashboard.this, profileuser.class);
                startActivity(intent);
            }
        });
        
        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout action
                finish();
            }
        });
    }
    }
