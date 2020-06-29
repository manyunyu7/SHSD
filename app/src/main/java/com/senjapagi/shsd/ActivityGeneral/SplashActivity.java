package com.senjapagi.shsd.ActivityGeneral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.senjapagi.shsd.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final Handler a = new Handler();

        a.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent a = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(a);
                finish();
            }
        }, 1500);
    }
    private void checkMentee(){

    }
}