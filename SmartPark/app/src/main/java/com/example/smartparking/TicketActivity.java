package com.example.smartparking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartparking.R;

public class TicketActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(com.example.smartparking.R.layout.activity_ticket);
	
	// Get the details from the intent
	String slotId = getIntent().getStringExtra("SLOT_ID");
	String name = getIntent().getStringExtra("NAME");
	String phone = getIntent().getStringExtra("PHONE");
	String vehicleNumber = getIntent().getStringExtra("VEHICLE_NUMBER");
	String vehicleType = getIntent().getStringExtra("VEHICLE_TYPE");
	String Charges = getIntent().getStringExtra("CHARGES");
	String hours = getIntent().getStringExtra("HOURS");
	String transactionId = getIntent().getStringExtra("TRANSACTION_ID"); // ✅ Transaction ID receive from MakeaPayment
	
	// Display the ticket details
	TextView slotTextView = findViewById(R.id.slotTextView);
	TextView contactInfoTextView = findViewById(R.id.nameTextView8);
	TextView phoneNumberTextView = findViewById(R.id.phoneNumberTextView8);
	TextView vehicleNumberTextView = findViewById(R.id.vehicleNumberTextView);
	TextView ChargesTextView = findViewById(R.id.ChargesTextView);
	TextView vechicalTextView = findViewById(R.id.vechialTextView);
	TextView TransactionId = findViewById(R.id.TraIdTextView);
	TextView hoursEditText = findViewById(R.id.hoursEditText);
	
	slotTextView.setText("Slot: " + slotId);
	contactInfoTextView.setText("Name: " + name );
	phoneNumberTextView.setText("\nPhone: " + phone);
	vehicleNumberTextView.setText("Vehicle Number: " + vehicleNumber);
	ChargesTextView.setText("Charges: " + Charges);
	hoursEditText.setText("Hours: " + hours);
	vechicalTextView.setText("Vehicle Type: " + vehicleType);
	TransactionId.setText("Transaction ID: " + transactionId);
	
}
}
