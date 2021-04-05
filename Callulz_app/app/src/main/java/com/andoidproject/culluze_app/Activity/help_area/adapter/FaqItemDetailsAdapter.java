package com.andoidproject.culluze_app.Activity.help_area.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.R;

public class FaqItemDetailsAdapter extends RecyclerView.Adapter<FaqItemDetailsAdapter.ViewHolders> {
    Context context;
    public FaqItemDetailsAdapter(FragmentActivity context)
    {
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faq_item_details_adapter,viewGroup,false);
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolders viewHolders, int i) {
        viewHolders.dropdown_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolders.reversedropdown_imv.setVisibility(View.VISIBLE);
                viewHolders.dropdown_imv.setVisibility(View.GONE);
                viewHolders.expand_ll.setVisibility(View.VISIBLE);
            }
        });

        viewHolders.reversedropdown_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolders.dropdown_imv.setVisibility(View.VISIBLE);
                viewHolders.reversedropdown_imv.setVisibility(View.GONE);
                viewHolders.expand_ll.setVisibility(View.GONE);

            }
        });
    }
    @Override
    public int getItemCount() {
        return 10;
    }
    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView query_tv;
        ImageView dropdown_imv,reversedropdown_imv;
        LinearLayout expand_ll;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            query_tv = itemView.findViewById(R.id.query_tv);
            dropdown_imv = itemView.findViewById(R.id.dropdown_imv);
            reversedropdown_imv = itemView.findViewById(R.id.reversedropdown_imv);
            expand_ll = itemView.findViewById(R.id.expand_ll);
        }
    }
}
