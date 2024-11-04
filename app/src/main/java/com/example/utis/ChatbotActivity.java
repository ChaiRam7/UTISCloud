package com.example.utis;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class ChatbotActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());  // To open links within the WebView
        webView.getSettings().setJavaScriptEnabled(true);

        // Load a chatbot web interface, you can host it or use existing chatbot platforms
        webView.loadUrl("https://bus-mate.zapier.app");
    }
}
