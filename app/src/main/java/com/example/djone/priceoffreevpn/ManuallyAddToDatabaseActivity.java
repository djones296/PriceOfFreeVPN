package com.example.djone.priceoffreevpn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManuallyAddToDatabaseActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    public Button backButton, addToDatabaseButton;
    public Intent goback;
    EditText editIp, editProcess, editSize, editInputOutput, editDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_add_to_database);
        myDb = new DatabaseHelper(this);

        editIp = findViewById(R.id.edit_Ip);
        editProcess = findViewById(R.id.edit_Process);
        editSize = findViewById(R.id.edit_Size);
        editInputOutput = findViewById(R.id.edit_Input_Output);
        editDateTime = findViewById(R.id.edit_Date_Time);
        addToDatabaseButton = findViewById(R.id.addToDatabase);

        backButton = findViewById(R.id.manualAddBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goback = new Intent(ManuallyAddToDatabaseActivity.this, OptionsActivity.class);
                startActivity(goback);
            }
        });
    }//onCreate

    public void addToDatabase(){
        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();


                boolean isInserted = myDb.manuallyInsertData(editIp.getText().toString(),
                        editProcess.getText().toString(),
                        editSize.getText().toString(),
                        editInputOutput.getText().toString(),
                        editDateTime.getText().toString());
                if (isInserted){
                    CharSequence toastText = "Successfully added to Database";
                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }else{
                    CharSequence toastText = "Failed to add to Database";
                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }
            }//onClick
        });
    }
}
