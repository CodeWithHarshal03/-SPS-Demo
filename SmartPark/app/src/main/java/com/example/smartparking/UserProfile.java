package com.example.smartparking;

public class UserProfile {
private String userId;
private String name;
private String mobileNumber;
private String qrImageUrl;

public UserProfile() {
	// Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
}

public UserProfile(String userId, String name, String mobileNumber, String qrImageUrl) {
	this.userId = userId;
	this.name = name;
	this.mobileNumber = mobileNumber;
	this.qrImageUrl = qrImageUrl;
}

public String getUserId() {
	return userId;
}

public String getName() {
	return name;
}

public String getMobileNumber() {
	return mobileNumber;
}

public String getQrImageUrl() {
	return qrImageUrl;
}
}
