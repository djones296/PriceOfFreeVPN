package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TutorialsActivity extends AppCompatActivity {
    public Button backButton, dataPrivacyButton;
    public Intent goMain, goDataPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

        backButton = findViewById(R.id.tutorialBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goMain = new Intent(TutorialsActivity.this, MainActivity.class);
                startActivity(goMain);
            }//onClick for backButton
        });//onClickListener for backButton

        dataPrivacyButton = findViewById(R.id.dataPrivacyTutorialsButton);
        dataPrivacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDataPrivacy = new Intent(TutorialsActivity.this, DataPrivacyTutorialsActivity.class);
                startActivity(goDataPrivacy);
            }//onClick for dataPrivacyButton
        });//onClickListener for dataPrivacyButton
    }//onCreate
}//TutorialsActivity
