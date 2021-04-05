package com.andoidproject.culluze_app.Activity.help_area.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoidproject.culluze_app.Activity.help_area.adapter.VideoAdapter;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class VideoFragment extends Fragment {


VideoAdapter videoAdapter;
RecyclerView video_rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        iniView(view);
        return view;
    }

    public void iniView(View view){
        video_rv = view.findViewById(R.id.video_rv);
        callFaqAdapter();
    }
    public void callFaqAdapter(){

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Login");
        arrayList.add("Order");
        arrayList.add("Collection");
        arrayList.add("Check In");
        arrayList.add("Print");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        video_rv.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(getActivity(),arrayList);
        video_rv.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }

}
