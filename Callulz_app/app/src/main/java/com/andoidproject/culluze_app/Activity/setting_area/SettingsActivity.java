package com.andoidproject.culluze_app.Activity.setting_area;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener
{
    RelativeLayout printer_ll,config_ll,remot_ll,about_ll;
    ImageView back_img,cornerImage;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    public void initView()
    {
        printer_ll = findViewById(R.id.printer_ll);
        config_ll = findViewById(R.id.config_ll);
        remot_ll = findViewById(R.id.remot_ll);
        about_ll = findViewById(R.id.about_ll);
        back_img = findViewById(R.id.back_img);
        cornerImage = findViewById(R.id.cornerImage);
        toolbarText = findViewById(R.id.toolbarText);

        toolbarText.setText("Setting");

        printer_ll.setOnClickListener(this);
        config_ll.setOnClickListener(this);
        remot_ll.setOnClickListener(this);
        about_ll.setOnClickListener(this);
        back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==printer_ll){
         startActivity(new Intent(SettingsActivity.this, PrintersActivity.class));
        }
        if (v==config_ll){

        }
        if (v==remot_ll){

        }
        if (v==about_ll){

            new AlertDialog.Builder(this).setTitle("About")
                    .setMessage("Earn verion 3.0.64")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // Perform Action & Dismiss dialog
                                    dialog.dismiss();
                                }
                            })
                    .create()
                    .show(); }

        if(v==back_img)
        {
            onBackPressed();
        }
    }
}
