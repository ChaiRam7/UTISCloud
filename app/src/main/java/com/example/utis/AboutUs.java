package com.example.utis;

import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {

    private WebView aboutUsWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Initialize the WebView and ProgressBar
        aboutUsWebView = findViewById(R.id.about_us_webview);
        progressBar = findViewById(R.id.progress_bar);

        // Set WebViewClient to handle the loading inside the WebView
        aboutUsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("WebView", "Loading URL: " + url);
                view.loadUrl(url); // Load the URL in the same WebView
                return true; // Returning true means we're handling the URL loading
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("WebView", "Error: " + description + ", URL: " + failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("WebView", "SSL Error: " + error);
                handler.proceed();  // Ignore SSL certificate errors for now
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "Page finished loading: " + url);
                progressBar.setVisibility(View.GONE);  // Hide progress bar

                if (url.equals("https://www.tnstc.in/OTRSOnline/")) {
                    // Disable scrolling for the first webpage
                    aboutUsWebView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return event.getAction() == MotionEvent.ACTION_MOVE; // Block scrolling
                        }
                    });
                } else {
                    // Enable scrolling for other pages
                    aboutUsWebView.setOnTouchListener(null);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);  // Show progress bar
            }
        });

        // Enable JavaScript
        WebSettings webSettings = aboutUsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Load the URL of the first page
        aboutUsWebView.loadUrl("https://www.tnstc.in/OTRSOnline/");

        // Disable horizontal scrolling
        aboutUsWebView.setHorizontalScrollBarEnabled(false);
        aboutUsWebView.setVerticalScrollBarEnabled(true);
    }
}
