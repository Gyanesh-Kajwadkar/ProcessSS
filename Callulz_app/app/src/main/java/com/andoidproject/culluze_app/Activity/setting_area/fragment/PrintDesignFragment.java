package com.andoidproject.culluze_app.Activity.setting_area.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoidproject.culluze_app.Activity.setting_area.adapter.PrintDesignAdapter;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class PrintDesignFragment extends Fragment {

    ArrayList<String> companyInfoList = new ArrayList<>();
    ArrayList<String> billInfoList = new ArrayList<>();
    ArrayList<String> summaryList = new ArrayList<>();
    RecyclerView printdesign_rv;
    PrintDesignAdapter printDesignAdapter;
    String itemName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_print_design, container, false);
       initView(view);
        return view;
    }

     public void initView(View view){
         printdesign_rv = view.findViewById(R.id.printdesign_rv);
         companyInfoList.add("Address");
         companyInfoList.add("Phone");
         companyInfoList.add("Tax number");
         companyInfoList.add("mail Id");


         billInfoList.add("Customer Name");
         billInfoList.add("Customer Address");
         billInfoList.add("Customer Phone");
         billInfoList.add("Customer Tax");
         billInfoList.add("Customer Code");
         billInfoList.add("Invoice No");
         billInfoList.add("Bill Reference No(Return)");
         billInfoList.add("Date");
         billInfoList.add("Time");
         billInfoList.add("User");
         billInfoList.add("Location");


         summaryList.add("Total Items");
         summaryList.add("Total Quantity");
         summaryList.add("Sub Total");
         summaryList.add("Tax Amount");
         summaryList.add("Discount");
         summaryList.add("Aound off Amount");
         summaryList.add("Remarks");


         callPrintDesignAdapter();

     }

     public void callPrintDesignAdapter(){
         itemName  =  getArguments().getString("itemName");

         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
         printdesign_rv.setLayoutManager(layoutManager);

         if (itemName == "CompanyInfo") {
             printDesignAdapter = new PrintDesignAdapter(getActivity(),companyInfoList);
             printdesign_rv.setAdapter(printDesignAdapter);
             printDesignAdapter.notifyDataSetChanged();
         }
         if (itemName == "BillInfo") {
             printDesignAdapter = new PrintDesignAdapter(getActivity(),billInfoList);
             printdesign_rv.setAdapter(printDesignAdapter);
             printDesignAdapter.notifyDataSetChanged();
         }
         if (itemName == "Summary") {
             printDesignAdapter = new PrintDesignAdapter(getActivity(),summaryList);
             printdesign_rv.setAdapter(printDesignAdapter);
             printDesignAdapter.notifyDataSetChanged();
         }
     }
}
