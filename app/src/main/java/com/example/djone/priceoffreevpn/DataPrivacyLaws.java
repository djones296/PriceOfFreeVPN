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

public class DataPrivacyLaws extends AppCompatActivity {
    public Button backButton;
    public Intent goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_privacy_laws);
        dataPrivLawWebView();
        dataPrivBack();
    }

    public void dataPrivLawWebView(){
        WebView webView = findViewById(R.id.dataPrivWebView);
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream stream = assetManager.open("Data Privacy Laws.htm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = reader.readLine()) !=null){
                total.append(line).append("\n");
            }
            webView.loadDataWithBaseURL(null, total.toString(), "text/html",
                    "UTF-8", null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }//dataPrivWebView

    public void dataPrivBack(){
        backButton = findViewById(R.id.dataPrivLawBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(DataPrivacyLaws.this, DataPrivacyTutorialsActivity.class);
                startActivity(goBack);
            }
        });
    }//dataPrivBack
}//DataPrivacylaws
