package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartparking.R;
import com.google.android.material.textfield.TextInputEditText;

public class ParkingBookingActivity extends AppCompatActivity {

private TextInputEditText vehicleNumberEditText;
private TextInputEditText hoursEditText;
private TextInputEditText nameEditText;
private TextInputEditText phoneEditText;
private TextInputEditText emailEditText;
private TextView chargeTextView;
private Button calculateChargeButton;
private Button bookSlotButton;
private Button MakeaPayment;
private RadioGroup vehicleTypeGroup;
private String slotId; // Added for handling the slot ID

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(com.example.smartparking.R.layout.activity_parking_booking); // Use the updated layout
	
	// Initialize the form fields
	nameEditText = findViewById(R.id.nameEditText);
	phoneEditText = findViewById(R.id.phoneEditText);
	vehicleNumberEditText = findViewById(R.id.vehicleNumberEditText);
	hoursEditText = findViewById(R.id.hoursEditText);
	chargeTextView = findViewById(R.id.chargeTextView);
	vehicleTypeGroup = findViewById(R.id.vehicleTypeGroup);
	calculateChargeButton = findViewById(R.id.calculateChargeButton);
	bookSlotButton = findViewById(R.id.bookSlotButton);
	MakeaPayment = findViewById(R.id.MakeApaymentButton);
	// Get the slot ID from the intent (if available)
	slotId = getIntent().getStringExtra("SLOT_ID");
	
	calculateChargeButton.setOnClickListener(v -> calculateCharge());
	bookSlotButton.setOnClickListener(v -> bookSlot());
	MakeaPayment.setOnClickListener(v -> MakePay());
}

private void calculateCharge() {
	String hoursStr = hoursEditText.getText().toString();
	double chargePerHour;
	
	if (vehicleTypeGroup.getCheckedRadioButtonId() == R.id.radioBike) {
		chargePerHour = 10.0;
	} else if (vehicleTypeGroup.getCheckedRadioButtonId() == R.id.radioCar) {
		chargePerHour = 20.0;
	} else {
		chargePerHour = 5.0; // Default charge
	}
	
	if (!hoursStr.isEmpty()) {
		int hours = Integer.parseInt(hoursStr);
		double charge = hours * chargePerHour;
		chargeTextView.setText(String.format("Charge: \u20B9%.2f", charge));
	} else {
		chargeTextView.setText("Charge: \u20B90.00");
		Toast.makeText(this, "Please enter the number of hours", Toast.LENGTH_SHORT).show();
	}
}

private void bookSlot() {
	// Get form field values
	String name = nameEditText.getText().toString();
	String phone = phoneEditText.getText().toString();
	String vehicleNumber = vehicleNumberEditText.getText().toString();
	String hours = hoursEditText.getText().toString();
	
	// Validate form inputs
	if (name.isEmpty()) {
		Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
		return;
	}
	if (phone.isEmpty()) {
		Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
		return;
	}
	
	if (vehicleNumber.isEmpty()) {
		Toast.makeText(this, "Please enter your vehicle number", Toast.LENGTH_SHORT).show();
		return;
	}
	if (hours.isEmpty()) {
		Toast.makeText(this, "Please enter the number of hours", Toast.LENGTH_SHORT).show();
		return;
	}
	
	// Assume booking is successful
	Toast.makeText(this, "Slot booked for vehicle: " + vehicleNumber, Toast.LENGTH_SHORT).show();
	// Add further logic to manage booking (e.g., storing in a database)
	// Show the ticket
	Intent intent = new Intent(this, TicketActivity.class);
	intent.putExtra("SLOT_ID", slotId);
	intent.putExtra("NAME", name);
	intent.putExtra("PHONE", phone);
	intent.putExtra("VEHICLE_NUMBER", vehicleNumber);
	intent.putExtra("VEHICLE_TYPE", vehicleTypeGroup.getCheckedRadioButtonId());
	intent.putExtra("CHARGES", chargeTextView.getText().toString());
	intent.putExtra("HOURS", hoursEditText.getText().toString());
	startActivity(intent);
}

private void MakePay() {
	// Implement your logic to check available slots
	Intent intent = new Intent(this, MakeaPayment.class);
	intent.putExtra("SLOT_ID", slotId);
	intent.putExtra("NAME", nameEditText.getText().toString());
	intent.putExtra("PHONE", phoneEditText.getText().toString());
	intent.putExtra("VEHICLE_NUMBER", vehicleNumberEditText.getText().toString());
	intent.putExtra("VEHICLE_TYPE", vehicleTypeGroup.getCheckedRadioButtonId() == R.id.radioBike ? "Bike" : "Car");
	intent.putExtra("CHARGES", chargeTextView.getText().toString());
	intent.putExtra("HOURS", hoursEditText.getText().toString());
	
	startActivity(intent);}
}
