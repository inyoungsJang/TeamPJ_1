package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FindAccountActivity extends AppCompatActivity {
    TextView tvLostID;
    EditText edtId, edtName;
    TextView tvShowPassword;
    Button btnAccountCheck;

    String strName, strId;

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

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strName = edtName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
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
