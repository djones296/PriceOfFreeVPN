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

public class ControlCookies extends AppCompatActivity {
    Button backButton;
    Intent goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_cookies);
        controlCookiesWebView();
        goBack();
    }

    public void controlCookiesWebView(){
        WebView webView = findViewById(R.id.controlCookiesWebView);

        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream stream = assetManager.open("Control Cookies.htm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = reader.readLine()) !=null){
                total.append(line).append("\n");
            }

            webView.loadDataWithBaseURL(null, total.toString(), "text/html",
                    "UTF-8", null);
        }//try
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goBack(){
        backButton.findViewById(R.id.controlCookiesBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(ControlCookies.this, CookiesTutorialActivity.class);
            }
        });
    }
}
