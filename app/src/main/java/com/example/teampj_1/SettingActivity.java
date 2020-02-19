package com.example.teampj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity{
//    public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //    static final String[] LIST_SET = {"도움말", "개발자 버전", "계정정보", "비밀번호 변경"};
//    View view = View.inflate(R.layout.)
    Button btnHelp, btnInformation, btnLockPassword, btnVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle("설정");

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_SET);
//        listview 에 listViewSetting 가져와서 저장
//        ListView listView = (ListView)findViewById(R.id.listViewSetting);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnInformation = (Button) findViewById(R.id.btnInformation);
        btnLockPassword = (Button) findViewById(R.id.btnLockPassword);
      //  btnVersion = (Button) findViewById(R.id.btnVersion);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent1 = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(mIntent1);
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

//        btnVersion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent4 = new Intent(getApplicationContext(), VersionActivity.class);
//                startActivity(mIntent4);
//            }
//        });
    }
}

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if(position == 0){
//            Intent mIntent1 = new Intent(SettingActivity.this, HelpActivity.class);
//            startActivity(mIntent1);
//        }
//        if(position == 1){
//            Intent mIntent2 = new Intent(SettingActivity.this, VersionActivity.class);
//            startActivity(mIntent2);
//        }
//        if(position == 2){
//            Intent mIntent3 = new Intent(SettingActivity.this, InformationActivity.class);
//            startActivity(mIntent3);
//        }
//        if(position == 3){
//            Intent mIntent4 = new Intent(SettingActivity.this, LockPasswordActivity.class);
//            startActivity(mIntent4);
//        }
//    }
//}
