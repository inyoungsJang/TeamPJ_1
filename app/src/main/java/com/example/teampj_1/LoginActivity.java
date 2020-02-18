package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText edtId, edtPassword;
    TextView tvLogin;
    ImageView imgExit;

    BluetoothDB btDB;
    SQLiteDatabase sqlDB;//로그인성공시 특정값을 메인에넘겨줌으로써 로그인되었다는 확인을 받음
    private int loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btDB = new BluetoothDB(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString();
                String password = edtPassword.getText().toString();

                Login(id, password);
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                loginSuccess = 0;
                intent.putExtra("piLOGIN", loginSuccess);
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
                    loginSuccess = 1;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("piLOGIN", loginSuccess);
//                            startActivity(intent,);
                    setResult(100, intent);
                    UserData data = DataManager.getInstance().getUserData();
                    data.id = strId;
                    data.password = strPassword;
                    data.user_name = strUserName;
                    data.rfid = strRFID;
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

}
