package com.andoidproject.culluze_app.Activity.analytic_area;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.activity.Forgot_Activity;
import com.andoidproject.culluze_app.Activity.notificaion_area.NotificationActivity;
import com.andoidproject.culluze_app.Activity.report_area.CusProduct_AnaSub;
import com.andoidproject.culluze_app.R;

public class DashBoard_AnaSub extends AppCompatActivity  implements View.OnClickListener {

    TextView toolbarText;
    ImageView back_img;
    LinearLayout cart_llBig,order_llbig,bills_ll,order_ll,recept_ll,return_ll;
    FloatingActionButton floatingAction;
    ImageView cornerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board__ana_sub);
        init();
    }

    private void init()
    {

        floatingAction=findViewById(R.id.floatingAction);
        back_img=findViewById(R.id.back_img);
        toolbarText=findViewById(R.id.toolbarText);
        cornerImage=findViewById(R.id.cornerImage);
        toolbarText.setText("Dashboard");

        cornerImage.setImageResource(R.drawable.refresh);


        cart_llBig=findViewById(R.id.cart_llBig);
        order_llbig=findViewById(R.id.order_llbig);
        bills_ll=findViewById(R.id.bills_ll);
        order_ll=findViewById(R.id.order_ll);
        recept_ll=findViewById(R.id.recept_ll);
        return_ll=findViewById(R.id.return_ll);


        back_img.setOnClickListener(this);
        cart_llBig.setOnClickListener(this);
        order_llbig.setOnClickListener(this);
        bills_ll.setOnClickListener(this);
        order_ll.setOnClickListener(this);
        recept_ll.setOnClickListener(this);
        return_ll.setOnClickListener(this);
        floatingAction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(back_img==v)
        {
            onBackPressed();
        }

        if(bills_ll==v || cart_llBig==v || order_llbig==v)
        {
            Intent intent=new Intent(this, CusProduct_AnaSub.class);
            intent.putExtra("send",1);
            startActivity(intent);
        }

        if(order_ll==v)
        {
            Intent intent=new Intent(this, CusProduct_AnaSub.class);
            intent.putExtra("send",2);
            startActivity(intent);
        }

        if(recept_ll==v)
        {
            Intent intent=new Intent(this, CusProduct_AnaSub.class);
            intent.putExtra("send",3);
            startActivity(intent);
        }

        if(return_ll==v)
        {
            Intent intent=new Intent(this, CusProduct_AnaSub.class);
            intent.putExtra("send",1);
            startActivity(intent);
        }

        if(floatingAction==v)
        {
            Intent intent=new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }
    }





}

// RecyclerView recycler_dashboard;
//recycler_dashboard=findViewById(R.id.recycler_dashboard);
  /*  recycler_dashboard.setLayoutManager(new LinearLayoutManager(this));
        recycler_dashboard.setItemAnimator(new DefaultItemAnimator());
        Dashboard_Adapter adapter= new Dashboard_Adapter();
        recycler_dashboard.setAdapter(adapter);*/