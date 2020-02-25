package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindAccountActivity extends AppCompatActivity {
    TextView tvLostID;
    EditText edtId, edtName;
    TextView tvShowPassword;
    Button btnAccountCheck;

    String strName, strId;
    String cPassword;

    BluetoothDB btDB;
    SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("비밀번호 찾기");

        tvLostID = (TextView) findViewById(R.id.tvLostID);
        edtId = (EditText) findViewById(R.id.edtId);
        edtName = (EditText) findViewById(R.id.edtName);
        tvShowPassword = (TextView) findViewById(R.id.tvShowPassword);
        btnAccountCheck = (Button) findViewById(R.id.btnAccountCheck);

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strName = edtName.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strName = edtName.getText().toString();
            }
        });

        edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strId = edtId.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strId = edtId.getText().toString();
            }
        });

        btnAccountCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strId = edtId.getText().toString();
                strName = edtName.getText().toString();
                if (strId.equals("") || strName.equals("")) {
                    Toast.makeText(FindAccountActivity.this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    btDB = new BluetoothDB(FindAccountActivity.this);
                    sqlDB = btDB.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT password FROM bluetoothUserTBL WHERE name='" + strName + "'AND id='" + strId + "';", null);

                    if (cursor.moveToFirst()) {
                        cPassword = cursor.getString(0);
                        tvShowPassword.setText(cPassword);
                    } else {
                        Toast.makeText(FindAccountActivity.this, "회원정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        tvShowPassword.setText("회원님의 비밀번호는");
                    }
                    cursor.close();
                    sqlDB.close();
                }
            }
        });

        tvLostID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), FindAccountIDActivity.class);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
