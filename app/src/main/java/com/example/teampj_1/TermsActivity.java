package com.example.teampj_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class TermsActivity extends AppCompatActivity {
    TextView tvHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("이용약관");

        tvHelp = (TextView) findViewById(R.id.tvHelp);


        InputStream inputStream = getResources().openRawResource(R.raw.help2); //
        try {
            byte[] txt = new byte[inputStream.available()];
            inputStream.read(txt);
            tvHelp.setText(new String(txt));// txt(byte) => txt(String)
            inputStream.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "파일을 읽을 수가 없습니다", Toast.LENGTH_SHORT).show();
            System.err.println();
        }
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
