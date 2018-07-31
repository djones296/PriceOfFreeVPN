package com.example.djone.priceoffreevpn;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "Packet Database";
    private static final String PACKETTABLE = "Packets";
    private static final String PACKETID = "PacketID";
    private final String IPADDRESS = "IP";
    private final String PROCESS = "Process";
    private final String PACKETSIZE ="PacketSize";
    private final String INPUTOUTPUT = "InputOutput";
    private final String DATETIME = "DateTime";
    public final String VIEWPACKETS = "ViewPackets";
    private final String TAG = "DeletingFromDatabase";


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }//DatabaseHelperConstructor

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PACKETTABLE + "(" +
        PACKETID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
        IPADDRESS + "VARCHAR NOT NULL, " +
        PROCESS + "STRING, " +
        PACKETSIZE + "INTEGER," +
        INPUTOUTPUT + "STRING," +
        DATETIME + "DATETIME);");//Create the Packets table for the database

    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PACKETTABLE);
        onCreate(db);
    }//onUpgrade

    public void clearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(PACKETTABLE, null, null);
            Log.i(TAG, "Deleting from the database");
        }//try
        catch (Exception e){
            e.printStackTrace();
        }//catch
        db.close();
    }

    public boolean manuallyInsertData(String ip, String process, String size, String inputOutput, String dateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(IPADDRESS, ip);
        contentValue.put(PROCESS, process);
        contentValue.put(PACKETSIZE, size);
        contentValue.put(INPUTOUTPUT, inputOutput);
        contentValue.put(DATETIME, dateTime);
        long result = db.insert(PACKETTABLE, null, contentValue);
        if (result ==-1){
            return false;
        }//if
        else {
            return true;
        }//else
    }//manuallyInsertData
}
