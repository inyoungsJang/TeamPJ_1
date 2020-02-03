package com.example.teampj_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class BluetoothDB extends AppCompatActivity {
    SQLiteDatabase sqlDB;
    BluetoothUserDB bluetoothUserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_db);

           bluetoothUserDB = new BluetoothUserDB(getApplicationContext());
    }
}

class BluetoothUserDB extends SQLiteOpenHelper {
    public BluetoothUserDB(@Nullable Context context) {
        super(context, "bluetoothUserDB", null, 1); //DB생성
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bluetoothUserTBL (id TEXT(20) NOT NULL PRIMARY KEY, password TEXT(20) NOT NULL, name TEXT(50) NOT NULL, rfid TEXT(50), email TEXT(10) NOT NULL);"); //bluetoothUserTBL Table생성
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {        }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
class bluetoothUserDB extends SQLiteOpenHelper {
    public bluetoothUserDB(@Nullable Context context) {
        super(context, "bluetoothUserDB", null, 1); //DB생성
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bluetoothUserTBL (id TEXT(20) NOT NULL PRIMARY KEY, password TEXT(20) NOT NULL, name TEXT(50) NOT NULL, rfid TEXT(50));"); //bluetoothUserTBL Table생성
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }
}