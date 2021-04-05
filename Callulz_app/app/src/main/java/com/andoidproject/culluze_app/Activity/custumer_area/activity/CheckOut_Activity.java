package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.LoginModel;
import com.andoidproject.culluze_app.Activity.model.OfflineSales_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.Activity.utils.DataBase;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userId;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.staticFreqList;


public class CheckOut_Activity extends Base_Activity implements View.OnClickListener {

    TextView toolbarText,discount_tv,texDetail_tv;
    ImageView back_img;
    EditText discount_et,remark_et;
    View review_view,discount_view;
    TextView customerName,customerMobile,customerAddress;
    TextView totalAmount,taxdetail_tv,subtotal_tv,grossAmount_tv,discountAmount,totalAll;
    Button checkOut_btn;
    ProgressBar progressBar;
    ArrayList<SubmitOrder_Model.SalesDet>list=new ArrayList<>();
    String type,sessionId;
    RecyclerView recyclerSales,recyclerFreq;
    boolean controler=true;
    ArrayList<SubmitSales_Model.SalesDet>list2=new ArrayList<>();
    ArrayList<OfflineSales_Model.BrandOffline_Model>offlineSaveList=new ArrayList<>();
    OfflineSales_Model offObject;
    PandingAmount_Adapter adapter;
    int j=0;
    LinearLayout lineartwo,linearOne;

    float toApiTotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getIntent().getStringExtra("type");

        if(TextUtils.equals(type,"offline") || TextUtils.equals(type,"offOrder"))
        {
            setContentView(R.layout.pending_view);
        }

        else if(TextUtils.equals(type,"pending"))
        {
            setContentView(R.layout.pending_view);
        }
        else {
            setContentView(R.layout.activity_check_out_);
        }

