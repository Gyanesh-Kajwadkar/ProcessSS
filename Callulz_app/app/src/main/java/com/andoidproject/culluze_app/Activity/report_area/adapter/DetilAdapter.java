package com.andoidproject.culluze_app.Activity.report_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andoidproject.culluze_app.Activity.report_area.Collection_MainAct;
import com.andoidproject.culluze_app.R;

public class DetilAdapter extends RecyclerView.Adapter<DetilAdapter.ViewHolders> {

    int message;
    Context context;
    int sizeList;
    public DetilAdapter(Context context, int message, int sizeList)
    {
        this.message=message;
        this.context=context;
        this.sizeList=sizeList;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        if(message==1){
            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailrepo_view,viewGroup,false);
        }

        if(message==2){
            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailrepo_view2,viewGroup,false);
        }
        return new ViewHolders(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {

        if(message==2){
        viewHolders.detail_twoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Collection_MainAct.class);
                context.startActivity(intent);
            }
        });  }
    }

    @Override
    public int getItemCount() {
        return sizeList; }

    public class ViewHolders extends RecyclerView.ViewHolder {
        LinearLayout detail_twoll;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            detail_twoll=itemView.findViewById(R.id.detail_twoll);
        }
    }
}
