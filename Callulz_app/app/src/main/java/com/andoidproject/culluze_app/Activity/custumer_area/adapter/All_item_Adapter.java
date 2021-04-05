package com.andoidproject.culluze_app.Activity.custumer_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.analytic_area.adapter.Dashboard_Adapter;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Purchase_ItemDetail_act;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.ItemDetail_orderSub;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.fragment.Items_OrderFrag;
import com.andoidproject.culluze_app.Activity.model.BrandList_Model;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.R;
import com.google.android.gms.common.data.DataHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.cartItemNumber_tv;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.cartdigit;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.staticFreqList;
import static com.andoidproject.culluze_app.Activity.custumer_area.fragment.Items_OrderFrag.editInput;

public class All_item_Adapter extends RecyclerView.Adapter<All_item_Adapter.ViewHolders> implements Filterable {

    FragmentActivity context;
    public  ArrayList<Brand_SubBrand_Model.Item> allList=new ArrayList<>();
    public  ArrayList<Brand_SubBrand_Model.Item> allList2=new ArrayList<>();
    int n = 0;
    boolean status = false;
    ArrayList<Brand_SubBrand_Model.Item> subLists = new ArrayList<>();
    private Fragment fragment;
    String cartNumbeer = "0";

    String editValue;



    Sales_Activity sales_activity = new Sales_Activity();

    public All_item_Adapter(FragmentActivity context, Fragment fragment,boolean isNeedHistory)
    {   cartdigit=staticItemList.size()+staticFreqList.size();
        status = true;
        this.context=context;
        this.fragment = fragment;
        allList2 = getHistoryList("allItems");
        allList.addAll(allList2);
       // this.allList=allList2;
        this.subLists.addAll(staticItemList);

        Log.e("constructorA","constructor");
     /*   if(!isNeedHistory) {
        } else {
            this.subLists.addAll(staticItemList);
        }*/
        //      this.allBrand=allBrand;
    }

    public All_item_Adapter(FragmentActivity context,boolean isNeedHistory,ArrayList<Brand_SubBrand_Model.Item> callList,Fragment fragment)
    {
        status = true;
        this.context=context;
        this.fragment = fragment;
        Log.e("constructorB","constructor");
        this.allList2 = callList;
        allList.addAll(allList2);
        this.subLists.addAll(staticItemList);
       /* if(!isNeedHistory) {
        } else {
            this.subLists.addAll(staticItemList);
        }*/
        //      this.allBrand=allBrand;
    }

    public All_item_Adapter(FragmentActivity context, Fragment fragment) {
        this.context=context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public All_item_Adapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freqbought_view,viewGroup,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final All_item_Adapter.ViewHolders viewHolders, final int i) {

            final Brand_SubBrand_Model.Item list=allList.get(i);
            if(subLists.size()!=0)
            {

                boolean setData=true;
                for (int j = 0; j < subLists.size(); j++)
                {

                    Brand_SubBrand_Model.Item model = subLists.get(j);
                    if (model.getId().equals(list.getId()))
                    {
                        model.setItmName(list.getItmName());
                        model.setItmCd(list.getItmCd());
                        model.setItmBarCd(list.getItmBarCd());
                        model.setCatgry(list.getCatgry());
                        model.setUnit(list.getUnit());
                        model.setBrand(list.getBrand());
                        model.setRTSWT(list.getRTSWT());
                        model.setEXSWT(list.getEXSWT());
                        model.setWHSWT(list.getWHSWT());
                        model.setDTSWT(list.getDTSWT());
                        model.setAvilStk(list.getAvilStk());
                        model.setTaxId(list.getTaxId());
                        model.setTaxPerc(list.getTaxPerc());
                        model.setMrp(list.getMrp());

                        staticItemList.set(j,model);
                        viewHolders.parentLl.setVisibility(View.GONE);
                            setData=false; } }

                if(setData)
                {
                    viewHolders.parentLl.setVisibility(View.VISIBLE);
                    setTextView_two(viewHolders, list, i);
                }
                //  }
            }

            else
            {
                viewHolders.parentLl.setVisibility(View.VISIBLE);
                setTextView_two(viewHolders,list,i);
            }

       // }

    /*    if(order==1)
        {
            viewHolders.order_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, ItemDetail_orderSub.class);
                    context.startActivity(intent);

                }
            });

            viewHolders.itemDetail_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, Purchase_ItemDetail_act.class);
                    context.startActivity(intent);
                }
            });
        }*/
    }

