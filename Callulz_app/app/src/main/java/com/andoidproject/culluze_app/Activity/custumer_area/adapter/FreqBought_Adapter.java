package com.andoidproject.culluze_app.Activity.custumer_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import com.andoidproject.culluze_app.Activity.custumer_area.activity.Purchase_ItemDetail_act;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.ItemDetail_orderSub;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.cartdigit;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.staticFreqList;
import static com.andoidproject.culluze_app.Activity.custumer_area.fragment.Items_OrderFrag.editInput;

public class FreqBought_Adapter extends RecyclerView.Adapter<FreqBought_Adapter.ViewHolders> implements Filterable {

    Context context;
    private ArrayList<FrequentlyModel>allList=new ArrayList<>();
    private ArrayList<FrequentlyModel>allList2=new ArrayList<>();
    private ArrayList<FrequentlyModel> subLists = new ArrayList<>();
    private Fragment fragment;
    private  String cartNumbeer = "0";
    Sales_Activity sales_activity = new Sales_Activity();
    String editValue;



    public FreqBought_Adapter(Context context,Fragment fragment)
    {
        cartdigit=staticItemList.size()+staticFreqList.size();
        this.fragment = fragment;
        this.context=context;
        this.allList2=getHistoryList(loginModel.getUserId()+"freqBought");
        allList.addAll(allList2);
        this.subLists.addAll(staticFreqList);
    }

    public FreqBought_Adapter(Context context, ArrayList<FrequentlyModel> allFreqBought,Fragment fragment) {

        this.allList2=allFreqBought;
        allList.addAll(allList2);
        this.context=context;
        this.subLists.addAll(staticFreqList);
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.freqbought_view,viewGroup,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i)
    {
         FrequentlyModel list=allList.get(i);
        if(subLists.size()!=0)
        {
            boolean setData=true;
            for (int j = 0; j < subLists.size(); j++)
            {
                FrequentlyModel model = subLists.get(j);
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
                    staticFreqList.set(j,model);
                    viewHolders.parentLl.setVisibility(View.GONE);
                    setData=false;
                }
            }

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


    /*    FrequentlyModel models = allList.get(i);
        Log.e("response", models.getId()+" , "+ models.getItmCd()+" , "+models.getItmName()+" , "+models.getItmBarCd()+" , "+models.getCatgry()+", "+models.getUnit()+" , "+models.getBrand()+
                " , "+models.getRTSWT()+" , "+models.getWHSWT()+", "+models.getDTSWT()+",  "+models.getEXSWT()+", "+models.getAvilStk()+" , "+models.getTaxPerc()+" , "+models.getTaxId());


        viewHolders.order_ll.setOnClickListener(new View.OnClickListener()
        {
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
        });*/

    }