        init();
    }


    //====================================inti===============================================
    private  void init()
    {

        toolbarText= findViewById(R.id.toolbarText);
        toolbarText.setText("Check Out");
        back_img= findViewById(R.id.back_img);
        discount_et= findViewById(R.id.discount_et);
        remark_et= findViewById(R.id.remark_et);
        review_view= findViewById(R.id.review_view);
        discount_view= findViewById(R.id.discount_view);
        discount_tv= findViewById(R.id.discount_tv);
        texDetail_tv= findViewById(R.id.texDetail_tv);
        totalAmount= findViewById(R.id.totalAmount);
        checkOut_btn= findViewById(R.id.checkOut_btn);
        progressBar= findViewById(R.id.progressBar);
        recyclerSales= findViewById(R.id.recyclerSales);
        recyclerFreq= findViewById(R.id.recyclerFreq);

        customerName= findViewById(R.id.customerName);
        customerMobile= findViewById(R.id.customerMobile);
        customerAddress= findViewById(R.id.customerAddress);

        lineartwo= findViewById(R.id.lineartwo);
        linearOne= findViewById(R.id.linearOne);

        taxdetail_tv= findViewById(R.id.taxdetail_tv);
        subtotal_tv =findViewById(R.id.subtotal_tv);
        grossAmount_tv =findViewById(R.id.grossAmount_tv);
        discountAmount =findViewById(R.id.discountAmount);
        totalAll =findViewById(R.id.totalAll);

        sessionId = getIntent().getStringExtra("totalMoney");
        totalAmount.setText("₹" + sessionId);
        checkOut_btn.setOnClickListener(this);

        customerName.setText(custmer.getCustmrName());
        customerMobile.setText(custmer.getMobl());
        customerAddress.setText(custmer.getAddress());

        back_img.setOnClickListener(this);


        if(TextUtils.equals(type,"offline"))
        {
            toolbarText.setText("Save Sales");
            checkOut_btn.setVisibility(View.GONE);
            offObject= (OfflineSales_Model) getIntent().getSerializableExtra("offlineList");
            totalAmount.setText("₹"+offObject.getTotalAmount());
            customerName.setText(offObject.getUserName());
            customerMobile.setText(offObject.getUserMobile());
            customerAddress.setText(offObject.getUserAddress());

            ArrayList<OfflineSales_Model.BrandOffline_Model>brand= (ArrayList<OfflineSales_Model.BrandOffline_Model>) offObject.getSalesDet();
            Log.e("brand",brand.size()+"");

            recyclerFreq.setLayoutManager(new LinearLayoutManager(this));
            recyclerFreq.setItemAnimator(new DefaultItemAnimator());
            adapter= new PandingAmount_Adapter(brand,"offline");
            recyclerFreq.setAdapter(adapter);
        }

        else if(TextUtils.equals(type,"offOrder"))
        {
            toolbarText.setText("Save Orders");
            checkOut_btn.setVisibility(View.GONE);
            lineartwo.setVisibility(View.GONE);
            linearOne.setVisibility(View.GONE);
            offObject= (OfflineSales_Model) getIntent().getSerializableExtra("offlineList");
            totalAmount.setText("₹"+offObject.getTotalAmount());
            customerName.setText(offObject.getUserName());
            customerMobile.setText(offObject.getUserMobile());
            customerAddress.setText(offObject.getUserAddress());

            ArrayList<OfflineSales_Model.BrandOffline_Model>brand= (ArrayList<OfflineSales_Model.BrandOffline_Model>) offObject.getSalesDet();
            Log.e("brand",brand.size()+"");

            recyclerFreq.setLayoutManager(new LinearLayoutManager(this));
            recyclerFreq.setItemAnimator(new DefaultItemAnimator());
            adapter= new PandingAmount_Adapter(brand,"offline");
            recyclerFreq.setAdapter(adapter);
        }


        else if(TextUtils.equals(type,"purchase")) {
            discount_tv.setOnClickListener(this);
            texDetail_tv.setOnClickListener(this);

            //  final String sessionId = getIntent().getStringExtra("totalMoney");

            toApiTotal = Float.parseFloat(sessionId);

            totalAll.setText("₹" + sessionId);
            focusEdit();

            final float taxCalculation = Float.parseFloat(sessionId) * 18 / 100;

            final float remainingAmount = Float.parseFloat(sessionId) - taxCalculation;

            taxdetail_tv.setText("₹" + String.format("%.2f", taxCalculation));


            subtotal_tv.setText("₹" + String.format("%.2f", remainingAmount));
            grossAmount_tv.setText("₹" + String.format("%.2f", remainingAmount));

            discount_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && !discount_et.getText().toString().isEmpty()) {
                        float flt = Float.parseFloat(s.toString());
                        if (flt > 99) {
                            Toast.makeText(CheckOut_Activity.this, "Cash Discount limit is Exceeded", Toast.LENGTH_SHORT).show();

                        } else {
                            DecimalFormat precision = new DecimalFormat("0.00");

                            float editCalculation = remainingAmount * flt / 100;
                            // discountAmount.setText(editCalculation+"");
                            float subTotalCalculation = remainingAmount - editCalculation;
                            //  subtotal_tv.setText(subTotalCalculation+"");
                            String finalTotal = String.valueOf(subTotalCalculation + taxCalculation);

                            toApiTotal = Float.parseFloat(String.format("%.2f", subTotalCalculation + taxCalculation));

                            totalAmount.setText("₹" + String.format("%.2f", subTotalCalculation + taxCalculation));
                            totalAll.setText("₹" + String.format("%.2f", subTotalCalculation + taxCalculation));
                            subtotal_tv.setText("₹" + String.format("%.2f", subTotalCalculation));
                            discountAmount.setText("₹" + String.format("%.2f", editCalculation));
                        }
                    } else {
                        float editCalculation = remainingAmount * 0 / 100;
                        // discountAmount.setText(editCalculation+"");
                        float subTotalCalculation = remainingAmount - editCalculation;
                        //  subtotal_tv.setText(subTotalCalculation+"");
                        String finalTotal = String.valueOf(subTotalCalculation + taxCalculation);
                        toApiTotal = Float.parseFloat(String.format("%.2f", subTotalCalculation + taxCalculation));
                        totalAmount.setText("₹" + String.format("%.2f", subTotalCalculation + taxCalculation));
                        totalAll.setText("₹" + String.format("%.2f", subTotalCalculation + taxCalculation));
                        subtotal_tv.setText("₹" + String.format("%.2f", subTotalCalculation));
                        discountAmount.setText("₹" + String.format("%.2f", editCalculation));
                    }
                }
            });
        }
        else
        {
            toolbarText.setText("Submit Sales");

            if(staticFreqList.size()!=0)
            {
                controler=true;
                recyclerFreq.setLayoutManager(new LinearLayoutManager(this));
                recyclerFreq.setItemAnimator(new DefaultItemAnimator());
                adapter= new PandingAmount_Adapter(staticFreqList);
                recyclerFreq.setAdapter(adapter);
            }
            else {
                secondRecycler();
            }

        }
    }
    //=====================================editAnimatioin======================================
    private void focusEdit()
    {
        discount_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    discount_view.setElevation(2f);
                    lineAnimation(discount_view);
                }
                else
                {

                    Reverse_lineAnimation(discount_view);
                } }});


        remark_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    review_view.setElevation(2f);
                    lineAnimation(review_view);
                }
                else {

                    Reverse_lineAnimation(review_view);
                } }});
    }

    //=====================================Click======================================
    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }

        if(v==discount_tv)
        {
            setDialog("Discount","Item Discount-","0.0","Bill Discount-",discountAmount.getText().toString());
        }

        if(v==texDetail_tv)
        {
            setDialog("Tax Details","IGST(18%)",taxdetail_tv.getText().toString(),"","");
        }

        if(v==checkOut_btn)
        {
            // enableProgress(progressBar);
            checkConnection();
            if(internet)
            {
                if(TextUtils.equals(type,"purchase")) {
                    callSubmitOrderApi();

                }
                else {
                    callSaveOrderApi();
                }
            }
            else
            {
                disableProgress(progressBar);
                if(TextUtils.equals(type,"purchase")) {
                    OfflineSales_Model off=saveOffline();
                    SubmitOrder_Model saveOrder=  insertSubmitOrder();
                    Gson gson1 = new Gson();
                    String toStoreObject = gson1.toJson(saveOrder, SubmitOrder_Model.class);
                    String saveOffline = gson1.toJson(off, OfflineSales_Model.class);
                    boolean done= db.addOrderITem(toStoreObject,saveOffline,loginModel.getUserId());
                    callToast(this,"Order save offline Successfully");
                    finishActivity();
                    Log.e("done",done+"");
                }
                else {
                    OfflineSales_Model off=saveOffline();
                    SubmitSales_Model saveSales=  insertSubmitSales();
                    Gson gson1 = new Gson();
                    String toStoreObject = gson1.toJson(saveSales, SubmitSales_Model.class);
                    String saveOffline = gson1.toJson(off, OfflineSales_Model.class);
                    boolean done= db.addSalesSave(toStoreObject,saveOffline,loginModel.getUserId());
                    callToast(this,"Sales save offline Successfully");
                    finishActivity();
                    Log.e("done",done+"");
                }}}}

    //=====================================setDialog======================================
    private void setDialog(String Title,String titleone,String oneEdit,String titletwo,String twoEdit)
    {
        LinearLayout subTwo_ll;
        TextView subtitle_one,subTitle_two,subEdit_one,subEdit_two,dialog_title;
        final Dialog customDialog;
        customDialog=new Dialog(this);
        LayoutInflater customInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customLayout=customInflater.inflate(R.layout.simple_dialog, (ViewGroup) findViewById(R.id.root));
        customDialog.setContentView(customLayout);
        ViewGroup.LayoutParams layoutParams2= customLayout.getLayoutParams();
        layoutParams2.height=500;
        layoutParams2.width=750;
        customDialog.show();
        subTwo_ll=customLayout.findViewById(R.id.subTwo_ll);
        subtitle_one=customLayout.findViewById(R.id.subtitle_one);
        subTitle_two=customLayout.findViewById(R.id.subTitle_two);
        subEdit_one=customLayout.findViewById(R.id.subEdit_one);
        subEdit_two=customLayout.findViewById(R.id.subEdit_two);
        dialog_title=customLayout.findViewById(R.id.dialog_title);
        dialog_title.setText(Title);
        subtitle_one.setText(titleone);
        subEdit_one.setText(oneEdit);
        subTitle_two.setText(titletwo);
        subEdit_two.setText(twoEdit);
    }

    //================================callSubmitOrderApi===========================================
    private void callSubmitOrderApi()
    {

        SubmitOrder_Model submitOrder_model= new SubmitOrder_Model();
        submitOrder_model.setCustmrId(custmer.getId());
        submitOrder_model.setUsrId(loginModel.getUserId());
        submitOrder_model.setTotSaleAmt(toApiTotal);
        for(int i=0;i<staticItemList.size();i++)
        {
            SubmitOrder_Model.SalesDet cartList = new SubmitOrder_Model.SalesDet();
            Brand_SubBrand_Model.Item model=staticItemList.get(i);
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            String customerRate=custmer.getCustRate();
            if(customerRate.equals("ITM-RT"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getRTSWT()));
            }
            else if(customerRate.equals("ITM-WH"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getWHSWT()));
            }
            else if(customerRate.equals("ITM-DI"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getDTSWT()));
            }
            else if(customerRate.equals("ITM-EX"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getEXSWT()));
            }
            list.add(cartList);
        }

        for(int i=0;i<staticFreqList.size();i++)
        {


            SubmitOrder_Model.SalesDet cartList = new SubmitOrder_Model.SalesDet();
            FrequentlyModel model=staticFreqList.get(i);
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            String customerRate=custmer.getCustRate();
            if(customerRate.equals("ITM-RT"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getRTSWT()));
            }
            else if(customerRate.equals("ITM-WH"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getWHSWT()));
            }
            else if(customerRate.equals("ITM-DI"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getDTSWT()));
            }
            else if(customerRate.equals("ITM-EX"))
            {
                cartList.setUnitPriceWiTax(String.valueOf(model.getEXSWT()));
            }
            list.add(cartList);
        }

        submitOrder_model.setSalesDet(list);

        Log.e("fdsfsdsdfsdfdfs_Order",submitOrder_model+"");

        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<SubmitOrder_Model> call=apiInterface.submitOrder(submitOrder_model);

        call.enqueue(new Callback<SubmitOrder_Model>()
        {
            @Override
            public void onResponse(Call<SubmitOrder_Model> call, Response<SubmitOrder_Model> response) {

                callToast(CheckOut_Activity.this,"Order place Successfully");
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishActivity();
                    }
                },2000);
            }

            @Override
            public void onFailure(Call<SubmitOrder_Model> call, Throwable t) {
                disableProgress(progressBar);
                checkConnection();
                if(!internet)
                {
                    callToast(CheckOut_Activity.this,"Internet Connection problem");
                }
            }
        });}

    //===============================saveOrderApi===================================
    private void callSaveOrderApi()
    {
       SubmitSales_Model saveSales=  insertSubmitSales();

       /* SubmitSales_Model saveSales= new SubmitSales_Model();
        saveSales.setCustmrId(custmer.getId());
        saveSales.setUsrId(loginModel.getUserId());
        saveSales.setTotSaleAmt(Float.parseFloat(sessionId));
        saveSales.setGrandTotAmt(1);
        saveSales.setOtherCharge(1);
        saveSales.setRoundingAmt(1);
        saveSales.setTranOverheads(1);

        for(int i=0;i<staticItemList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            Brand_SubBrand_Model.Item model=staticItemList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list2.add(cartList);
        }

        for(int i=0;i<staticFreqList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            FrequentlyModel model=staticFreqList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list2.add(cartList);
        }

        saveSales.setSalesDet(list2);*/
        Log.e("fdsfsdsdfsdfdfs_Order",saveSales+"");


        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<SubmitSales_Model> call=apiInterface.submitsales(saveSales);
        call.enqueue(new Callback<SubmitSales_Model>()
        {
            @Override
            public void onResponse(Call<SubmitSales_Model> call, Response<SubmitSales_Model> response) {

                callToast(CheckOut_Activity.this,"Order Save Successfully");
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishActivity();
                    }
                },1000);
            }

            @Override
            public void onFailure(Call<SubmitSales_Model> call, Throwable t) {
                disableProgress(progressBar);
                callToast(CheckOut_Activity.this,"Server Error");
            }
        });}



        //==============================SaveToSP======================================================

    private void saveToSp(ArrayList<Brand_SubBrand_Model.Item> allList) {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticItemList", json);
        prefsEditor.apply();
    }

    private void saveToSp2(ArrayList<FrequentlyModel> allList)
    {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticFreqList", json);
        prefsEditor.apply();
    }

    //================================PandingAmount_RecyclerAdapter==============================
    private class PandingAmount_Adapter extends RecyclerView.Adapter<PandingAmount_Adapter.ViewHolder>
    {

        ArrayList<OfflineSales_Model.BrandOffline_Model> brand;
        String offline="null";

        public PandingAmount_Adapter(ArrayList<Brand_SubBrand_Model.Item> staticItemList, int ss) {
        }

        public PandingAmount_Adapter(ArrayList<FrequentlyModel> staticFreqList) {
        }

        public PandingAmount_Adapter(ArrayList<OfflineSales_Model.BrandOffline_Model> brand, String offline) {
            this.brand=brand;
            this.offline=offline;
        }


        @NonNull
        @Override
        public PandingAmount_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.panding_adapter_view,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PandingAmount_Adapter.ViewHolder viewHolder, int post) {


            if(TextUtils.equals(offline,"offline"))
            {
                OfflineSales_Model.BrandOffline_Model modelObject=brand.get(post);
                float total = 0;
                String rate = "";
                j=post+1;
                viewHolder.sno_tv.setText(j+"");
                viewHolder.item_name.setText(modelObject.getItmName());
                viewHolder.qty_tv.setText(modelObject.getQuantity()+" Qty");
                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    total=modelObject.getQuantity()* modelObject.getRTSWT();
                    rate= String.valueOf(modelObject.getRTSWT());
                }
                else if(customerRate.equals("ITM-WH"))
                {
                    total=modelObject.getQuantity()* modelObject.getWHSWT();
                    rate= String.valueOf(modelObject.getWHSWT());
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    total=modelObject.getQuantity()* modelObject.getDTSWT();
                    rate= String.valueOf(modelObject.getDTSWT());
                }
                else if(customerRate.equals("ITM-EX"))
                {
                    total=modelObject.getQuantity()* modelObject.getEXSWT();
                    rate= String.valueOf(modelObject.getEXSWT());
                }
                viewHolder.rate_textv.setText("₹"+rate);
            }

         else if(controler)
            {
                float total = 0;
                String rate = "";
                FrequentlyModel model=staticFreqList.get(post);
                j=post+1;
                viewHolder.sno_tv.setText(j+"");
                viewHolder.item_name.setText(model.getItmName());
                viewHolder.qty_tv.setText(model.getQuantity()+" Qty");
                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    total=model.getQuantity()* model.getRTSWT();
                    rate= String.valueOf(model.getRTSWT());
                }
                else if(customerRate.equals("ITM-WH"))
                {
                    total=model.getQuantity()* model.getWHSWT();
                    rate= String.valueOf(model.getWHSWT());
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    total=model.getQuantity()* model.getDTSWT();
                    rate= String.valueOf(model.getDTSWT());
                }
                else if(customerRate.equals("ITM-EX"))
                {
                    total=model.getQuantity()* model.getEXSWT();
                    rate= String.valueOf(model.getEXSWT());
                }
                viewHolder.rate_textv.setText("₹"+rate);

                if(staticFreqList.size()-1==post)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            secondRecycler();
                        }
                    },100);
                }

            }

            else {
                float total = 0;
                String rate = "";
                Brand_SubBrand_Model.Item model=staticItemList.get(post);
                j=j+1;
                viewHolder.sno_tv.setText(j+"");
                viewHolder.item_name.setText(model.getItmName());
                viewHolder.qty_tv.setText(model.getQuantity()+" Qty");
                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    total=model.getQuantity()* model.getRTSWT();
                    rate= String.valueOf(model.getRTSWT());
                }
                else if(customerRate.equals("ITM-WH"))
                {
                    total=model.getQuantity()* model.getWHSWT();
                    rate= String.valueOf(model.getWHSWT());
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    total=model.getQuantity()* model.getDTSWT();
                    rate= String.valueOf(model.getDTSWT());
                }
                else if(customerRate.equals("ITM-EX"))
                {
                    total=model.getQuantity()* model.getEXSWT();
                    rate= String.valueOf(model.getEXSWT());
                }
                viewHolder.rate_textv.setText("₹"+total);
            }

        }

        @Override
        public int getItemCount() {
            int counting=0;
            if(TextUtils.equals(offline,"offline"))
            {
               counting=brand.size();
            }

            else if(controler)
            {
                counting=staticFreqList.size();
            }
            else {
                counting=staticItemList.size();
            }
            return counting;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView sno_tv,item_name,qty_tv,rate_textv;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                sno_tv=itemView.findViewById(R.id.sno_tv);
                item_name=itemView.findViewById(R.id.item_name);
                qty_tv=itemView.findViewById(R.id.qty_tv);
                rate_textv=itemView.findViewById(R.id.rate_textv);
            }
        }
    }

    //================================SecondRecycler===============================
    private void secondRecycler()
    {
        controler=false;
        int ss=0;
        recyclerSales.setLayoutManager(new LinearLayoutManager(this));
        recyclerSales.setItemAnimator(new DefaultItemAnimator());
        PandingAmount_Adapter adapter= new PandingAmount_Adapter(staticItemList,ss);
        recyclerSales.setAdapter(adapter);
    }

    //===============================insertSubmitSales========================================
    private SubmitSales_Model insertSubmitSales()
    {

        OfflineSales_Model offlineModel=new OfflineSales_Model();
        SubmitSales_Model saveSales= new SubmitSales_Model();
        saveSales.setCustmrId(custmer.getId());
        saveSales.setUsrId(loginModel.getUserId());


        String[] arr=String.valueOf(sessionId).split("\\.");
        int[] intArr=new int[2];
        intArr[0]=Integer.parseInt(arr[0]); // 1
        intArr[1]=Integer.parseInt(arr[1]);
        Log.e("fraction",intArr[0]+"");
        Log.e("integer", intArr[1]+"");

        double value= Double.parseDouble(sessionId);

        int paisa=intArr[0]*100+intArr[1];
        Log.e("valueFraction",paisa+"");

        int roundingAmount;
        if(paisa>50)
        {
            roundingAmount=1;
        }
        else
            {
            roundingAmount=0;
        }
        saveSales.setTotSaleAmt(value+roundingAmount);
        saveSales.setGrandTotAmt(sessionId);
        saveSales.setOtherCharge(0);
        saveSales.setRoundingAmt(roundingAmount);
        saveSales.setTranOverheads(0);

        for(int i=0;i<staticItemList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            Brand_SubBrand_Model.Item model=staticItemList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list2.add(cartList);
        }

        for(int i=0;i<staticFreqList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            FrequentlyModel model=staticFreqList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list2.add(cartList);
        }
        saveSales.setSalesDet(list2);
        return saveSales;
    }

    //===============================insertSubmitOrder========================================
    private SubmitOrder_Model insertSubmitOrder()
    {
        Log.e("userId",loginModel.getUsrName());
        Log.e("userId",loginModel.getUserId()+"");
        SubmitOrder_Model saveSales= new SubmitOrder_Model();
        saveSales.setCustmrId(custmer.getId());
        saveSales.setUsrId(loginModel.getUserId());
        saveSales.setTotSaleAmt(Float.parseFloat(sessionId));

        for(int i=0;i<staticItemList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            Brand_SubBrand_Model.Item model=staticItemList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list2.add(cartList);

        }

        for(int i=0;i<staticFreqList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            FrequentlyModel model=staticFreqList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list2.add(cartList);
        }
        saveSales.setSalesDet(list);
        return saveSales;
    }




    private void finishActivity()
    {
        staticItemList.clear();
        saveToSpBrandItem(staticItemList);
        staticFreqList.clear();
        saveToSpFreqItem(staticFreqList);
        disableProgress(progressBar);
        Intent intent=new Intent(CheckOut_Activity.this,Manufacture_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private OfflineSales_Model saveOffline()
    {


        OfflineSales_Model offmodel=new OfflineSales_Model();
        offmodel.setUserName(custmer.getCustmrName());
        offmodel.setUserAddress(custmer.getMobl());
        offmodel.setUserMobile(custmer.getAddress());
        offmodel.setTotalAmount(sessionId);

        for(int i=0;i<staticItemList.size();i++)
        {
            OfflineSales_Model.BrandOffline_Model cartList = new OfflineSales_Model.BrandOffline_Model();
            Brand_SubBrand_Model.Item model=staticItemList.get(i);
            cartList.setQuantity(model.getQuantity());
            cartList.setItmName(model.getItmName());
            cartList.setItmCd(model.getItmCd());
            cartList.setItmBarCd(model.getItmBarCd());
            cartList.setCatgry(model.getCatgry());
            cartList.setUnit(model.getUnit());
            cartList.setBrand(model.getBrand());
            cartList.setRTSWT(model.getRTSWT());
            cartList.setEXSWT(model.getEXSWT());
            cartList.setWHSWT(model.getWHSWT());
            cartList.setDTSWT(model.getDTSWT());
            cartList.setAvilStk(model.getAvilStk());
            cartList.setTaxId(model.getTaxId());
            cartList.setTaxPerc(model.getTaxPerc());
            cartList.setMrp(model.getMrp());
            offlineSaveList.add(cartList);
        }

        for(int i=0;i<staticFreqList.size();i++)
        {
            OfflineSales_Model.BrandOffline_Model cartList = new OfflineSales_Model.BrandOffline_Model();
            FrequentlyModel model=staticFreqList.get(i);
            cartList.setQuantity(model.getQuantity());
            cartList.setItmName(model.getItmName());
            cartList.setItmCd(model.getItmCd());
            cartList.setItmBarCd(model.getItmBarCd());
            cartList.setCatgry(model.getCatgry());
            cartList.setUnit(model.getUnit());
            cartList.setBrand(model.getBrand());
            cartList.setRTSWT(model.getRTSWT());
            cartList.setEXSWT(model.getEXSWT());
            cartList.setWHSWT(model.getWHSWT());
            cartList.setDTSWT(model.getDTSWT());
            cartList.setAvilStk(model.getAvilStk());
            cartList.setTaxId(model.getTaxId());
            cartList.setTaxPerc(model.getTaxPerc());
            cartList.setMrp(model.getMrp());
            offlineSaveList.add(cartList);
        }
        offmodel.setSalesDet(offlineSaveList);
        return offmodel;
    }





}
