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

public class ThirdPartyCookies extends AppCompatActivity {
    Button backButton;
    Intent goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_cookies);
        thirdPartyWebView();
        goBack();
    }

    public void thirdPartyWebView(){
        WebView webView = findViewById(R.id.thirdPartyWebView);

        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream stream = assetManager.open("Third Party Cookies.htm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                total.append(line).append("\n");
            }//while
            webView.loadDataWithBaseURL(null, total.toString(), "text/html",
                    "UTF-8", null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goBack(){
        backButton = findViewById(R.id.thirdPartyBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(ThirdPartyCookies.this, CookiesTutorialActivity.class);
                startActivity(goBack);
            }
        });
    }
}
