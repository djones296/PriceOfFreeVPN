package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CookiesTutorialActivity extends AppCompatActivity {
    Button backButton, whatAreCookiesButton, sessionCookiesButton, permanentCookiesButton,
    thirdPartyCookiesButton, howToControlCookies;

    Intent goBack, goWhatAre, goSession, goPermanent, goThirdParty, goHowToControlCookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookies_tutorial);

        whatAreCookies();
        sessionCookies();
        permanentCookies();
        thirdPartyCookies();
        controlCookies();
        goBack();
    }

    public void goBack(){
        backButton = findViewById(R.id.cookiesBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(CookiesTutorialActivity.this, TutorialsActivity.class);
                startActivity(goBack);
            }
        });
    }

    public void whatAreCookies(){
        whatAreCookiesButton = findViewById(R.id.WhatAreCookies);
        whatAreCookiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWhatAre = new Intent(CookiesTutorialActivity.this, WhatAreCookies.class);
                startActivity(goWhatAre);
            }
        });
    }

    public void sessionCookies(){
        sessionCookiesButton = findViewById(R.id.sessionCookies);
        sessionCookiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSession = new Intent(CookiesTutorialActivity.this, SessionCookies.class);
                startActivity(goSession);
            }
        });
    }

    public void permanentCookies(){
        permanentCookiesButton = findViewById(R.id.permanentCookies);
        permanentCookiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPermanent = new Intent(CookiesTutorialActivity.this, PermanentCookies.class);
                startActivity(goPermanent);
            }
        });
    }

    public void thirdPartyCookies(){
        thirdPartyCookiesButton = findViewById(R.id.thirdPartyCookies);
        thirdPartyCookiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goThirdParty = new Intent(CookiesTutorialActivity.this, ThirdPartyCookies.class);
                startActivity(goThirdParty);
            }
        });
    }

    public void controlCookies(){
        howToControlCookies = findViewById(R.id.controlCookies);
        howToControlCookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHowToControlCookies = new Intent(CookiesTutorialActivity.this, ControlCookies.class);
                startActivity(goHowToControlCookies);
            }
        });
    }
}
