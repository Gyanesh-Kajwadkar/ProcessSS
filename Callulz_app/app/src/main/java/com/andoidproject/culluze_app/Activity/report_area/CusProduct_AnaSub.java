package com.andoidproject.culluze_app.Activity.report_area;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.model.Orderlist_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.Activity.report_area.adapter.SummeryAdapter;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class CusProduct_AnaSub extends AppCompatActivity implements View.OnClickListener {

    ImageView back_img;
    TextView toolbarText,totalValueEnter,totalBillsEntry,totalItemEnter;
    TextView textupper,textLeft,textRight;
    LinearLayout data_ll;
    int message;
    RecyclerView summeryRecycler;
    ArrayList<Salesorderlist_Model>salesOrder;
    ArrayList<Orderlist_Model>orderlist;
    String statusFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_product__ana_sub);
        init();
    }

    private void init()
    {
        back_img=findViewById(R.id.back_img);
        toolbarText=findViewById(R.id.toolbarText);
        textupper=findViewById(R.id.textupper);
        textLeft=findViewById(R.id.textLeft);
        textRight=findViewById(R.id.textRight);
        totalItemEnter=findViewById(R.id.totalItemEnter);
        totalBillsEntry=findViewById(R.id.totalBillsEntry);
        totalValueEnter=findViewById(R.id.totalValueEnter);
        data_ll=findViewById(R.id.data_ll);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getInt("send");

        toolbarText.setText("Summary");
        summeryRecycler = findViewById(R.id.summeryRecycler);
        summeryRecycler.setLayoutManager(new LinearLayoutManager(this));
        summeryRecycler.setItemAnimator(new DefaultItemAnimator());
        if(message==2|| message==3) {
            SummeryAdapter adapter = new SummeryAdapter(this, message);
            summeryRecycler.setAdapter(adapter);
        }

        if(message==2)
        {
            statusFilter= bundle.getString("statusFilter");

            textupper.setText("total value");
            textLeft.setText("total orders");
            textRight.setText("total items");
            bundle=getIntent().getBundleExtra("bundle");
            orderlist= (ArrayList<Orderlist_Model>) bundle.getSerializable("arreyList");

            ArrayList<Orderlist_Model>list=new ArrayList<>();

            if(TextUtils.equals(statusFilter,"All"))
            {
                list.addAll(orderlist);
            }

            else if(TextUtils.equals(statusFilter,"Full Supply"))
            {
                for(int i=0;i<orderlist.size();i++)
                {
                    Orderlist_Model orders=orderlist.get(i);
                    if(TextUtils.equals(orders.getStatus(),"Completed"))
                    {
                        list.add(orders);
                    }
                }
            }

            else if(TextUtils.equals(statusFilter,"Partial Supply"))
            {
                for(int i=0;i<orderlist.size();i++)
                {
                    Orderlist_Model orders=orderlist.get(i);
                    if(TextUtils.equals(orders.getStatus(),"Incomplete"))
                    {
                        list.add(orders);
                    }
                }
            }

            totalBillsEntry.setText(orderlist.size()+"");
            totalItemEnter.setText(orderlist.size()+"");
            double values=0;
            for(int i=0;i<orderlist.size();i++)
            {
                Orderlist_Model orders=orderlist.get(i);
                values=values+orders.getTotSaleAmt();
            }
            totalValueEnter.setText(String.format("%.2f", values));

            SummeryAdapter adapter = new SummeryAdapter(this, message,list);
            summeryRecycler.setAdapter(adapter);
        }
        if(message==5)
        {
            statusFilter= bundle.getString("statusFilter");
            textupper.setText("total value");
            textLeft.setText("total orders");
            textRight.setText("total items");
            bundle=getIntent().getBundleExtra("bundle");
            salesOrder= (ArrayList<Salesorderlist_Model>) bundle.getSerializable("arreyList");


            ArrayList<Salesorderlist_Model>list=new ArrayList<>();

            if(TextUtils.equals(statusFilter,"All"))
            {
                list.addAll(salesOrder);
            }

            else if(TextUtils.equals(statusFilter,"Full Supply"))
            {
                for(int i=0;i<salesOrder.size();i++)
                {
                    Salesorderlist_Model orders=salesOrder.get(i);
                    if(TextUtils.equals(orders.getStatus(),"Completed"))
                    {
                        list.add(orders);
                    }
                }
            }

            else if(TextUtils.equals(statusFilter,"Partial Supply"))
            {
                for(int i=0;i<salesOrder.size();i++)
                {
                    Salesorderlist_Model orders=salesOrder.get(i);
                    if(TextUtils.equals(orders.getStatus(),"Incomplete"))
                    {
                        list.add(orders);
                    }
                }
            }


            totalBillsEntry.setText(salesOrder.size()+"");
            totalItemEnter.setText(salesOrder.size()+"");
            double values=0;
            for(int i=0;i<salesOrder.size();i++)
            {
                Salesorderlist_Model orders=salesOrder.get(i);
                values=values+orders.getTotSaleAmt();
            }
            totalValueEnter.setText(String.format("%.2f", values));
            int i=0;
            SummeryAdapter adapter = new SummeryAdapter(this, message,salesOrder,i);
            summeryRecycler.setAdapter(adapter);
        }

        if(message==3)
        {
            textupper.setText("total value");
            textLeft.setText("Cash amount");
            textRight.setText("cheque amount");
        }


     /*   if(message==6)
        {
            data_ll.setVisibility(View.GONE);
            statusFilter= bundle.getString("statusFilter");
            bundle=getIntent().getBundleExtra("bundle");
            salesOrder= (ArrayList<Salesorderlist_Model>) bundle.getSerializable("arreyList");


            ArrayList<Salesorderlist_Model>list=new ArrayList<>();

                list.addAll(salesOrder);
            int i=0;
            SummeryAdapter adapter = new SummeryAdapter(this, 5,salesOrder,i);
            summeryRecycler.setAdapter(adapter);
        }*/

        back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==back_img)
        {
            onBackPressed();
        }
    }
}
