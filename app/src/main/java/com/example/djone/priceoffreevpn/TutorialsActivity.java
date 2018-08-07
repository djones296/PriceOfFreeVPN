package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TutorialsActivity extends AppCompatActivity {
    public Button backButton, dataPrivacyButton, cookiesButton;
    public Intent goMain, goDataPrivacy, goCookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);
        goBack();
        goDataPrivacy();
        goCookies();

    }//onCreate

    public void goBack(){
        backButton = findViewById(R.id.tutorialBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goMain = new Intent(TutorialsActivity.this, MainActivity.class);
                startActivity(goMain);
            }//onClick for backButton
        });//onClickListener for backButton
    }
    public void goCookies(){
        cookiesButton = findViewById(R.id.cookiesTutorialButton);
        cookiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCookies = new Intent(TutorialsActivity.this, CookiesTutorialActivity.class);
                startActivity(goCookies);
            }
        });
    }
    public void goDataPrivacy(){
        dataPrivacyButton = findViewById(R.id.dataPrivacyTutorialsButton);
        dataPrivacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDataPrivacy = new Intent(TutorialsActivity.this, DataPrivacyTutorialsActivity.class);
                startActivity(goDataPrivacy);
            }//onClick for dataPrivacyButton
        });//onClickListener for dataPrivacyButton
    }
}//TutorialsActivity
