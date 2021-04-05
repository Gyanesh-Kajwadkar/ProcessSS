package com.andoidproject.culluze_app.Activity.help_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andoidproject.culluze_app.Activity.help_area.FaqDetailsActivity;
import com.andoidproject.culluze_app.R;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolders> {
    Context context;
    public FaqAdapter(Context context)
    {
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faq_adapter_view,viewGroup,false);
        return new ViewHolders(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {

        viewHolders.itemname_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.startActivity(new Intent(context, FaqDetailsActivity.class));
            }
        });
    }
    @Override
    public int getItemCount() {
        return 10;
    }
    public class ViewHolders extends RecyclerView.ViewHolder {
        LinearLayout itemname_ll;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            itemname_ll = itemView.findViewById(R.id.itemname_ll);
        }
    }
}
