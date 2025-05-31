package com.example.smartparking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartparking.R;

public class bookslots extends AppCompatActivity {


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bookslots);
    
    // Initialize parking slots
    setupParkingSlotClickListener(R.id.slot_A0, "A0");
    setupParkingSlotClickListener(R.id.slot_A1, "A1");
    setupParkingSlotClickListener(R.id.slot_A2, "A2");
    setupParkingSlotClickListener(R.id.slot_A3, "A3");
    setupParkingSlotClickListener(R.id.slot_A4, "A4");
    setupParkingSlotClickListener(R.id.slot_A5, "A5");
    setupParkingSlotClickListener(R.id.slot_A6, "A6");
    setupParkingSlotClickListener(R.id.slot_A7, "A7");
}

// Method to set up click listener for parking slots
private void setupParkingSlotClickListener(int layoutId, String slotId) {
    LinearLayout slotLayout = findViewById(layoutId);
    if (slotLayout == null) {
        Toast.makeText(this, "Slot layout not found for ID: " + layoutId, Toast.LENGTH_SHORT).show();
        return;
    }
    
    // Generate the button ID based on the slotId
    int buttonId = getResources().getIdentifier("slot_" + slotId + "_button", "id", getPackageName());
    
    Button bookButton = findViewById(buttonId); // Find the button directly
    if (bookButton == null) {
        Toast.makeText(this, "Book button not found for slot: " + slotId, Toast.LENGTH_SHORT).show();
        return;
    }
    
    // Set the click listener for the button
    bookButton.setOnClickListener(v -> {
        Log.d("BookSlots", "Book button clicked for slot: " + slotId);
        openBookingForm(slotId);
    });
}

// Logic to open the booking form
private void openBookingForm(String slotId) {
    Log.d("BookSlots", "Opening booking form for slot: " + slotId);
    
    Intent intent = new Intent(bookslots.this, ParkingBookingActivity.class);
    intent.putExtra("SLOT_ID", slotId);
    
    // Check if the intent can be handled
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
    } else {
        Toast.makeText(this, "No activity found to handle the intent", Toast.LENGTH_SHORT).show();
    }
}
}

