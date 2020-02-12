package com.example.teampj_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


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

    public void BluetoothInsertUserDB(String id, String password, String name) { //회원가입
        Log.i("test","id: "+id+" pass: "+password+" name: "+name);
        sqlDB.execSQL("INSERT INTO bluetoothUserTBL (id,password,name) VALUES ( '" + id + "','" + password + "','" + name + "');");
        sqlDB.close();
    }

    public void BluetoothUpdateRFIDDB(String rfid) { //RFID값
        String id; //DB의 id값이 존재하면 해당하는 id의 RFID 값을 추가
        Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL;", null);

        if (cursor.moveToLast() && cursor != null) {
            cursor.moveToLast();
            id = cursor.getString(0);
            sqlDB.execSQL("UPDATE bluetoothUserTBL SET rfid='" + rfid + "' WHERE id='" + id + "' ;");
            Toast.makeText(context, "카드를 등록하였습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "회원가입을 먼저 진행 해주세요.", Toast.LENGTH_SHORT).show();
        }
        sqlDB.close();
        cursor.close();
    }


//    public void BluetoothLoginDB(String id, String password){ //로그인
//        String id1; //
//        Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL;", null);
//
//        if (cursor.moveToFirst() && cursor != null) {
//            id1 = cursor.getString(0);
//            sqlDB.rawQuery("SELECT id='" + id+ "',password='"+password+"' FROM bluetoothUserTBL WHERE id='" + id1 + "' ;",null);//select
//        } else {
//            Toast.makeText(context, "회원가입을 먼저 진행 해주세요.", Toast.LENGTH_SHORT).show();
//        }
//        sqlDB.close();
//        cursor.close();
//    }

    public void bluetoothOverlapDB(){ //계정 중복확인

    }

    public void bluetoothUserNameDB(){ //로그인 후 사용자 이름 출력

    }
}
