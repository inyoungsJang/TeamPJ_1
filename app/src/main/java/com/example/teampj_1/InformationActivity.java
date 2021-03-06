package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.teampj_1.R;

public class InformationActivity extends AppCompatActivity {

    Button btnPassWordChange;
//    Button btnRFIDChange;
    TextView tvShowId, tvShowName;
    AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        tvShowId = (TextView) findViewById(R.id.tvShowId);
        tvShowName = (TextView) findViewById(R.id.tvShowName);
        btnPassWordChange = (Button) findViewById(R.id.btnPasswordChange);
//        btnRFIDChange = (Button) findViewById(R.id.btnRFIDChange);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("내 정보 수정");


        String id = DataManager.getInstance().getUserData().id;
        String name = DataManager.getInstance().getUserData().user_name;
        if (StateManager.getInstance().getIsLogin()) {
            tvShowId.setText(id);
            tvShowName.setText(name);
            btnPassWordChange.setEnabled(true);
        } else {
            tvShowId.setText("로그인이 필요합니다.");
            tvShowName.setText("");
            btnPassWordChange.setEnabled(false);
        }

        btnPassWordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordIntent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(passwordIntent);
            }
        });

//        btnRFIDChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder builder_createCard = new AlertDialog.Builder(InformationActivity.this);
//                builder_createCard.setTitle("");
//                View dialogView = (View) View.inflate(InformationActivity.this, R.layout.dialog_createcard, null);
//                //   mDevices = bluetoothAdapter.getBondedDevices();
//                //   mPairedDeviceCount = mDevices.size();
//                BluetoothDB btDB = new BluetoothDB(InformationActivity.this); //update
//
//                builder_createCard.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder_createCard.setView(dialogView);
//                builder_createCard.setCancelable(false);
//                ad = builder_createCard.create();
//                ad.show();
//            }
//        });
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
