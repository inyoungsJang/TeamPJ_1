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
    String cId, cName, cPassword;

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
            }
        });

        edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               strId= edtId.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btDB = new BluetoothDB(this);
        sqlDB = btDB.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT id,name,password FROM bluetoothUserTBL WHERE name='"+strName+"';", null);
        if (cursor.moveToFirst()) {
            cId = cursor.getString(0);
            cName = cursor.getString(1);
            cPassword = cursor.getString(2);

        } else {
            Toast.makeText(getApplicationContext(), "가입된 회원정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        btnAccountCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strId.equals("") && strName.equals("")) {
                    Toast.makeText(FindAccountActivity.this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (strName.equals(cName) && strId.equals(cId)) { //
                        tvShowPassword.setText(cPassword);
                    } else {
                        Toast.makeText(getApplicationContext(), "없는 회원정보입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //마지막 저장값 가져와야함.
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
