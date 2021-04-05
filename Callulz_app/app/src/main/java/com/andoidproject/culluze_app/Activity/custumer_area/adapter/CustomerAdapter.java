package com.andoidproject.culluze_app.Activity.custumer_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolders> implements Filterable {
    Context context;
    ArrayList<Get_CustomerModel>modelList;
    public ArrayList<Get_CustomerModel> custerList=new ArrayList<>();


    public CustomerAdapter(Context context, ArrayList<Get_CustomerModel>modelList)
    {
        this.context=context;
        // this.modelList=getHistoryList("allCustomer");
         this.modelList=modelList;
         custerList.addAll(modelList);
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_view,viewGroup,false);
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolders viewHolders, final int i) {

        final Get_CustomerModel temp = custerList.get(i);


        viewHolders.name_tv.setText(temp.getCustmrName());
        viewHolders.mobile_no.setText(temp.getMobl());
        viewHolders.address_tv.setText(temp.getAddress());

        viewHolders.beat_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(context, Detail_CustomSub_act.class);
                intent.putExtra("customerDetail", temp);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return custerList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView mobile_no,name_tv,address_tv;
        RelativeLayout beat_rv;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            mobile_no=itemView.findViewById(R.id.mobile_no);
            beat_rv=itemView.findViewById(R.id.beat_rv);
            address_tv=itemView.findViewById(R.id.address_tv);
            name_tv=itemView.findViewById(R.id.name_tv);


            Paint p = new Paint();
            p.setColor(ContextCompat.getColor(context,android.R.color.holo_blue_dark));
            p.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            mobile_no.setPaintFlags(p.getFlags());

        }
    }

    public ArrayList<Get_CustomerModel> getHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Get_CustomerModel>>() {}.getType();
        return gson.fromJson(json, type);
    }


    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                custerList.clear();
                final FilterResults results = new FilterResults();
                if(constraint.length() == 0){
                    custerList.addAll(modelList);

                }else{

                    final String filterPattern =constraint.toString().toLowerCase().trim();
                    for(Get_CustomerModel listcountry : modelList){
                        String nameIs=listcountry.getCustmrName();
                       // String mobile=listcountry.getMobile();

                        if(nameIs.toLowerCase().startsWith(filterPattern)){

                            custerList.add(listcountry);
                        }
                    }
                }
                results.values = custerList;
                results.count = custerList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }


}
