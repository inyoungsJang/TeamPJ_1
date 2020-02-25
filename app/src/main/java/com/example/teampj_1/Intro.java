package com.example.teampj_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class Intro extends AppCompatActivity {
    ImageView ivD, ivEasy;
    Animation ani_D, ani_Easy; //13213213

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        ActionBar bar = getSupportActionBar();
        bar.hide();

        ivD=(ImageView)findViewById(R.id.ivD);
        ivEasy=(ImageView)findViewById(R.id.ivEasy);

        ani_Easy= AnimationUtils.loadAnimation(Intro.this,R.anim.splash_move); //옆으로이동하면서 사라짐

        ivEasy.startAnimation(ani_Easy);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);




    }
}
