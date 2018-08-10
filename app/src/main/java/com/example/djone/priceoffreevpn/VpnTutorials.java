package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VpnTutorials extends AppCompatActivity {

    Button whatIsVpn, backButton;
    Intent goWhatIsVpn, goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpn_tutorials);
        goWhatIsVpn();
        goBack();
    }

    public void goWhatIsVpn(){
        whatIsVpn.findViewById(R.id.whatIsVpn);
        whatIsVpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWhatIsVpn = new Intent(VpnTutorials.this, WhatIsAVpn.class);
                startActivity(goWhatIsVpn);
            }
        });
    }

    public void goBack(){
        backButton.findViewById(R.id.vpnTutorialBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(VpnTutorials.this, TutorialsActivity.class);
                startActivity(goBack);
            }
        });
    }
}
