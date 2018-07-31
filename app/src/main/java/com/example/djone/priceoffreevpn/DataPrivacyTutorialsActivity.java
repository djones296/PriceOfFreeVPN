package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DataPrivacyTutorialsActivity extends AppCompatActivity {
    public Button backButton, improvePrivacyButton;
    public Intent goBack, goImprovePrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_privacy_tutorials);

        improvePrivacyButton = findViewById(R.id.improveDataPrivacy);
        improvePrivacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goImprovePrivacy = new Intent(DataPrivacyTutorialsActivity.this, ImproveDataPrivacyActivity.class);
                startActivity(goImprovePrivacy);
            }//onClick for goImprovePrivacy button
        });//onClickListener for goImprovePrivacy button

        backButton = findViewById(R.id.manualAddBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(DataPrivacyTutorialsActivity.this, TutorialsActivity.class);
                startActivity(goBack);
            }//onClick for backButton
        });//onClickListener for backButton
    }//onCreate

}//DataPrivacyTutorialsActivity
