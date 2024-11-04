package com.example.utis;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class TimeActivity extends AppCompatActivity {
    private TextView temp;
    private ImageView backImg;
    private WebView webView; // Declare WebView here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        temp = findViewById(R.id.timely);
        backImg = findViewById(R.id.imageBackView);
        webView = findViewById(R.id.livetime);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        temp.setText(str != null ? str : "No message received");
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TimeActivity.this, HomeActivity.class);
                startActivity(backIntent);
                finish();  // Close the current activity
            }
        });

        // Set up the WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://chalo.com/app/nearest-bus-stop/live-map" + "");
}}