package com.andoidproject.culluze_app.Activity.report_area;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andoidproject.culluze_app.R;

public class Reports_Activity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout billDetail_ll,orderDetail_ll,collection_ll,mapReport_ll,salesReport_ll;
    ImageView back_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_sub_detail);
        init();
    }

    private void init()
    {
        billDetail_ll=findViewById(R.id.billDetail_ll);
        orderDetail_ll=findViewById(R.id.orderDetail_ll);
        collection_ll=findViewById(R.id.collection_ll);
        mapReport_ll=findViewById(R.id.mapReport_ll);
        salesReport_ll=findViewById(R.id.salesReport_ll);
        back_img=findViewById(R.id.back_img);

        billDetail_ll.setOnClickListener(this);
        orderDetail_ll.setOnClickListener(this);
        collection_ll.setOnClickListener(this);
        mapReport_ll.setOnClickListener(this);
        salesReport_ll.setOnClickListener(this);

        back_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }

        if(v==billDetail_ll)
        {
            Intent intent= new Intent(Reports_Activity.this, Filter_Activity.class);
            intent.putExtra("send",1);
            startActivity(intent);
        }

        if(v==orderDetail_ll)
        {
            Intent intent= new Intent(Reports_Activity.this,Filter_Activity.class);
            intent.putExtra("send",2);
            startActivity(intent);
        }

        if(v==collection_ll)
        {
            Intent intent= new Intent(Reports_Activity.this,Filter_Activity.class);
            intent.putExtra("send",3);
            startActivity(intent);
        }

        if(v==mapReport_ll)
        {
            Intent intent= new Intent(Reports_Activity.this,Filter_Activity.class);
            intent.putExtra("send",4);
            startActivity(intent);
        }

        if(v==salesReport_ll)
        {
            Intent intent= new Intent(Reports_Activity.this,Filter_Activity.class);
            intent.putExtra("send",5);
            startActivity(intent);
        }




    }
}