    @Override
    public int getItemCount() {
        return allList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        LinearLayout order_ll,parentLayout,free_ll,parentLl;
        ImageView itemDetail_img;
        TextView productName,free_tv,stock_tvv,taxper_tv,mrp_tv,rate_tv;
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
            stock_tvv=itemView.findViewById(R.id.stockfreq_tv);
            taxper_tv=itemView.findViewById(R.id.taxper_tv);
            mrp_tv=itemView.findViewById(R.id.mrp_tv);
            rate_tv=itemView.findViewById(R.id.rate_tv);

            MyTextWatcher textWatcher = new MyTextWatcher(qty_et);
            qty_et.addTextChangedListener(textWatcher);

        }}

    public ArrayList<FrequentlyModel> getHistoryList(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<FrequentlyModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void setTextView_two(final ViewHolders viewHolders, final FrequentlyModel list, final int i)
    {
        final boolean[] status = {false};
        final boolean[] checkZero = {false};


        viewHolders.qty_et.setTag(list);
        viewHolders.qty_et.setTag(R.id.qty_et,status);
        editValue=viewHolders.qty_et.getText().toString();

        viewHolders.productName.setText(list.getItmName());
        if(list.getQuantity()!=0)
        { viewHolders.qty_et.setText(list.getQuantity()+"");}
        else
        {viewHolders.qty_et.setText("");}

        viewHolders.free_tv.setText(list.getFreetv());

        Float avlStock=list.getAvilStk();

        if(avlStock<=0)
        {
            viewHolders.stock_tvv.setText("STOCK: Out of Stock");
            viewHolders.stock_tvv.setTextColor(Color.RED);
            viewHolders.qty_et.setFocusable(false);
            viewHolders.qty_et.setClickable(false);
            viewHolders.qty_et.setCursorVisible(false);
            viewHolders.qty_et.setEnabled(false);
        }
        else
        {
            viewHolders.stock_tvv.setText("STOCK: "+list.getAvilStk());
            viewHolders.stock_tvv.setTextColor(Color.BLACK);
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

       /* viewHolders.qty_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            private Timer timer=new Timer();
            private final long DELAY = 500;

            @Override
            public void afterTextChanged(final Editable s) {

                if(!TextUtils.isEmpty(s.toString()))
                {
                    int checkInput=Integer.parseInt(s.toString());
                    if(checkInput!=0)
                    {
                        if(!list.getPosition() )
                        {
                            viewHolders.qty_et.setSelection(viewHolders.qty_et.getText().length());
                            cartdigit=cartdigit+1;
                            cartNumbeer = String.valueOf(cartdigit);
                            sales_activity.setItemList( cartNumbeer);
                            list.setPosition(true);
                        }
                        status[0] = true;
                    }}

                else
                {
                    if(!list.getPosition())
                    {

                    }
                    else {
                        if(!viewHolders.qty_et.getText().toString().equals("0")) {
                            list.setPosition(false);
                            Log.e("watchWord1", cartdigit + " ");
                            cartdigit--;
                            Log.e("watchWord2", cartdigit + " ");
                        }
                        if(cartdigit==0)
                        {
                            cartNumbeer = String.valueOf("0");
                        }
                        else
                        {
                            cartNumbeer = String.valueOf(cartdigit);
                            Log.e("cartDigit",cartdigit+"");
                        }
                        //  }
                        status[0] = false;
                        sales_activity.setItemList( cartNumbeer);
                    }
                }
            }});*/

        viewHolders.order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInput=true;
                if(!TextUtils.equals(viewHolders.qty_et.getText().toString(),"0") && !TextUtils.isEmpty(viewHolders.qty_et.getText().toString()))
                {
                    list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                }
                Intent intent= new Intent(context, ItemDetail_orderSub.class);
                intent.putExtra("currentPosition2",String.valueOf(i));
                intent.putExtra("list2",list);
                fragment.startActivityForResult(intent,7);
            }
        });

        viewHolders.itemDetail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInput=true;
                if(!TextUtils.equals(viewHolders.qty_et.getText().toString(),"0") && !TextUtils.isEmpty(viewHolders.qty_et.getText().toString()))
                {
                    list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                }
                Intent intent= new Intent(context, Purchase_ItemDetail_act.class);
                intent.putExtra("currentPosition2",String.valueOf(i));
                intent.putExtra("list2", list);
                fragment.startActivityForResult(intent,8);
            }
        });

        if(editInput)
        {
        if(!TextUtils.isEmpty(viewHolders.qty_et.getText().toString()))
        {
            if(!TextUtils.equals(viewHolders.qty_et.getText().toString(),"0"))
            {int checkInput=Integer.parseInt(viewHolders.qty_et.getText().toString());
            if(!viewHolders.qty_et.getText().toString().isEmpty() && checkInput!=0) {
                status[0] =true;
                viewHolders.qty_et.requestFocus();
                if(checkInput!=0) {
                    if(!list.getPosition()) {
                        cartdigit=cartdigit+1;
                        cartNumbeer = String.valueOf(cartdigit);
                        viewHolders.qty_et.setSelection(viewHolders.qty_et.getText().length());
                        sales_activity.setItemList(cartNumbeer);
                        list.setPosition(true);
                    } } }}}}

        viewHolders.qty_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!viewHolders.qty_et.getText().toString().isEmpty())
                {if (!hasFocus && status[0]) {

               /*     Log.e("listGetBasic",list.getBasic()+"");
                    Log.e("listGetCart",list.getCart()+"");*/
                    if (i == 0 && !checkZero[0] && !list.isSetOrNot() ) {

                        list.setBasic(i);
                        list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                        list.setCart(staticFreqList.size());
                        list.setSetOrNot(true);
                        staticFreqList.add(i,list);
                        Log.e("mainId", "mainIf");
                        checkZero[0] = true;
                        saveToSp(staticFreqList);
                    }

                    else if (list.getBasic() == i) {
                        staticFreqList.set(list.getCart(), list);
                        list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                        Log.e("subIf", "subIf");
                        saveToSp(staticFreqList);
                    }
                    else {
                        list.setBasic(i);
                        list.setQuantity(Integer.parseInt(viewHolders.qty_et.getText().toString()));
                        list.setCart(staticFreqList.size());
                        staticFreqList.add(list);
                        saveToSp(staticFreqList);
                        Log.e("elseadapter", "else");

                    }

                  /*  Gson gson;
                    SharedPreferences preferences;
                    preferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefsEditor = preferences.edit();
                    gson = new Gson();
                    String json = gson.toJson(allList);
                    prefsEditor.putString("allItems", json);
                    prefsEditor.apply();*/
                    status[0] = false;
                }}

                else {
                    if( i == 0  && list.isSetOrNot() && viewHolders.qty_et.getText().toString().isEmpty())
                    {
                        list.setSetOrNot(false);
                        staticFreqList.remove(list.getCart());
                        saveToSp(staticFreqList);
                        checkZero[0] = false;
                        Log.e("hasfocus", "hasfocusIf");
                    }
                    else if (list.getBasic() == i && i!=0 && viewHolders.qty_et.getText().toString().isEmpty()) {
                        list.setBasic(0);
                        list.setCart(0);
                        staticFreqList.remove(list.getCart());
                        saveToSp(staticFreqList);
                        Log.e("hasfocus", "hasfocuselse");
                    }
                }
            }
        });
    }


    private void saveToSp(ArrayList<FrequentlyModel> allList) {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticFreqList", json);
        prefsEditor.apply();
    }

    public ArrayList<FrequentlyModel> getItemListModel()
    {
        return allList;
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
                    for(FrequentlyModel listcountry : allList2){
                        String nameIs=listcountry.getItmName();
                        // String mobile=listcountry.getMobile();

                        if(nameIs.toLowerCase().contains(filterPattern)){
                            allList.add(listcountry);
                        }
                    }
                }
                results.values = allList;
                results.count = allList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                allList = (ArrayList<FrequentlyModel>) results.values;
                notifyDataSetChanged();
            }}; }

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
            FrequentlyModel list = (FrequentlyModel) editText.getTag();
            boolean[] status= (boolean[]) editText.getTag(R.id.qty_et);
            if(!TextUtils.isEmpty(s.toString()))
            {
                int checkInput=Integer.parseInt(s.toString());
                if(checkInput!=0)
                {
                    if(!list.getPosition() )
                    {
                        cartdigit=cartdigit+1;
                        cartNumbeer = String.valueOf(cartdigit);
                        sales_activity.setItemList( cartNumbeer);
                        list.setPosition(true);
                    }
                    status[0] = true; }}
            else
            {
                if(!list.getPosition())
                {

                }
                else {
                    if(!TextUtils.equals(editValue,"0")) {
                        list.setPosition(false);
                        Log.e("watchWord1", cartdigit + " ");
                        cartdigit--;
                        Log.e("watchWord2", cartdigit + " ");
                    }
                    if(cartdigit==0)
                    {
                        cartNumbeer = String.valueOf("0");
                    }
                    else
                    {
                        cartNumbeer = String.valueOf(cartdigit);
                        Log.e("cartDigit",cartdigit+"");
                    }
                    //  }
                    status[0] = false;
                    sales_activity.setItemList( cartNumbeer);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


}
