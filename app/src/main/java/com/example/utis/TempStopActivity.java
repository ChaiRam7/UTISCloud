package com.example.utis;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TempStopActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempstop);

        webView = findViewById(R.id.tempmap);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Load the local HTML file with Leaflet map
        webView.loadUrl("https://tomato-faydra-74.tiiny.site");

        // Detect Location Button
        Button detectLocationButton = findViewById(R.id.btn_detect_location);
        detectLocationButton.setOnClickListener(v -> {
            // Call JavaScript function to get location and pin the bus stop
            webView.evaluateJavascript("getLocationAndPin();", null);
        });

        // Cancel Bus Stop Button
        Button cancelBusStopButton = findViewById(R.id.btn_cancel_bus_stop);
        cancelBusStopButton.setOnClickListener(v -> {
            // Call JavaScript function to cancel the bus stop
            webView.evaluateJavascript("cancelBusStop();", null);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
