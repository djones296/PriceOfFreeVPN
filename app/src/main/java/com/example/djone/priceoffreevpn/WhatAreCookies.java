package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WhatAreCookies extends AppCompatActivity {
    Button goBackButton;
    Intent goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_are_cookies);
        whatAreCookies();
        goBack();
    }

    public void whatAreCookies(){
        WebView webView = findViewById(R.id.whatAreCookiesWebView);
        try {
            AssetManager assetManager =getApplicationContext().getAssets();
            InputStream stream = assetManager.open("What Are Cookies.htm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = reader.readLine()) !=null){
                total.append(line).append("\n");
            }//while

            webView.loadDataWithBaseURL(null, total.toString(), "text/htm",
                    "UTF-8", null);
        }//try
        catch (Exception e){
            e.printStackTrace();
        }//catch
    }//whatAreCookies

    public void goBack(){
        goBackButton = findViewById(R.id.whatAreCookiesBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(WhatAreCookies.this, CookiesTutorialActivity.class);
                startActivity(goBack);
            }
        });
    }
}
