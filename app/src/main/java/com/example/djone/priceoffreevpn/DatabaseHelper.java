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
    private final String STARTIPADDRESS = "SourceIP";
    private final String DESTIP = "DestinationIP";
    private final String PROCESS = "Process";
    private final String PACKETSIZE ="PacketSize";
    private final String DATETIME = "DateTime";
    public final String VIEWPACKETS = "ViewPackets";
    private final String TAG = "DeletingFromDatabase";
    public int packetId = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
    }//DatabaseHelperConstructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PACKETTABLE + "(" +
        PACKETID + "INTEGER PRIMARY KEY, " +
        STARTIPADDRESS + "VARCHAR , " +
        DESTIP + "VARCHAR , " +
        PROCESS + "STRING, " +
        PACKETSIZE + "INTEGER," +
        DATETIME + "DATETIME)");
        Log.i("Database", "Database     Built");
    }//onCreate creates the packets table to be used for the database

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PACKETTABLE);
        onCreate(db);
    }//onUpgrade

    public void recyclerView(SQLiteDatabase db){
        db.execSQL("CREATE VIEW recyclerView AS " +
                "SELECT packetID, sourceIP, DestinationIP" +
                "FROM Packets");
    }//not yet implemented

    public synchronized void addToDatabase(String source, String destination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PACKETID, packetId);
        contentValues.put(STARTIPADDRESS, source);
        contentValues.put(DESTIP, destination);
        db.insert(PACKETTABLE, null, contentValues);
        db.close();
    }//addToDatabase

    public void clearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(PACKETTABLE, null, null);
            Log.i(TAG, "Deleting all data from the database");
        }//try
        catch (Exception e){
            e.printStackTrace();
        }//catch
        db.close();
    }//clearDatabase
}//DatabaseHelper
