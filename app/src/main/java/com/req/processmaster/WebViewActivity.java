package com.req.processmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //getData
        Intent intent = getIntent();
        String webUrlPath = intent.getStringExtra("webUrlPath");
        File file = new File(webUrlPath);

        //WebView initialization
        WebView webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///"+file);


    }
}