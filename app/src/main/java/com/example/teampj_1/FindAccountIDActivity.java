package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FindAccountIDActivity extends AppCompatActivity {
    BluetoothDB btDB;
    SQLiteDatabase sqlDB;

    ListView listShowId;
    Button btnAccountCheck;
    EditText edtName;
    ArrayList<String> strId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_account_id);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("아이디 찾기");

        btnAccountCheck = (Button) findViewById(R.id.btnAccountCheck);
        edtName = (EditText) findViewById(R.id.edtName);
        listShowId = (ListView) findViewById(R.id.listShowId);

        strId = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strId);
        listShowId.setAdapter(adapter);

        btnAccountCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = edtName.getText().toString();
                if (strName.equals("")) {
                    Toast.makeText(FindAccountIDActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                btDB = new BluetoothDB(FindAccountIDActivity.this);
                sqlDB = btDB.getReadableDatabase();
                Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL WHERE name='" + strName + "';", null);
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    strId.add(cursor.getString(0));
                }
                adapter.notifyDataSetChanged(); //갱신
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
