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

public class WhatIsAVpn extends AppCompatActivity {
    Button backButton;
    Intent goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_avpn);
        whatIsVpnWebView();
        goBack();
    }//onCreate

    public void goBack(){
        backButton.findViewById(R.id.whatIsVpnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(WhatIsAVpn.this, VpnTutorials.class);
                startActivity(goBack);
            }//onClick
        });//setOnClickListenet
    }//goBack

    public void whatIsVpnWebView(){
        WebView webView = findViewById(R.id.whatIsVpnWeb);

        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream stream = assetManager.open("What is a Vpn.htm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                total.append(line).append("\n");
            }//while
            webView.loadDataWithBaseURL(null, total.toString(), "text/html",
                    "UTF-8", null);
        }//try
        catch (Exception e){
            e.printStackTrace();
        }//catch
    }//whatIsVpnWebView
}//WhatIsAVpn
