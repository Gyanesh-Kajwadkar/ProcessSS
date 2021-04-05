package com.andoidproject.culluze_app.Activity.report_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.CheckOut_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Outstanding_activity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.ReturnActivity;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.OfflineSales_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class ReturnAdapter extends RecyclerView.Adapter<ReturnAdapter.ViewHolder> implements Filterable {

    Context context;
    int source;
    ArrayList<Salesorderlist_Model>model=new ArrayList<>();
    ArrayList<Salesorderlist_Model>modelList;
    ArrayList<OfflineSales_Model> listSubmitSales=new ArrayList<>();
    int j=0;



    public ReturnAdapter(Context context, ArrayList<Salesorderlist_Model> model,int source)
    {
        this.context=context;
        this.modelList=model;
        this.model.addAll(modelList);
        this.source=source;
    }

    public ReturnAdapter(Context context, int source, ArrayList<OfflineSales_Model> listSubmitSales) {
        this.context=context;
        this.source=source;
        this.listSubmitSales=listSubmitSales;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if(source==1)
        {
             v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.return_view,viewGroup,false);
        }
        else {
            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offline_sales_view,viewGroup,false);

        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if(source==1)
        {Salesorderlist_Model modelList=model.get(i);

        viewHolder.amount_tv.setText(modelList.getTotSaleAmt()+"");
        viewHolder.date_tv.setText(modelList.getTranDate()+"");
        viewHolder.status_tv.setText(modelList.getStatus()+"");
        viewHolder.transDetail.setText(modelList.getTranNo()+"");
        viewHolder.return_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent= new Intent(context, Outstanding_activity.class);
                context.startActivity(intent);*/
            }
        });}

        else
            {
                 final OfflineSales_Model modelList=listSubmitSales.get(i);
                j=i+1;

            viewHolder.cartTv.setText("Cart "+j);

            viewHolder.cartRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CheckOut_Activity.class);
                    if(source==2)
                    {
                        intent.putExtra("type","offline");
                    }
                    else {
                        intent.putExtra("type","offOrder");
                    }
                    intent.putExtra("totalMoney","");
                    intent.putExtra("offlineList",modelList);
                    context.startActivity(intent);
                }
            });
            }

    }

    @Override
    public int getItemCount() {
        int count=0;
        if(source==1)
        {
            count=model.size();
        }
        else {
            count=listSubmitSales.size();
        }

        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView transDetail,amount_tv,status_tv,date_tv,cartTv;
        LinearLayout return_ll;
        RelativeLayout cartRl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            return_ll=itemView.findViewById(R.id.return_ll);

            transDetail=itemView.findViewById(R.id.transDetail);
            amount_tv=itemView.findViewById(R.id.amount_tv);
            status_tv=itemView.findViewById(R.id.status_tv);
            date_tv=itemView.findViewById(R.id.date_tv);
            cartTv=itemView.findViewById(R.id.cartTv);
            cartRl=itemView.findViewById(R.id.cartRl);
        }
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                model.clear();
                final FilterResults results = new FilterResults();
                if(constraint.length() == 0){
                    model.addAll(modelList);
                }else{
                    final String filterPattern =constraint.toString().toLowerCase().trim();
                    for(Salesorderlist_Model listcountry : modelList){
                        String TransIs=listcountry.getTranNo();
                        String dateIs=listcountry.getTranDate();
                        String statusIs=listcountry.getStatus();
                        String amountIs= String.valueOf(listcountry.getTotSaleAmt());
                        if(TransIs.toLowerCase().contains(filterPattern)){
                            model.add(listcountry);
                        }
                            else if(dateIs.toLowerCase().contains(filterPattern))
                        {
                            model.add(listcountry);
                        }
                        else if(statusIs.toLowerCase().contains(filterPattern))
                        {
                            model.add(listcountry);
                        }
                        else if(amountIs.toLowerCase().contains(filterPattern))
                        {
                            model.add(listcountry);
                        }


                    }}
                results.values = model;
                results.count = model.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

}
