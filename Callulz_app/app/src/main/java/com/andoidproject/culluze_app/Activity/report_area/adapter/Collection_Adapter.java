package com.andoidproject.culluze_app.Activity.report_area.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andoidproject.culluze_app.R;

public class Collection_Adapter extends RecyclerView.Adapter<Collection_Adapter.ViewHolders> {

    Context context;

    public Collection_Adapter(Context context)
    {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailrepo_view,viewGroup,false);
    return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {

        viewHolders.status_text.setTextColor(ContextCompat.getColor(context, R.color.black));
        viewHolders.status_text.setText("Rs 1000.00");
        viewHolders.order_value.setVisibility(View.GONE);
        viewHolders.order_txt.setText("12/6/2019");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView status_text,order_value,order_txt;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            status_text=itemView.findViewById(R.id.status_text);
            order_value=itemView.findViewById(R.id.order_value);
            order_txt=itemView.findViewById(R.id.order_txt);

        }
    }
}
