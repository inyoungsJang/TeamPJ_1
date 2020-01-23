package com.example.teampj_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class BluetoothDB extends AppCompatActivity {
    SQLiteDatabase sqlDB;
    bluetoothUserDB bludtoothUserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_db);

        bludtoothUserDB = new bluetoothUserDB(getApplicationContext());

    }

    class bluetoothUserDB extends SQLiteOpenHelper {
        public bluetoothUserDB(@Nullable Context context) {
            super(context, "bluetoothUserDB", null, 1); //DB생성
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE bluetoothUserTBL (id TEXT(20) NOT NULL PRIMARY KEY, password TEXT(20) NOT NULL, name TEXT(50) NOT NULL, rfid TEXT(50) NOT NULL, email TEXT(10) NOT NULL);"); //bluetoothUserTBL Table생성
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    void bluetoothDB(String id, String password, String name, String rfid, String email) { //id,password,name,rfid,email 값 받아야함
        sqlDB = bludtoothUserDB.getWritableDatabase();
        sqlDB.execSQL("INSERT OR REPLACE INTO bluetoothUserTBL (id,password,name,rfid,email) VALUES ( '" + id + "','" + password + "','" + name + "','" + rfid + "','" + email + "');");
        // TODO: 2020-01-23 main.Java에서 bluetoothDB의 매개변수값을 넣어줘야함 
        sqlDB.close();
    }
}