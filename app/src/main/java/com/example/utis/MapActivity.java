package com.example.utis;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String source, destination, sourceTime, destinationTime;
    private LatLng sourceLatLng, destinationLatLng;  // These should be retrieved using Google Places API or hardcoded for testing
    private Button showBusPositionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get the source and destination from the Intent
        Intent intent = getIntent();
        source = intent.getStringExtra("source");
        destination = intent.getStringExtra("destination");
        sourceTime = intent.getStringExtra("source_time");
        destinationTime = intent.getStringExtra("destination_time");

        // Get the map fragment and initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        showBusPositionBtn = findViewById(R.id.showBusPositionBtn);
        showBusPositionBtn.setOnClickListener(v -> showBusPosition());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Example coordinates for source and destination (you should replace with actual lat/lng values or use Google Places API)
        sourceLatLng = new LatLng(12.934533, 79.137555);  // Example: Vellore
        destinationLatLng = new LatLng(12.907531, 79.132435);  // Example: Puthur

        // Add markers for source and destination
        mMap.addMarker(new MarkerOptions().position(sourceLatLng).title("Source: " + source));
        mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination: " + destination));

        // Draw route between source and destination
        mMap.addPolyline(new PolylineOptions().add(sourceLatLng, destinationLatLng).width(5).color(android.graphics.Color.BLUE));

        // Move the camera to source
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sourceLatLng, 12));
    }

    private void showBusPosition() {
        // Calculate the bus's current position based on time
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }
        LocalTime currentTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTime = LocalTime.now();
        }
        LocalTime srcTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            srcTime = LocalTime.parse(sourceTime, formatter);
        }
        LocalTime dstTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dstTime = LocalTime.parse(destinationTime, formatter);
        }

        // Find the ratio of the time between source and destination
        long totalTime = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            totalTime = java.time.Duration.between(srcTime, dstTime).toMinutes();
        }
        long elapsed = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            elapsed = java.time.Duration.between(srcTime, currentTime).toMinutes();
        }
        float ratio = (float) elapsed / totalTime;

        // Calculate bus's approximate position
        double latDiff = destinationLatLng.latitude - sourceLatLng.latitude;
        double lngDiff = destinationLatLng.longitude - sourceLatLng.longitude;

        LatLng busPosition = new LatLng(sourceLatLng.latitude + (latDiff * ratio), sourceLatLng.longitude + (lngDiff * ratio));

        // Add marker for bus position
        mMap.addMarker(new MarkerOptions().position(busPosition).title("Approx Bus Position"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(busPosition, 14));
    }
}