    @Override
    public int getItemCount() {
        return allList.size();
    }


    public class ViewHolders extends RecyclerView.ViewHolder {
        LinearLayout order_ll,parentLayout,free_ll,parentLl;
        ImageView itemDetail_img;
        TextView productName,free_tv,stock_tv,taxper_tv,mrp_tv,rate_tv;
        EditText qty_et;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            order_ll=itemView.findViewById(R.id.order_ll);
            itemDetail_img=itemView.findViewById(R.id.itemDetail_img);
            productName=itemView.findViewById(R.id.productName);
            qty_et=itemView.findViewById(R.id.qty_et);
            parentLayout=itemView.findViewById(R.id.parentLayout);
            parentLl=itemView.findViewById(R.id.parentLl);

            free_ll=itemView.findViewById(R.id.free_ll);
            free_tv=itemView.findViewById(R.id.free_tv);
            stock_tv=itemView.findViewById(R.id.stockfreq_tv);
            taxper_tv=itemView.findViewById(R.id.taxper_tv);
            mrp_tv=itemView.findViewById(R.id.mrp_tv);
            rate_tv=itemView.findViewById(R.id.rate_tv);

            MyTextWatcher textWatcher = new MyTextWatcher(qty_et);
            qty_et.addTextChangedListener(textWatcher);

        }}


    public ArrayList<Brand_SubBrand_Model.Item> getHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Brand_SubBrand_Model.Item>>() {}.getType();
        return gson.fromJson(json, type); }


    public ArrayList<Brand_SubBrand_Model.Item> getItemListModel()
    {
        return allList;
    }


    private void setTextView_two(final All_item_Adapter.ViewHolders viewHolders, final Brand_SubBrand_Model.Item list, final int i)
    {
        final boolean[] status = {false};
        final boolean[] checkZero = {false};
        viewHolders.qty_et.setTag(list);
        viewHolders.qty_et.setTag(R.id.qty_et,status);

        editValue=viewHolders.qty_et.getText().toString();
        viewHolders.productName.setText(list.getItmName());

        if(list.getQuantity()!=0) {
            viewHolders.qty_et.setText(list.getQuantity() + "");
        }
        else
        {viewHolders.qty_et.setText("");}

        viewHolders.free_tv.setText(list.getFreetv());

        float avlStock=list.getAvilStk();

        if(avlStock<=0  )
        {
            Log.e("adapterPosition",  viewHolders.getAdapterPosition()+"");
            Log.e("adapterPosition",  i+"");
            viewHolders.stock_tv.setText("STOCK: Out of Stock");
            viewHolders.stock_tv.setTextColor(Color.RED);
            viewHolders.qty_et.setFocusable(false);
            viewHolders.qty_et.setClickable(false);
            viewHolders.qty_et.setCursorVisible(false);
            viewHolders.qty_et.setEnabled(false);
        }
        else
        {
            viewHolders.stock_tv.setText("STOCK: "+list.getAvilStk());
            viewHolders.stock_tv.setTextColor(Color.BLACK);
            viewHolders.qty_et.setFocusable(true);
            viewHolders.qty_et.setClickable(true);
            viewHolders.qty_et.setCursorVisible(true);
            viewHolders.qty_et.setEnabled(true);
            viewHolders.qty_et.setFocusableInTouchMode(true);
        }

        viewHolders.taxper_tv.setText("TAX: "+list.getTaxPerc()+"%");

        viewHolders.mrp_tv.setText(list.getMrp()+"");

        String customerRate=custmer.getCustRate();

        if(customerRate.equals("ITM-RT"))
        {
            viewHolders.rate_tv.setText(list.getRTSWT()+"");
        }
        else if(customerRate.equals("ITM-WH"))
        {
            viewHolders.rate_tv.setText(list.getWHSWT()+"");
        }

        else if(customerRate.equals("ITM-DI"))
        {
            viewHolders.rate_tv.setText(list.getDTSWT()+"");
        }

        else if(customerRate.equals("ITM-EX"))
        {
            viewHolders.rate_tv.setText(list.getEXSWT()+"");
        }

        if(!viewHolders.free_tv.getText().toString().isEmpty())
        {
            viewHolders.free_ll.setVisibility(View.VISIBLE);
        }


      /*  viewHolders.qty_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {

                if(!TextUtils.isEmpty(s.toString()))
                {

                    int checkInput=Integer.parseInt(s.toString());

                    if(checkInput!=0)
                    {
                        Log.e("000000000",list.isPostion()+"");
                        Log.e("//////////",i+"");
                        Log.e("selectedPostion",i+"");

                        if(!list.isPostion() )
                        {

                            list.setPostion(true);
                            Log.e("dsfsdsf",list.isPostion()+"");

              *//*              viewHolders.qty_et.setSelection(viewHolders.qty_et.getText().length());
                            cartdigit=cartdigit+1;
                            cartNumbeer = String.valueOf(cartdigit);
                            sales_activity.setItemList( cartNumbeer);*//*
                            }

                        status[0] = true;

                    }}

           *//*     else
                    {
                        Log.e("empty","elseEntry");
                        if(!list.isPostion())
                        {

                        }
                        else {
                            if(!viewHolders.qty_et.getText().toString().equals("0"))
                            {
                                list.setPostion(false);
                            //   Log.e("watchWord1",cartdigit+" ");
                            cartdigit--;
                         }

                            if(cartdigit==0)
                            {
                                cartNumbeer = String.valueOf("0");
                            }
                            else
                            {
                                cartNumbeer = String.valueOf(cartdigit);
                            }
                            //  }
                            status[0] = false;
                            // Sales_Activity sales_activity = new Sales_Activity();
                            sales_activity.setItemList( cartNumbeer);
                        } }*//*}});*/

        viewHolders.order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.equals(viewHolders.qty_et.getText().toString(),"0") && !TextUtils.isEmpty(viewHolders.qty_et.getText().toString()))
                {
                    list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                }
                Intent intent= new Intent(context, ItemDetail_orderSub.class);
                intent.putExtra("currentPosition",String.valueOf(i));
                intent.putExtra("list",  list);
                fragment.startActivityForResult(intent,5);
            }});

        viewHolders.itemDetail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInput=true;
                if(!TextUtils.equals(viewHolders.qty_et.getText().toString(),"0") && !TextUtils.isEmpty(viewHolders.qty_et.getText().toString()))
                {
                    list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                }
                Intent intent= new Intent(context, Purchase_ItemDetail_act.class);
                intent.putExtra("currentPosition",String.valueOf(i));
                intent.putExtra("list",  list);
                fragment.startActivityForResult(intent,6);
            }
        });
        if(editInput)
        {
        if(!TextUtils.isEmpty(viewHolders.qty_et.getText().toString()))
        {
            if(!TextUtils.equals(viewHolders.qty_et.getText().toString(),"0"))
            {
            String cartNumber = null;
            int checkInput=Integer.parseInt(viewHolders.qty_et.getText().toString());
            if(!viewHolders.qty_et.getText().toString().isEmpty() && checkInput!=0) {
                status[0] =true;
                viewHolders.qty_et.requestFocus();
                if(checkInput!=0) {
                    if(!list.isPostion()) {
                        cartdigit=cartdigit+1;
                        cartNumbeer = String.valueOf(cartdigit);
                        viewHolders.qty_et.setSelection(viewHolders.qty_et.getText().length());
                        //cartItemNumber_tv.setText(cartNumbeer);
                            sales_activity.setItemList(cartNumbeer);
                            list.setPostion(true);
                    } } } }}}

        viewHolders.qty_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               if(!viewHolders.qty_et.getText().toString().isEmpty())
                {if (!hasFocus && status[0]) {
                    if (i == 0 && !checkZero[0] && !list.isSetOrNot() ) {

                        list.setBasic(i);
                        list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                        list.setCart(staticItemList.size());
                        list.setSetOrNot(true);
                        staticItemList.add(i,list);
                        Log.e("mainId", "mainIf");
                        checkZero[0] = true;
                        saveToSp(staticItemList);
                    }

                    else if (list.getBasic() == i) {
                        staticItemList.set(list.getCart(), list);
                        list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                        Log.e("subIf", "subIf");
                        saveToSp(staticItemList);
                    }
                    else {
                        list.setBasic(i);
                        list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                        list.setCart(staticItemList.size());
                        staticItemList.add(list);
                        saveToSp(staticItemList);
                        Log.e("elseadapter", "else");
                        Log.e("elseadapter", staticItemList.size()+"");
                    }

                    status[0] = false;
                }}

                else {
                        if( i == 0  && list.isSetOrNot() && viewHolders.qty_et.getText().toString().isEmpty())
                    {
                        list.setSetOrNot(false);
                        staticItemList.remove(list.getCart());
                        saveToSp(staticItemList);
                        checkZero[0] = false;
                        Log.e("hasfocus", "hasfocusIf");
                    }
                     else if (list.getBasic() == i && i!=0 && viewHolders.qty_et.getText().toString().isEmpty()) {
                      list.setBasic(0);
                      list.setCart(0);
                        staticItemList.remove(list.getCart());
                            saveToSp(staticItemList);
                        Log.e("hasfocus", "hasfocuselse");
                    }
                } }
        }); }

    private void saveToSp(ArrayList<Brand_SubBrand_Model.Item> allList) {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticItemList", json);
        prefsEditor.apply();
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                allList.clear();
                final FilterResults results = new FilterResults();
                if(constraint.length() == 0){
                    allList.addAll(allList2);
                }
                else{

                    final String filterPattern =constraint.toString().toLowerCase().trim();
                    for(Brand_SubBrand_Model.Item listcountry : allList2){
                        String nameIs=listcountry.getItmName();
                        // String mobile=listcountry.getMobile();

                        if(nameIs.toLowerCase().contains(filterPattern)){
                            allList.add(listcountry);
                        }
                    }
                }
                results.values = allList;
                results.count = allList.size();
                return results; }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
             allList = (ArrayList<Brand_SubBrand_Model.Item>) results.values;
                notifyDataSetChanged();
            }}; }

 /*   @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allList = allList2;
                } else {
                    allList.clear();
                    ArrayList<Brand_SubBrand_Model.Item> filteredList = new ArrayList<>();
                    for (Brand_SubBrand_Model.Item row : allList2) {
                        if (row.getItmName().toLowerCase().contains(charString.toLowerCase().trim()) ) {
                            filteredList.add(row);
                        }
                    }
                    allList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = allList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allList = (ArrayList<Brand_SubBrand_Model.Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }*/



    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Brand_SubBrand_Model.Item list = (Brand_SubBrand_Model.Item) editText.getTag();
            boolean[] status= (boolean[]) editText.getTag(R.id.qty_et);


            if(!TextUtils.isEmpty(s.toString()))
            {

                int checkInput=Integer.parseInt(s.toString());

                if(checkInput!=0)
                {
                    if(!list.isPostion() )
                    {
                        list.setPostion(true);
                        cartdigit=cartdigit+1;
                        cartNumbeer = String.valueOf(cartdigit);
                        sales_activity.setItemList( cartNumbeer);
                    }
                    status[0] = true;

                }}

             else
            {
                if(!list.isPostion())
                {
                }
                else {
                    if(!TextUtils.equals(editValue,"0"))
                    {
                        list.setPostion(false);
                        cartdigit--;
                    }

                    if(cartdigit==0)
                    {
                        cartNumbeer = String.valueOf("0");
                    }
                    else
                    {
                        cartNumbeer = String.valueOf(cartdigit);
                    }
                    //  }
                    status[0] = false;
                    // Sales_Activity sales_activity = new Sales_Activity();
                    sales_activity.setItemList( cartNumbeer);
                } } }

        @Override
        public void afterTextChanged(Editable s) {
        }}


 /*   public void updateList(ArrayList<Brand_SubBrand_Model.Item> list){
        allList = list;
        notifyDataSetChanged();
    }*/

}
