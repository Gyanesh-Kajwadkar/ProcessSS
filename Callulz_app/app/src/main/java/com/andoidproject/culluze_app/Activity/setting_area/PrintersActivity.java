package com.andoidproject.culluze_app.Activity.setting_area;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.andoidproject.culluze_app.R;

public class PrintersActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_img;
    RelativeLayout printdesign_rl;
    RadioButton noPrinter_rbtn;
    LinearLayout wirlsprinter_ll,pdf_ll;
    ScrollView printer_ll;
    EditText printer_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printers);
        initView();
    }

    public void  initView(){
        back_img = findViewById(R.id.back_img);
        printer_et = findViewById(R.id.printer_et);
        printdesign_rl = findViewById(R.id.printdesign_rl);
        printer_ll = findViewById(R.id.print_ll);
        wirlsprinter_ll = findViewById(R.id.wirlsprinter_ll);
        pdf_ll = findViewById(R.id.pdf_ll);
        noPrinter_rbtn = findViewById(R.id.noPrinter_rbtn);


        back_img.setOnClickListener(this);
        printdesign_rl.setOnClickListener(this);
        noPrinter_rbtn.setChecked(true);
        printer_ll.setOnClickListener(this);

        boolean chck = noPrinter_rbtn.isChecked();
        if (chck) {
            wirlsprinter_ll.setVisibility(View.GONE);
            pdf_ll.setVisibility(View.GONE);
        }

        printer_et.setTextIsSelectable(true);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.noPrinter_rbtn:
                if (checked)
                    wirlsprinter_ll.setVisibility(View.GONE);
                   pdf_ll.setVisibility(View.GONE);
                    break;
            case R.id.wirls_rbtn:
                if (checked)
                    wirlsprinter_ll.setVisibility(View.VISIBLE);
                    pdf_ll.setVisibility(View.GONE);
                    break;
            case R.id.pdf_rbtn:
                if (checked)
                    wirlsprinter_ll.setVisibility(View.GONE);
                    pdf_ll.setVisibility(View.VISIBLE);
                    break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v==back_img){
            onBackPressed();
        }
        if (v==printer_ll){
            printer_ll.setFocusable(true);
            printer_ll.setFocusableInTouchMode(true);
        }
        if (v==printdesign_rl){
          startActivity(new Intent(PrintersActivity.this, PrintDesignActivity.class));
        }
    }
}
