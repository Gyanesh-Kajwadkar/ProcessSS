package com.andoidproject.culluze_app.Activity.setting_area.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class PrintDesignAdapter extends RecyclerView.Adapter<PrintDesignAdapter.ViewHolders> {
    Context context;
    ArrayList<String> companyInfoList;
    public PrintDesignAdapter(Context context, ArrayList<String> companyInfoList)
    {
        this.context=context;
        this.companyInfoList=companyInfoList;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.print_design_adapter_view,viewGroup,false);
        return new ViewHolders(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {
        String str =  companyInfoList.get(i);
        viewHolders.checkbox.setText(str);
    }
    @Override
    public int getItemCount() {
        return companyInfoList.size();
    }
    public class ViewHolders extends RecyclerView.ViewHolder {
         CheckBox checkbox;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
