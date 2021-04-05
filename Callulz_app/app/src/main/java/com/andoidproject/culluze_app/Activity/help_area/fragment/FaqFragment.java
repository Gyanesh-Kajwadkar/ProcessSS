package com.andoidproject.culluze_app.Activity.help_area.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoidproject.culluze_app.Activity.help_area.adapter.FaqAdapter;
import com.andoidproject.culluze_app.R;

public class FaqFragment extends Fragment {


  RecyclerView faq_rv;
  FaqAdapter faqAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        iniView(view);
        return view;
    }

    public void iniView(View view){
        faq_rv = view.findViewById(R.id.faq_rv);
        callFaqAdapter();
    }
    public void callFaqAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        faq_rv.setLayoutManager(linearLayoutManager);
        faqAdapter = new FaqAdapter(getActivity());
        faq_rv.setAdapter(faqAdapter);
        faqAdapter.notifyDataSetChanged();
    }
}
