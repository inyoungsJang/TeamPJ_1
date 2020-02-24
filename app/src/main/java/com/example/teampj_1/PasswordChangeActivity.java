package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import android.view.MenuItem;

public class PasswordChangeActivity extends AppCompatActivity {
    TextView tvNewPasswordCheck; //비밀번호 변경시 변경비밀번호와 값이 동일한지
    EditText edtNewPassword, edtNewPasswordCheck, edtGijonPassword;
    String newPasswordCheck, newPassword, gijonPassword;
    Button btnPasswordSave, btnCancel;
    boolean result = false;
    SQLiteDatabase sqlDB;
    BluetoothDB btDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("비밀번호 변경");

        tvNewPasswordCheck = (TextView) findViewById(R.id.tvNewPasswordCheck);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtNewPasswordCheck = (EditText) findViewById(R.id.edtNewPasswordCheck);
        edtGijonPassword = (EditText) findViewById(R.id.edtGijonPassword);
        btnPasswordSave = (Button) findViewById(R.id.btnPasswordSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        Log.i("test", "비밀번호:" + newPassword + "  비밀번호확인:" + newPasswordCheck);
        btDB = new BluetoothDB(this);

        edtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword = edtNewPassword.getText().toString(); //새로운 비밀번호
                newPasswordCheck = edtNewPasswordCheck.getText().toString(); //새로운 비밀번호 확인
                if (newPassword.equals("")) {
                    tvNewPasswordCheck.setText("*비밀번호를 입력주세요.");
                    tvNewPasswordCheck.setTextColor(Color.RED);
                } else if (newPassword.equals(newPasswordCheck)) {
                    tvNewPasswordCheck.setText("*비밀번호가 일치합니다.");
                    tvNewPasswordCheck.setTextColor(getResources().getColor(R.color.SignatureColorBlue));
                } else {
                    tvNewPasswordCheck.setText("*비밀번호를 다시 확인해주세요.");
                    tvNewPasswordCheck.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtNewPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword = edtNewPassword.getText().toString(); //새로운 비밀번호
                newPasswordCheck = edtNewPasswordCheck.getText().toString(); //새로운 비밀번호 확인
                if (newPassword.equals("")) {
                    tvNewPasswordCheck.setText("*비밀번호를 입력주세요.");
                    tvNewPasswordCheck.setTextColor(Color.RED);
                } else if (newPassword.equals(newPasswordCheck)) {
                    tvNewPasswordCheck.setText("*비밀번호가 일치합니다.");
                    tvNewPasswordCheck.setTextColor(getResources().getColor(R.color.SignatureColorBlue));
                } else {
                    tvNewPasswordCheck.setText("*비밀번호를 다시 확인해주세요.");
                    tvNewPasswordCheck.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btnPasswordSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                newPassword = edtNewPassword.getText().toString(); //새로운 비밀번호
//                newPasswordCheck = edtNewPasswordCheck.getText().toString(); //새로운 비밀번호 확인
                gijonPassword = edtGijonPassword.getText().toString(); //기존 비밀번호
                if (gijonPassword.equals(newPassword)) {
                    Toast.makeText(PasswordChangeActivity.this, "기존비밀번호와 동일합니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newPasswordCheck.equals("")) {
                    Toast.makeText(getApplicationContext(), "재입력란을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gijonPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "기존 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserData data = DataManager.getInstance().getUserData();
                String dataId, dataPassword;
                dataId = data.id; //로그인된 id
                dataPassword = data.password; //로그인된 password

                if (gijonPassword.equals(dataPassword)) { //현재 로그인계정 패스워드 == 기존패스워드
                    if (newPassword.equals(newPasswordCheck)) {
                        tvNewPasswordCheck.setText("*비밀번호가 일치합니다.");
                        tvNewPasswordCheck.setTextColor(getResources().getColor(R.color.SignatureColorBlue));
                        sqlDB = btDB.getWritableDatabase();
                        sqlDB.execSQL("UPDATE bluetoothUserTBL SET password = '" + newPasswordCheck + "' WHERE id='" + dataId + "'; ");
                        Log.i("test", "dataId=" + dataId + "비밀번호가 일치하여 비밀번호 업데이트");
                        sqlDB.close();
                        finish();
                    } else {
                        tvNewPasswordCheck.setText("*비밀번호를 다시 확인해주세요.");
                        tvNewPasswordCheck.setTextColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "두 비밀번호가 일치하지 않습니다.\n다시한번 확인해주세요.", Toast.LENGTH_SHORT);
                        Log.i("test", "비밀번호가일치하지않음");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "현재 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "비밀번호변경을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }//on Create END
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
