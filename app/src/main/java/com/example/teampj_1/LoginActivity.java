package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    EditText edtId, edtPassword;
    Button btnLogin, btnCancel;
    TextView tvGoLostId, tvGoLostPassword;

    BluetoothDB btDB;
    SQLiteDatabase sqlDB;//로그인성공시 특정값을 메인에넘겨줌으로써 로그인되었다는 확인을 받음
    //private int loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvGoLostId = (TextView) findViewById(R.id.tvGoLostId);
        tvGoLostPassword = (TextView) findViewById(R.id.tvGoLostPassword);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("로그인");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btDB = new BluetoothDB(this);

        tvGoLostPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindAccountActivity.class);
                startActivity(intent);
            }
        });

        tvGoLostId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindAccountIDActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString();
                String password = edtPassword.getText().toString();
                Login(id, password);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(101, intent);
                finish(); //현재창 종료하기
            }
        });
    } //onCreate END

    void Login(String id, String password) {
        if (password.equals("")) {
            showToast("ID PASSWORD를 입력해주세요");
        } else if (id.equals("")) {
            showToast("ID PASSWORD를 입력해주세요");
        } else {
            sqlDB = btDB.getReadableDatabase(); //읽다
            Cursor cUser = sqlDB.rawQuery("SELECT * FROM bluetoothUserTBL WHERE id='" + id + "';", null);
            if (cUser.moveToFirst()) { //디비가 존재한다면
                String strId, strPassword, strUserName, strRFID;
                strId = cUser.getString(0);
                strPassword = cUser.getString(1);
                strUserName = cUser.getString(2);
                strRFID = cUser.getString(3);

                if (id.equals(strId) && password.equals(strPassword)) { //동일하면
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(100, intent);
                    UserData data = DataManager.getInstance().getUserData();
                    DataManager.getInstance().setUserData(strId, strPassword, strUserName, strRFID);
                    finish(); //현재창 종료하기
                } else {
                    showToast("비밀번호를 다시한번 확인해주세요.");
                }
            } else {
                showToast("회원 정보가 없습니다.");
            }
            sqlDB.close();
            cUser.close();
        }
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
