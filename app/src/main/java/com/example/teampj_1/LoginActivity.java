package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
    SQLiteDatabase sqlDB;

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
                String strId ;
                String strPassword ;

                if (password.equals("")) {
                    showToast("password null error");
                } else if (id.equals("")) {
                    showToast("id null error");
                } else {
                    sqlDB = btDB.getReadableDatabase(); //읽다
                    Cursor cId = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL WHERE id='" + id + "' ", null);
                    Cursor cPassword = sqlDB.rawQuery("SELECT password FROM bluetoothUserTBL WHERE password='" + password + "' ", null);

                    if (cId.moveToFirst() && cId.getString(0) != null) { //정보가 존재한다면
                        strId = cId.getString(0);
                        cPassword.moveToFirst();
                        strPassword = cPassword.getString(0);
                        if (id.equals(strId) && password.equals(strPassword)) { //동일하면
                            btDB.BluetoothLoginDB(id, password); //select
                            showToast("로그인을 하였습니다");
                        } else if (!(id.equals(strId))) {
                            showToast("아이디를 다시한번 확인해주세요.");
                        } else if (id.equals(strId) && !(password.equals(strPassword))) {
                            showToast("비밀번호를 다시한번 확인해주세요.");
                        }
                        cId.close();
                        cPassword.close();
                    } else {
                        showToast("정보가없습니다.");
                    }
//
//                    if (id.equals(strId) && password.equals(strPassword)) { //동일하면
//                        btDB.BluetoothLoginDB(id, password); //select
//                        showToast("로그인 성공");
//                        sqlDB.close();
//                    } else if (!id.equals(strId)) {
//                        showToast("아이디를 다시한번 확인해주세요.");
//                    } else if (!password.equals(strPassword)) {
//                        showToast("비밀번호를 다시한번 확인해주세요.");
//                    }// else {
                    //      showToast("정보가없습니다.");
                    //   }
                    // boolean a;
                    // Log.i("test", a = id.equals(strId) ? true : false);

                    finish(); //현재창 종료하기
                }
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //현재창 종료하기
            }
        });
    } //onCreate END

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
