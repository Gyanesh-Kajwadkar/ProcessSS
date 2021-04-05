package com.andoidproject.culluze_app.Activity.notificaion_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoidproject.culluze_app.Activity.notificaion_area.DisplayNotificationDetailsActivity;
import com.andoidproject.culluze_app.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolders> {
    Context context;


    public NotificationAdapter(FragmentActivity context)
    {
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_adapter_view,viewGroup,false);
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {
        viewHolders.ntcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DisplayNotificationDetailsActivity.class));
            }
        });
    }
    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        CardView ntcardView;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            ntcardView = itemView.findViewById(R.id.ntcardView);

        }
    }
}
