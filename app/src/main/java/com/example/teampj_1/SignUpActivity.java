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

    String id, password, rfid, name;

    SQLiteDatabase sqlDB;
    BluetoothDB btDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtName = (EditText) findViewById(R.id.edtName);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        singUp = (TextView) findViewById(R.id.singUp);

        btDB  = new BluetoothDB(this); //insert

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
                name = edtName.getText().toString();

                if (password.equals("")) {
                    showToast("password null error");
                } else if (name.equals("")) {
                    showToast("name null error");
                } else if (id.equals("")) {
                    showToast("id null error");
                } else {
                    btDB.bluetoothInsertUserDB(id,password,name); // insert
                    sqlDB.close();
                    showToast("create user");
                }
            }
        });

    } //onCreate END

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
