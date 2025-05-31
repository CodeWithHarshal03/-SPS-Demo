package com.example.smartparking;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartparking.R;

public class profileuser extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	EdgeToEdge.enable(this);
	setContentView(com.example.smartparking.R.layout.activity_profileuser);
	ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
		Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
		v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
		return insets;
	});
}
}