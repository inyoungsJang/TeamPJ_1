package com.example.teampj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WithdrawlActivity extends AppCompatActivity {

    TextView tvLostPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl);
        tvLostPassword = (TextView)findViewById(R.id.tvLostPassword);

        setTitle("회원탈퇴");

       tvLostPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), FindAccountActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
