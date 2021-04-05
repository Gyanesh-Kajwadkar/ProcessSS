package com.andoidproject.culluze_app.Activity.route_area;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.CustomerActivity;
import com.andoidproject.culluze_app.Activity.route_area.adapter.RoutePlanner_Adapter;
import com.andoidproject.culluze_app.R;

public class RoutePlanner_Main extends AppCompatActivity implements View.OnClickListener {

    RecyclerView routePlanner_recycler;
    ImageView back_img;
    Button loadCustomers_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planner__main);
        init();
    }

    private void init()
    {
        routePlanner_recycler=findViewById(R.id.routePlanner_recycler);
        back_img=findViewById(R.id.back_img);
        loadCustomers_btn=findViewById(R.id.loadCustomers_btn);

        routePlanner_recycler.setLayoutManager(new LinearLayoutManager(this));
        routePlanner_recycler.setItemAnimator(new DefaultItemAnimator());
        RoutePlanner_Adapter adapter= new RoutePlanner_Adapter(this);
        routePlanner_recycler.setAdapter(adapter);

        back_img.setOnClickListener(this);
        loadCustomers_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(back_img==v)
        {
            onBackPressed();
        }

        if(loadCustomers_btn==v)
        {
            Intent intent= new Intent(this, CustomerActivity.class);
            startActivity(intent);
        }


    }
}
