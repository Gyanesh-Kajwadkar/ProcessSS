package com.andoidproject.culluze_app.Activity.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.R;

public class Forgot_Activity extends Base_Activity implements View.OnClickListener {


    Button sumbit_btn;
    TextView toolbarText;
    ImageView back_img;
    View name_view;
    EditText forgot_mob_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_);

        init();
    }

    private void init()
    {
        sumbit_btn=findViewById(R.id.sumbit_btn);
        toolbarText=findViewById(R.id.toolbarText);
        back_img=findViewById(R.id.back_img);
        name_view=findViewById(R.id.name_view);
        forgot_mob_et=findViewById(R.id.forgot_mob_et);

        toolbarText.setText("Forgot Password");
        back_img.setOnClickListener(this);
        sumbit_btn.setOnClickListener(this);


        forgot_mob_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    name_view.setElevation(2f);
                    lineAnimation(name_view);
                }
                else {

                    Reverse_lineAnimation(name_view);
                } }});




    }

    @Override
    public void onClick(View v) {

        if(v==sumbit_btn)
        {
            onBackPressed();
            Toast.makeText(Forgot_Activity.this, "Message send Successfully", Toast.LENGTH_SHORT).show();

        }

        if(v==back_img)
        {
            onBackPressed();
        }
    }
}
