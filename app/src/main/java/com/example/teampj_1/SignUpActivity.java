package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText edtId, edtPassword, edtName;
    TextView tvSignup;
    ImageView imgExit;

    String id, password, rfid, name;

    // SQLiteDatabase sqlDB;
    BluetoothDB btDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtName = (EditText) findViewById(R.id.edtName);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        tvSignup = (TextView) findViewById(R.id.tvSignup);

        btDB = new BluetoothDB(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //현재창 종료하기
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = edtId.getText().toString();
                password = edtPassword.getText().toString();
                name = edtName.getText().toString();
                SQLiteDatabase sqlDB;
                sqlDB = btDB.getReadableDatabase();
                Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL", null); //동일한 계정이있는지 중복성 검사


                if (password.equals("")) {
                    showToast("password null error");
                } else if (name.equals("")) {
                    showToast("name null error");
                } else if (id.equals("")) {
                    showToast("id null error");
                } else {
                    String cId;
                    if (cursor.moveToFirst() && cursor.getString(0) != null) { //저장된데이터가있으면 비교
                        cId = cursor.getString(0);
                        Log.i("test", "cId" + cId);
                        Log.i("test", "id" + id);
                        if (id.equals(cId)) {
                            showToast("사용할 수 없는 아이디입니다.");
                        } else {
                            btDB.BluetoothInsertUserDB(id, password, name); // insert
                            showToast("생성 되었습니다.");
                        }
                    }else{
                        btDB.BluetoothInsertUserDB(id, password, name); // insert
                        showToast("생성 되었습니다.");
                    }
                }
                sqlDB.close();
                cursor.close();
                finish(); //현재창 종료하기
            }
        });

    } //onCreate END

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
