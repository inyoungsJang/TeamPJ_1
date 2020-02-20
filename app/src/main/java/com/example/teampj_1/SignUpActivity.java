package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText edtId, edtPassword, edtName;
    Button btnSignup, btnCheckID,btnCancel;
    String id, password, rfid, name;

    // SQLiteDatabase sqlDB;
    BluetoothDB btDB;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtName = (EditText) findViewById(R.id.edtName);
        btnCheckID = (Button) findViewById(R.id.btnCheckID);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btDB = new BluetoothDB(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원가입");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnCheckID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString();
                if (id.equals("")) {
                    showToast("ID를 입력하세요");
                } else {
                    sqlDB = btDB.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL where id ='" + id + "';", null); //동일한 계정이있는지 중복성 검사
                    if (cursor.moveToFirst()) {
                        showToast("사용할 수 없는 아이디입니다.");
                    } else {
                        showToast("사용 가능한 아이디입니다.");
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = edtId.getText().toString();
                password = edtPassword.getText().toString();
                name = edtName.getText().toString();


                if (id.equals("")) {
                    showToast("ID를 입력해주세요");
                } else if (password.equals("")) {
                    showToast("패스워드를 입력해 주세요");
                } else if (name.equals("")) {
                    showToast("이름을 입력해주세요");
                } else {
                    sqlDB = btDB.getReadableDatabase();
                    Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL WHERE id = '" + id + "';", null); //동일한 계정이있는지 중복성 검사

                    if (cursor.moveToFirst()) { //저장된데이터가있으면 아이가 중복된것과 같다.
                        String cId = cursor.getString(0);
                        Log.i("test", "cId" + cId);
                        Log.i("test", "id" + id);

                        showToast("사용할 수 없는 아이디입니다.");

                    } else {
                        btDB.BluetoothInsertUserDB(id, password, name); // insert
                        UserData data = DataManager.getInstance().getUserData();
                        data.id = id;
                        data.password = password;
                        data.user_name = name;
                        showToast("생성 되었습니다.");
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        setResult(100);
                        sqlDB.close();
                        cursor.close();
                        finish(); //현재창 종료하기
                    }
                }
            }
        });

    } //onCreate END

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
