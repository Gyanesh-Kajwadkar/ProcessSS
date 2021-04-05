package com.andoidproject.culluze_app.Activity.route_area.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolders> {

    ArrayList<String>setList;


   public CommonAdapter(ArrayList<String>setList)
   {
       this.setList=setList;
   }


    boolean initilize=true;
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_view,viewGroup,false);
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolders viewHolders, int i) {

        String str=setList.get(i);

        viewHolders.name_tv.setText(str);
    }

    @Override
    public int getItemCount() {
        return setList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        CheckBox name_tv;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            name_tv=itemView.findViewById(R.id.address_checkbox);

        }
    }



}
