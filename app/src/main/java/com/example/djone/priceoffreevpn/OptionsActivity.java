package com.example.djone.priceoffreevpn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity{
    DatabaseHelper db;
    public Button backButton, emptyDatabaseButton, manuallyAddDatabase;
    public Intent goBack, goManuallyAdd;
    private static final String TAG = "TryDeleteFromDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);
        db = new DatabaseHelper(this);

        backButton = findViewById(R.id.settingsBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(goBack);
            }//onClick for goBack button
        });//onClickListener for goBack button

        manuallyAddDatabase = findViewById(R.id.ManualAddPackets);
        manuallyAddDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goManuallyAdd = new Intent(OptionsActivity.this, ManuallyAddToDatabaseActivity.class);
                startActivity(goManuallyAdd);
            }//onClick for manuallyAddDatabase button
        });//onClickListener for manuallyAddDatabase button

        emptyDatabaseButton = findViewById(R.id.clearDatabaseButton);
        emptyDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Attempting to delete from the database");
                db.clearDatabase();
                Context context = getApplicationContext();

                CharSequence toastText = "The Database has been cleared";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
            }
        });
    }//onCreate
}//OptionsActivity
