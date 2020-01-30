package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText edtId, edtPassword, edtName, edtEmail;
    TextView singUp;
    ImageView imgExit;

    String id, password, email, rfid, name;

    SQLiteDatabase sqlDB;
    BluetoothDB.bluetoothUserDB bluetoothUserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtName = (EditText) findViewById(R.id.edtName);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        singUp = (TextView) findViewById(R.id.singUp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bluetoothUserDB = new BluetoothDB().bluetoothUserDB;

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //현재창 종료하기
            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = edtId.getText().toString();
                password = edtPassword.getText().toString();
                email = edtEmail.getText().toString();
                name = edtName.getText().toString();

                if (password.equals("")) {
                    showToast("password null error");
                } else if (name.equals("")) {
                    showToast("name null error");
                } else if (id.equals("")) {
                    showToast("id null error");
                } else if (email.equals("")) {
                    showToast("email null error");
                } else {
                    bluetoothDB(id, password, name, email);
                    sqlDB.close();
                    showToast("create user");
                }
            }
        });

    } //onCreate END

    void bluetoothDB(String id, String password, String name, String email) { //id,password,name,rfid,email 값 받아야함
        sqlDB = bluetoothUserDB.getWritableDatabase(); //쓰고읽기
        sqlDB.execSQL("INSERT OR REPLACE INTO bluetoothUserTBL (id,password,name,email) VALUES ( '" + id + "','" + password + "','" + name + "','" + email + "');");
        // TODO: 2020-01-23 main.Java에서 bluetoothDB의 매개변수값을 넣어줘야함

    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
