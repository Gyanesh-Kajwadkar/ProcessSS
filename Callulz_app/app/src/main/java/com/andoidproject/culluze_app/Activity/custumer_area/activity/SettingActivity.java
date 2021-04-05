package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.andoidproject.culluze_app.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init(); }

    private void init()
    {    back_img=findViewById(R.id.back_img);

        back_img.setOnClickListener(this); }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }
    }
}
