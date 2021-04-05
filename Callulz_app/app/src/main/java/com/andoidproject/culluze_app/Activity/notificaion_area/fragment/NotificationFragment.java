package com.andoidproject.culluze_app.Activity.notificaion_area.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.andoidproject.culluze_app.Activity.notificaion_area.adapter.NotificationAdapter;
import com.andoidproject.culluze_app.R;

public class NotificationFragment extends Fragment {

    RecyclerView notification_rv;
    NotificationAdapter notificationAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
       initview(view);
        return view;
    }

    public void initview(View view){
        notification_rv = view.findViewById(R.id.notification_rv);
        notificationAdapter();
    }

    public void notificationAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        notification_rv.setLayoutManager(linearLayoutManager);
        notificationAdapter  = new NotificationAdapter(getActivity());
        notification_rv.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
    }
}
