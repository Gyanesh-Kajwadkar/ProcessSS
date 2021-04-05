package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.adapter.CustomerAdapter;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.notificaion_area.NotificationActivity;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;

public class CustomerActivity extends Base_Activity implements View.OnClickListener {

    RecyclerView customer_recylcer;
    ImageView back_img,setting_img,addnew_user_img;
    FloatingActionButton floatingAction;
    EditText search_et;
    ImageView filter_img;
    View name_view;
    CustomerAdapter adapter;
    private ArrayList<Get_CustomerModel> modellist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        init();
    }

    private void init()
    {
        customer_recylcer=findViewById(R.id.customer_recylcer);
        back_img=findViewById(R.id.back_img);
        setting_img=findViewById(R.id.setting_img);
        addnew_user_img=findViewById(R.id.addnew_user_img);
        floatingAction=findViewById(R.id.floatingAction);
        search_et=findViewById(R.id.search_et);
        name_view=findViewById(R.id.name_view);
        filter_img=findViewById(R.id.filter_img);

//=====================RecyclerCode=============================================

       // ArrayList<Get_CustomerModel> modellist=getHistoryList("allCustomer");
       /* modellist.add(new Get_CustomerModel());
        modellist.add(new Get_CustomerModel());
        modellist.add(new Get_CustomerModel());
        modellist.add(new Get_CustomerModel());*/

  /*      modellist = getHistoryList("allCustomer");
        customer_recylcer.setLayoutManager(new LinearLayoutManager(this));
        customer_recylcer.setItemAnimator(new DefaultItemAnimator());
        final CustomerAdapter adapter= new CustomerAdapter(this,modellist);
        customer_recylcer.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        customer_recylcer.setAdapter(adapter);*/

            callRecycler();



//=====================RecyclerCode=============================================



        search_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    name_view.setElevation(2f);
                    lineAnimation(name_view);
                }
                else {
                    Reverse_lineAnimation(name_view);
                } }});


    //======================clickEvent==================================
        back_img.setOnClickListener(this);
        setting_img.setOnClickListener(this);
        addnew_user_img.setOnClickListener(this);
        floatingAction.setOnClickListener(this);
        filter_img.setOnClickListener(this);
    //======================clickEvent==================================
    }

    @Override
    public void onClick(View v) {
        if(v==back_img)
        {
            onBackPressed();
        }

        if(v==setting_img)
        {
            Intent intent= new Intent(CustomerActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        if(v==addnew_user_img)
        {
            Intent intent= new Intent(CustomerActivity.this, AddUser_Activity.class);
            startActivity(intent);
        }

        if(v==floatingAction)
        {
            Intent intent= new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }

        if(filter_img==v)
        {
            setMenuCustomer(filter_img);
        }

    }

    //================================getArreyList=========================================
         public ArrayList<Get_CustomerModel> getHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Get_CustomerModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
    //================================getArreyList=========================================

    //==============================setPopupMenu&ClickEvent=================================
         public void setMenuCustomer(ImageView filter_img) {
        PopupMenu popup = new PopupMenu(this, filter_img);
        popup.getMenuInflater().inflate(R.menu.customer_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String id = (String) item.getTitle();

                if(id.equals("Customer name") )
                {
                    search_et.setText("");
                    modellist = getHistoryCustomer(loginModel.getUserId()+"allCustomer",CustomerActivity.this);

                    Collections.sort(modellist, new Comparator<Get_CustomerModel>(){
                        public int compare(Get_CustomerModel s1, Get_CustomerModel s2) {
                            return s1.getCustmrName().compareToIgnoreCase(s2.getCustmrName());
                        }
                    });

                    adapter = new CustomerAdapter(CustomerActivity.this,modellist);
                    customer_recylcer.addItemDecoration(new DividerItemDecoration(CustomerActivity.this, LinearLayout.VERTICAL));
                    customer_recylcer.setAdapter(adapter);
                }

                if(id.equals("All"))
                {
                    search_et.setText("");
                    callRecycler();
                }


                return false;
            }
        });

    }
    //==============================setPopupMenu&ClickEvent=================================


    //=============================callRecycler===========================================
    private void callRecycler()
    {
        modellist = getHistoryCustomer(loginModel.getUserId()+"allCustomer",CustomerActivity.this);
        customer_recylcer.setLayoutManager(new LinearLayoutManager(this));
        customer_recylcer.setItemAnimator(new DefaultItemAnimator());
        adapter= new CustomerAdapter(this,modellist);
        customer_recylcer.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        customer_recylcer.setAdapter(adapter);

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());

            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



    }
    //=============================callRecycler===========================================

}
