package com.example.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class MakeaPayment extends AppCompatActivity implements PaymentResultListener {

private EditText etAmount;
private Button btnPay;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_makea_payment);
	
	etAmount = findViewById(R.id.etAmount);
	btnPay = findViewById(R.id.btnPay);
	
	// Preload Checkout
	Checkout.preload(getApplicationContext());
	
	btnPay.setOnClickListener(view -> startPayment());
}

private void startPayment() {
	String amountText = etAmount.getText().toString().trim();
	if (amountText.isEmpty()) {
		Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
		return;
	}
	
	int amount = Math.round(Float.parseFloat(amountText) * 100); // Convert to paise
	
	Checkout checkout = new Checkout();
	checkout.setKeyID("rzp_test_88OvKvCA3T7s4D"); // Replace with your Razorpay Key ID
	
	try {
		JSONObject options = new JSONObject();
		options.put("name", "Smart Parking");
		options.put("description", "Parking Payment");
		options.put("currency", "INR");
		options.put("amount", amount);
		options.put("image", "https://yourlogo.com/logo.png"); // Change to your logo
		
		// Prefill Details
		JSONObject prefill = new JSONObject();
		prefill.put("email", "test@example.com");
		prefill.put("contact", "9876543210");
		options.put("prefill", prefill);
		
		// ✅ Enable Payment Methods
		JSONObject methodOptions = new JSONObject();
		methodOptions.put("upi", true);  // UPI enabled
		methodOptions.put("card", true);
		methodOptions.put("netbanking", true);
		options.put("method", methodOptions);
		
		checkout.open(this, options);
	} catch (Exception e) {
		Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
	}
}

@Override
public void onPaymentSuccess(String razorpayPaymentID) {
	Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();
	Intent intent = new Intent(this, TicketActivity.class);
	intent.putExtra("SLOT_ID", getIntent().getStringExtra("SLOT_ID"));
	intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
	intent.putExtra("PHONE", getIntent().getStringExtra("PHONE"));
	intent.putExtra("VEHICLE_NUMBER", getIntent().getStringExtra("VEHICLE_NUMBER"));
	intent.putExtra("VEHICLE_TYPE", getIntent().getStringExtra("VEHICLE_TYPE"));
	intent.putExtra("CHARGES", getIntent().getStringExtra("CHARGES"));
	intent.putExtra("HOURS", getIntent().getStringExtra("HOURS"));
	intent.putExtra("TRANSACTION_ID", razorpayPaymentID); // ✅ Transaction ID send to Ticket
	
	startActivity(intent);
}

@Override
public void onPaymentError(int code, String response) {
	Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_LONG).show();
}
}
