package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teampj_1.R;

public class InformationActivity extends AppCompatActivity {

    Button btnPassWordChange, btnNameChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        setTitle("내 정보 수정");

        btnPassWordChange = (Button)findViewById(R.id.btnPasswordChange);
        btnNameChange = (Button)findViewById(R.id.btnNameChange);

        btnPassWordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordIntent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(passwordIntent);

            }
        });
        btnNameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nameIntent = new Intent(getApplicationContext(), NameChangeActivity.class);
                startActivity(nameIntent);
            }
        });
    }

}
