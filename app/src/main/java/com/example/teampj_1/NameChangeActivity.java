package com.example.teampj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NameChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_change);

        setTitle("이름 변경");
    }
}
