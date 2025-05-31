package com.example.smartparking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Adminstratoravailable extends AppCompatActivity implements OnMapReadyCallback {

private FusedLocationProviderClient fusedLocationProviderClient;
private final int LOCATION_REQUEST_CODE = 101;
private DatabaseReference databaseReference;
private GoogleMap mMap;
private Marker adminMarker;
private LatLng currentLatLng;
private String adminName = "Administrator";

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_adminstratoravailable);
	
	// Initialize FusedLocationProviderClient
	fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
	// Initialize Firebase Database reference
	databaseReference = FirebaseDatabase.getInstance().getReference("admin_location");
	
	Button updateLocationButton = findViewById(R.id.updateLocationButton);
	updateLocationButton.setOnClickListener(v -> updateAdminLocation());

	// Obtain the SupportMapFragment and get notified when the map is ready to be used.
	SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			                                                      .findFragmentById(R.id.map);
	if (mapFragment != null) {
		mapFragment.getMapAsync(this);
	} else {
		Log.e("MapError", "SupportMapFragment is null");
	}
}



@Override
public void onMapReady(@NonNull GoogleMap googleMap) {
	mMap = googleMap;
	
	// Enable user location if permission is granted
	if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
		mMap.setMyLocationEnabled(true);
		getCurrentLocation();
	} else {
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
	}
	
	// Set a default location (can be customized)
	LatLng defaultLocation = new LatLng(-34, 151);
	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
	
	// Set a listener for marker drag events
	mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
		@Override
		public void onMarkerDragStart(Marker marker) {
			// No action needed here
		}
		
		@Override
		public void onMarkerDrag(Marker marker) {
			// No action needed here
		}
		
		@Override
		public void onMarkerDragEnd(Marker marker) {
			// Update the location in Firebase when the marker is dragged
			currentLatLng = marker.getPosition();
			updateLocationInFirebase(currentLatLng.latitude, currentLatLng.longitude);
		}
	});
	
	// Set a listener for marker click events
	mMap.setOnMarkerClickListener(marker -> {
		Toast.makeText(Adminstratoravailable.this, adminName, Toast.LENGTH_SHORT).show();
		return false; // return false to let default behavior occur (e.g., centering the map on the marker)
	});
}

private void getCurrentLocation() {
	if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
		return;
	}
	fusedLocationProviderClient.getLastLocation()
			.addOnSuccessListener(location -> {
				if (location != null) {
					currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
					updateMapWithLocation(currentLatLng);
					updateLocationInFirebase(currentLatLng.latitude, currentLatLng.longitude);
				} else {
					Log.e("LocationError", "Location is null");
				}
			});
}

private void updateMapWithLocation(LatLng location) {
	if (mMap == null) {
		return;
	}
	
	if (adminMarker == null) {
		// Create a new marker if it doesn't exist
		adminMarker = mMap.addMarker(new MarkerOptions().position(location)
				                             .title("Administrator Location").draggable(true));
	} else {
		// Update the existing marker's position
		adminMarker.setPosition(location);
	}
	
	// Move the camera to the new location
	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
}


private void updateAdminLocation() {
	if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
		return;
	}
	fusedLocationProviderClient.getLastLocation()
			.addOnSuccessListener(location -> {
				if (location != null) {
					currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
					updateMapWithLocation(currentLatLng);
					updateLocationInFirebase(currentLatLng.latitude, currentLatLng.longitude);
					Toast.makeText(Adminstratoravailable.this, "Location Updated!", Toast.LENGTH_SHORT).show();
				} else {
					Log.e("LocationError", "Location is null");
				}
			});
}

private void updateLocationInFirebase(double latitude, double longitude) {
	databaseReference.child("latitude").setValue(latitude);
	databaseReference.child("longitude").setValue(longitude);
}

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	if (requestCode == LOCATION_REQUEST_CODE) {
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			getCurrentLocation();
		} else {
			Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
		}
	}
}

public void onUpdateLocationClick(View view) {
}
}
