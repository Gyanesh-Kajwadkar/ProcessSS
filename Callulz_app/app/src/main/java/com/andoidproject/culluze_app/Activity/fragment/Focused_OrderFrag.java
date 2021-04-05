package com.andoidproject.culluze_app.Activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoidproject.culluze_app.R;

public class Focused_OrderFrag extends Fragment  {

    private String title;
    private int page;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_frg, container, false);

        return view;
    }


    public static Focused_OrderFrag newInstance(int page, String title) {
        Focused_OrderFrag fragmentFirst = new Focused_OrderFrag();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

}
