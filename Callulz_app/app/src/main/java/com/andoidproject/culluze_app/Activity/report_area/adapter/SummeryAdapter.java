package com.andoidproject.culluze_app.Activity.report_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.model.Orderlist_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.Activity.report_area.Detail_Activity_rep;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class SummeryAdapter extends RecyclerView.Adapter<SummeryAdapter.ViewHolders> {

    int message;
    Context context;
    ArrayList<Orderlist_Model>orderlist;
    ArrayList<Salesorderlist_Model>salesList;


    public SummeryAdapter(Context context,int message)
    {
        this.message=message;
        this.context=context;
    }

    public SummeryAdapter(Context context, int message, ArrayList<Orderlist_Model>orderlist)
    {
        this.message=message;
        this.context=context;
        this.orderlist=orderlist;
    }

    public SummeryAdapter(Context context, int message, ArrayList<Salesorderlist_Model>salesList,int i)
    {
        this.message=message;
        this.context=context;
        this.salesList=salesList;
    }




    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        if(message==2||message==5){
            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.summery_view,viewGroup,false);
        }
        if(message==3){
            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.summery_view2,viewGroup,false);
        }
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {

        if(message==2){
            viewHolders.arrow.setVisibility(View.INVISIBLE);
            Orderlist_Model orders=orderlist.get(i);
            viewHolders.name_tv.setText(orders.getTranNo());
            viewHolders.status_tv.setText(orders.getStatus());
            viewHolders.amount_tv.setText(String.format("%.2f", orders.getTotSaleAmt()));

       /*     viewHolders.view0_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Detail_Activity_rep.class);
                    intent.putExtra("detail",1);
                    context.startActivity(intent);
                }
            });*/

        }

        if(message==5){
            viewHolders.arrow.setVisibility(View.INVISIBLE);
            Salesorderlist_Model orders=salesList.get(i);
            viewHolders.name_tv.setText(orders.getTranNo());
            viewHolders.status_tv.setText(orders.getStatus());
            viewHolders.date_tv.setText(orders.getTranDate());
            viewHolders.amount_tv.setText(String.format("%.2f", orders.getTotSaleAmt()));

        }

        if(message==3){
            viewHolders.view1_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Detail_Activity_rep.class);
                    intent.putExtra("detail",2);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        int countNumber;
        if(message==2)
        {
            countNumber=orderlist.size();
        }
        else  if(message==5)
        {
            countNumber=salesList.size();
        }
        else
        {
            countNumber=1;
        }
        return countNumber;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        LinearLayout view0_ll,view1_ll;
        TextView name_tv,status_tv,amount_tv,date_tv;
        ImageView arrow;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            view0_ll=itemView.findViewById(R.id.view0_ll);
            view1_ll=itemView.findViewById(R.id.view1_ll);
            name_tv=itemView.findViewById(R.id.name_tv);
            status_tv=itemView.findViewById(R.id.status_tv);
            amount_tv=itemView.findViewById(R.id.amount_tv);
            arrow=itemView.findViewById(R.id.arrow);
            date_tv=itemView.findViewById(R.id.date_tv);

        }
    }
}
