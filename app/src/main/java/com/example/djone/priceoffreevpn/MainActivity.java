    package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.net.VpnService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
        DatabaseHelper myDb;

    public Button vpnButton, settingsButton, tutorialsButton, stopVpnButton;
    public Intent intentVPN, goSettings, goTutorials, startVpnIntent, intentStopVpn;
    public boolean vpnAllowed = false;//defaults to false in case user does not allow vpn services
    public boolean vpnRunning = false;//used in conjunction with if function so actions only take place when the vpn is running
    //private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mLiveFeedText = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        intentVPN = VpnService.prepare(this);
        if (intentVPN != null){
            startActivityForResult(intentVPN, 0);
        }//if
        else{
            onActivityResult(0, RESULT_OK, null);
        }//else

        vpnButton = findViewById(R.id.StartVpnButton);
        vpnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vpnAllowed) {
                    startVpnIntent = new Intent(MainActivity.this, MyVpnService.class);
                    startService(startVpnIntent);
                    vpnRunning = true;
                }//if function so vpn only attempts to run if vpn services have been enabled
            }//onClick for startVpnButton
        });//setOnClickListener for startVpnButton

        stopVpnButton = findViewById(R.id.stopVpnButton);
        stopVpnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    intentStopVpn = new Intent(MainActivity.this, MyVpnService.class);
                    stopService(startVpnIntent);
            }
        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSettings = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(goSettings);
            }//onClick for settingsButton
        });//setOnClickListener for settingsButton

        tutorialsButton = findViewById(R.id.tutorialsButton);
        tutorialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTutorials = new Intent(MainActivity.this, TutorialsActivity.class);
                startActivity(goTutorials);
            }//onClick for tutorialsButton
        });//setOnClickListener for tutorialsButton

        initRecyclerViewContents();
    }//onCreate

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            vpnAllowed = true;
        }
    }

    private void initRecyclerViewContents(){
        initRecyclerView();
            mLiveFeedText.add("Random test message");
//            mImages.add("@mipmap/ic_launcher");
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.liveFeed);
        LiveFeedAdapter adapter = new LiveFeedAdapter(mLiveFeedText,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
