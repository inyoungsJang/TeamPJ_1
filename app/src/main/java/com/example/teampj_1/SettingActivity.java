package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {
    //    public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //    static final String[] LIST_SET = {"도움말", "개발자 버전", "계정정보", "비밀번호 변경"};
//    View view = View.inflate(R.layout.)
    Button btnHelp, btnInformation, btnLockPassword, btnWithdrawal, btnTerms, btnFindPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("설정");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_SET);
//        listview 에 listViewSetting 가져와서 저장
//        ListView listView = (ListView)findViewById(R.id.listViewSetting);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnInformation = (Button) findViewById(R.id.btnInformation);
        btnLockPassword = (Button) findViewById(R.id.btnLockPassword);
        btnWithdrawal = (Button) findViewById(R.id.btnWithdrawal);
        btnTerms = (Button) findViewById(R.id.btnTerms);
        btnFindPassword = (Button) findViewById(R.id.btnFindPassword);

        btnFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), FindAccountActivity.class);
                startActivity(intent6);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent1 = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(mIntent1);
            }
        });
        btnTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent5 = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(mIntent5);
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent2 = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(mIntent2);
            }
        });

        btnLockPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent3 = new Intent(getApplicationContext(), LockPasswordActivity.class);
                startActivity(mIntent3);
            }
        });
        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent4 = new Intent(getApplicationContext(), WithdrawlActivity.class);
                startActivity(mIntent4);
            }
        });

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

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

