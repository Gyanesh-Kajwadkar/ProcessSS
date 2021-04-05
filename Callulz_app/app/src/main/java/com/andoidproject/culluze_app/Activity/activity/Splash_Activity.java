package com.andoidproject.culluze_app.Activity.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andoidproject.culluze_app.R;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_);
        new Handler( ).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(Splash_Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
