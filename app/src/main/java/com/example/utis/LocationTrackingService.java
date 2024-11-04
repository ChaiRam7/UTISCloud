package com.example.utis;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class LocationTrackingService extends Service implements LocationListener {

    private static final String TAG = "LocationTrackingService";
    private LocationManager locationManager;
    private long updateInterval = 1000 * 60 * 10; // 10 minutes interval
    private long samePlaceInterval = 5 * 60 * 60 * 1000; // 5 hours interval for same location
    private Location lastRecordedLocation = null;
    private long lastRecordedTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        startLocationTracking();
    }

    private void startLocationTracking() {
        // Check if location permissions are granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            // Request location updates
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    updateInterval,
                    0,
                    this
            );
            Log.d(TAG, "Location tracking started.");
        } else {
            Log.e(TAG, "Location permissions are not granted.");
            stopSelf(); // Stop the service as we can't proceed without permissions
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            long currentTime = System.currentTimeMillis();

            // Check if this is the first location or if the location has changed
            if (lastRecordedLocation == null || location.distanceTo(lastRecordedLocation) > 10) { // 10 meters threshold for change
                // Record new location as it changed
                recordLocation(location, currentTime);
            } else if (currentTime - lastRecordedTime >= samePlaceInterval) {
                // Record location after 5 hours even if it didn't change
                recordLocation(location, currentTime);
            }
        }
    }

    private void recordLocation(Location location, long currentTime) {
        lastRecordedLocation = location;
        lastRecordedTime = currentTime;

        // Log or store the location here
        Log.d(TAG, "Location Recorded: " + location.getLatitude() + ", " + location.getLongitude() + " at " + currentTime);

        // Example: You could save this location to a database or file here
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Ensure tracking continues if the service is restarted
        startLocationTracking();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove updates when the service is stopped to avoid memory leaks
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
        Log.d(TAG, "Location tracking stopped.");
    }
}
