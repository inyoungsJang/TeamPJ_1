package com.example.teampj_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

/*class BluetoothDBMy {
    SQLiteDatabase sqlDB;
    BluetoothDB bluetoothDB;

    // Context context;
    void BluetoothUpdateRFIDDB(String rfid) {
        sqlDB = bluetoothDB.getWritableDatabase();
        String id; //DB의 id값이 존재하면 해당하는 id의 RFID 값을 추가
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM bluetoothUserTBL;", null);
        id = cursor.getString(0);
        if (cursor.moveToFirst() && cursor != null) {
            sqlDB.execSQL("UPDATE bluetoothUserTBL SET rfid='" + rfid + "' WHERE id='" + id + "' ;");
        } else {
            //Toast.makeText()
        }
    }

    void BluetoothInsertUserDB(String id, String password, String name) {
        sqlDB = bluetoothDB.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO bluetoothUserTBL VALUES ( '" + id + "','" + password + "','" + name + "');");
    }
//    BluetoothDB db = new BluetoothDB();
} //Class BluetoothDBMy END*/

public class BluetoothDB extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase sqlDB = getWritableDatabase();

    public BluetoothDB(@Nullable Context context) {
        super(context, "bluetoothUserDB", null, 1); //DB생성
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bluetoothUserTBL (id TEXT(20) NOT NULL PRIMARY KEY, password TEXT(20) NOT NULL, name TEXT(50) NOT NULL, rfid TEXT(50));"); //bluetoothUserTBL Table생성
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public void BluetoothInsertUserDB(String id, String password, String name) {
        sqlDB.execSQL("INSERT INTO bluetoothUserTBL (id,password,name) VALUES ( '" + id + "','" + password + "','" + name + "');");
    }

    public void BluetoothUpdateRFIDDB(String rfid) {
        String id; //DB의 id값이 존재하면 해당하는 id의 RFID 값을 추가
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM bluetoothUserTBL;", null);
        id = cursor.getString(0);
        if (cursor.moveToFirst() && cursor != null) {
            sqlDB.execSQL("UPDATE bluetoothUserTBL SET rfid='" + rfid + "' WHERE id='" + id + "' ;");
        } else {
            Toast.makeText(context, "회원가입 먼저 해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
