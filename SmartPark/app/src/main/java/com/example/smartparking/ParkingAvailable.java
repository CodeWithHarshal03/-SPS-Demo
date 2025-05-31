package com.example.smartparking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParkingAvailable extends AppCompatActivity implements OnMapReadyCallback {

private GoogleMap mMap;
private FusedLocationProviderClient fusedLocationProviderClient;
private final int LOCATION_REQUEST_CODE = 100;
private DatabaseReference adminLocationRef; // Firebase Database reference for admin location

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_parking_available);
	
	// Initialize the FusedLocationProviderClient
	fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
	
	// Obtain the SupportMapFragment and get notified when the map is ready to be used.
	SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			                                                      .findFragmentById(R.id.map);
	if (mapFragment != null) {
		mapFragment.getMapAsync(this);
	} else {
		Log.e("MapError", "SupportMapFragment is null");
	}
	
	Button navigateButton = findViewById(R.id.navigateButton);
	navigateButton.setOnClickListener(v -> {
		Intent intent = new Intent(ParkingAvailable.this,bookslots.class);
		startActivity(intent);
	});
	
	// Initialize Firebase reference
	adminLocationRef = FirebaseDatabase.getInstance().getReference("admin_location");
}

@Override
public void onMapReady(@NonNull GoogleMap googleMap) {
	if (googleMap == null) {
		Log.e("MapError", "GoogleMap is null");
		return;
	}
	
	mMap = googleMap;
	
	// Enable user location and add parking slot markers
	enableUserLocation();
	addParkingSlotMarkers();
	
	// Set a default camera position
	LatLng defaultLocation = new LatLng(-34, 151);
	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
}

private void enableUserLocation() {
	if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
			    == PackageManager.PERMISSION_GRANTED) {
		mMap.setMyLocationEnabled(true);
		getCurrentLocation();
	} else {
		// Request location permission
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
	}
}

private void getCurrentLocation() {
	if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
		return;
	}
	fusedLocationProviderClient.getLastLocation()
			.addOnSuccessListener(this, new OnSuccessListener<Location>() {
				@Override
				public void onSuccess(Location location) {
					if (location != null) {
						// Get current location's latitude and longitude
						LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
						// Add a marker for the current location
						mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
						// Move the camera to the current location
						mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
					} else {
						Log.e("LocationError", "Current location is null");
					}
				}
			});
}

private void addParkingSlotMarkers() {
	if (mMap == null) {
		Log.e("MapError", "GoogleMap is null in addParkingSlotMarkers");
		return;
	}
	
	// Listen for admin location updates from Firebase
	adminLocationRef.addValueEventListener(new ValueEventListener() {
		@Override
		public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
			if (dataSnapshot.exists()) {
				double adminLat = dataSnapshot.child("latitude").getValue(Double.class);
				double adminLng = dataSnapshot.child("longitude").getValue(Double.class);
				
				// Clear existing markers
				mMap.clear();
				
				// Add a marker for the administrator's live location
				LatLng adminLocation = new LatLng(adminLat, adminLng);
				mMap.addMarker(new MarkerOptions().position(adminLocation).title("Admin Live Location"));
				
				// Move the camera to the admin's current location
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(adminLocation, 15));
			}
		}
		
		@Override
		public void onCancelled(@NonNull DatabaseError databaseError) {
			Log.e("DatabaseError", "Failed to read admin location: " + databaseError.getMessage());
		}
	});
	
	// Example parking slots
	LatLng slot1 = new LatLng(-34.0, 151.0);
	mMap.addMarker(new MarkerOptions().position(slot1).title("Available Parking Slot 1"));
	
	LatLng slot2 = new LatLng(-34.1, 151.1);
	mMap.addMarker(new MarkerOptions().position(slot2).title("Available Parking Slot 2"));
}

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	if (requestCode == LOCATION_REQUEST_CODE) {
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			// Permission granted, enable user location
			enableUserLocation();
		} else {
			Log.e("PermissionError", "Location permission denied");
		}
	}
}
}
