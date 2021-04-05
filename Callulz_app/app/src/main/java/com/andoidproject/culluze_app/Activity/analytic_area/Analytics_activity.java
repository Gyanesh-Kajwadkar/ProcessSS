package com.andoidproject.culluze_app.Activity.analytic_area;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andoidproject.culluze_app.Activity.report_area.CusProduct_AnaSub;
import com.andoidproject.culluze_app.R;

public class Analytics_activity extends AppCompatActivity implements View.OnClickListener {

    ImageView back_img;
    LinearLayout dashboard_ll,customer_analytics_ll,customer_product_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_activity);
        init();
    }

    public void init()
    {
        back_img=findViewById(R.id.back_img);
        dashboard_ll=findViewById(R.id.dashboard_ll);
        customer_analytics_ll=findViewById(R.id.customer_analytics_ll);
        customer_product_ll=findViewById(R.id.customer_product_ll);

        back_img.setOnClickListener(this);
        dashboard_ll.setOnClickListener(this);
        customer_analytics_ll.setOnClickListener(this);
        customer_product_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==back_img)
        {
            onBackPressed();
        }

        if(v==dashboard_ll)
        {
            Intent intent=new Intent(Analytics_activity.this, DashBoard_AnaSub.class);
            startActivity(intent);
        }

        if(v==customer_analytics_ll)
        {
            Intent intent=new Intent(Analytics_activity.this, Customer_AnaSub.class);
            startActivity(intent); }


        if(v==customer_product_ll)
        {
            Intent intent=new Intent(Analytics_activity.this, CusProduct_AnaSub.class);
           intent.putExtra("send",1);
            startActivity(intent);
        }
    }
}
