package com.example.progressdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Base_Activity {

    Button progressButton,toastButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressButton=findViewById(R.id.progressButton);
        toastButton=findViewById(R.id.toastButton);
        progressBar=findViewById(R.id.progressBar);


        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    disableProgress(progressBar);
                }
            },3000);
            enableProgress(progressBar);
            }
        });

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(onBack)
        {
            super.onBackPressed();
        }
        else {
            //Do something
        }

    }
}
