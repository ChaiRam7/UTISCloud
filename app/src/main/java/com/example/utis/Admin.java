package com.example.utis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
public class Admin extends AppCompatActivity {
    ImageView backHomeImageView;
    WebView webView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        webView = findViewById(R.id.events);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force the WebView to enable horizontal scroll and disable vertical scroll
        webView.setHorizontalScrollBarEnabled(true);   // Enable horizontal scrollbar
        webView.setVerticalScrollBarEnabled(false);    // Disable vertical scrollbar

        // Load your webpage
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://10times.com/vellore-in");

        // Apply the custom JavaScript to make the content scroll horizontally
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Inject CSS to make content scroll horizontally
                webView.loadUrl("javascript:(function() {" +
                        "document.body.style['writing-mode'] = 'horizontal-tb';" +
                        "document.body.style['overflow-x'] = 'scroll';" +
                        "document.body.style['overflow-y'] = 'hidden';" +
                        "document.body.style['white-space'] = 'nowrap';" +
                        "})()");
            }
        });

        backHomeImageView = findViewById(R.id.backhome);
        backHomeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
