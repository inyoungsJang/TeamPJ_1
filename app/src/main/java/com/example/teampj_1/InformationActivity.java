package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teampj_1.R;

public class InformationActivity extends AppCompatActivity {

    Button btnPassWordChange;
    TextView tvShowId, tvShowName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        tvShowId = (TextView) findViewById(R.id.tvShowId);
        tvShowName = (TextView) findViewById(R.id.tvShowName);

        setTitle("내 정보 수정");

        btnPassWordChange = (Button) findViewById(R.id.btnPasswordChange);

        String id = DataManager.getInstance().getUserData().id;
        String name = DataManager.getInstance().getUserData().user_name;
        if (StateManager.getInstance().getIsLogin()) {
            tvShowId.setText(id);
            tvShowName.setText(name);
            btnPassWordChange.setEnabled(true);
        } else {
            tvShowId.setText("로그인이 필요합니다.");
            tvShowName.setText("이름");
            btnPassWordChange.setEnabled(false);
        }

        btnPassWordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordIntent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(passwordIntent);
            }
        });
    }
}
