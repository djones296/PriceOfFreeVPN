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

public class ImproveDataPrivacyActivity extends AppCompatActivity {
    public Button backButton;
    public Intent goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_data_privacy);

        WebView webView = findViewById(R.id.improvePrivacyWebView);
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            InputStream stream = assetManager.open("Ways to improve data privacy.htm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) !=null){
                total.append(line).append("\n");
                }//while
                webView.loadDataWithBaseURL(null, total.toString(), "text/html",
                        "UTF-8", null);
                }//try
                catch (Exception e){
            e.printStackTrace();
                }//catch

        backButton = findViewById(R.id.manualAddBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(ImproveDataPrivacyActivity.this, DataPrivacyTutorialsActivity.class);
                startActivity(goBack);
            }//onClick for goBack button
        });//onClickListener for button
    }//onCreate
}
