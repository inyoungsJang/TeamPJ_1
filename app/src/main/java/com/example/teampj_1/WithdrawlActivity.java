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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class WithdrawlActivity extends AppCompatActivity {

    TextView tvLostPassword;
    TextView tvWithdrawalId;
    EditText edtWithdrawalPassword;
    Button btnWithdrawalOk, btnCancel;
    CheckBox WithdrawalCheck;
    boolean check = false;

    SQLiteDatabase sqlDB;
    BluetoothDB btDB;

    String dataPassword;
    String dataId;
    String withdrawalPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        setTitle("회원탈퇴");

        tvLostPassword = (TextView) findViewById(R.id.tvLostPassword);
        tvWithdrawalId = (TextView) findViewById(R.id.tvWithdrawalId);
        edtWithdrawalPassword = (EditText) findViewById(R.id.edtWithdrawalPassword);
        btnWithdrawalOk = (Button) findViewById(R.id.btnWithdrawalOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        WithdrawalCheck = (CheckBox) findViewById(R.id.WithdrawalCheck);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("회원탈퇴");

        Boolean isLogin = StateManager.getInstance().getIsLogin();


        if (isLogin) {
            dataId = DataManager.getInstance().getUserData().id;
            tvWithdrawalId.setText(dataId);
        } else {
            tvWithdrawalId.setText("로그인이 필요합니다.");
        }

        edtWithdrawalPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                dataPassword = DataManager.getInstance().getUserData().password;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                withdrawalPassword = edtWithdrawalPassword.getText().toString();

                if (dataPassword.equals(withdrawalPassword)) {
                    check = true;
                } else {
                    check = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnWithdrawalOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    if (WithdrawalCheck.isChecked()) {
                        btDB = new BluetoothDB(WithdrawlActivity.this);
                        sqlDB = btDB.getWritableDatabase();
                        sqlDB.execSQL("DELETE FROM bluetoothUserTBL WHERE id='" + dataId + "';");
                        DataManager.getInstance().Logout();
                        Toast.makeText(getApplicationContext(), "회원탈퇴 되셨습니다.", Toast.LENGTH_SHORT).show();
                        sqlDB.close();
                    /*    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("withdraw","로그아웃");
                        startActivity(intent);*/
                    //    setResult(RESULT_OK);
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        setResult(101, intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "유의사항을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시한번 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvLostPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), FindAccountActivity.class);
                startActivity(mIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "탈퇴를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
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
