package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    EditText edtId, edtPassword;
    ImageView imgExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgExit = (ImageView) findViewById(R.id.imgExit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //현재창 종료하기
            }
        });
    }
}
