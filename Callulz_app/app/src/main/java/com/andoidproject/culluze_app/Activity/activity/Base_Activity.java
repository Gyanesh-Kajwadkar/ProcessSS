package com.andoidproject.culluze_app.Activity.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.CheckOut_Activity;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.CompanyName_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.Activity.utils.ConnectivityReceiver;
import com.andoidproject.culluze_app.Activity.utils.DataBase;
import com.andoidproject.culluze_app.Activity.utils.MyApplication;
import com.andoidproject.culluze_app.Activity.utils.SharedPreferanceUtils;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.activity.Main_Menu_Activity.showDialogbol;

public class Base_Activity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    public Calendar myCalendar;
    public  ApiInterface apiInterface;
    public boolean internet=false;
    SharedPreferanceUtils sp;
    Gson gson;
    SharedPreferences preferences;
    SharedPreferences.Editor prefsEditor;
    public  boolean controlBack=false;
    public DataBase db;
    ArrayList<SubmitSales_Model> submitSales=new ArrayList<>();
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<Integer> idOrder=new ArrayList<>();
    ArrayList<SubmitOrder_Model> listorderList=new ArrayList<>();
    int getId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_);
        myCalendar = Calendar.getInstance();
        apiInterface= APIClient.getClient().create(ApiInterface.class);
        sp = new SharedPreferanceUtils(this);
        db=new DataBase(this);
        checkConnection();
        if(internet)
        {
            DataBase.Pair pair=db.saveAllList();
            DataBase.Order order=db.saveOrderList();

            submitSales=pair.Saveorder();
            listorderList=order.submitOrders();

            id=pair.passId();
            idOrder=order.passId();
            if(submitSales.size()!=0)
            {
                for(int i=0;i<submitSales.size();i++) {
                    int inputId=id.get(i);
                    final SubmitSales_Model saveSales=  submitSales.get(i);
                    callSalesSaverApi(inputId,saveSales);
                }


            }

            if(listorderList.size()!=0)
            {
                for(int i=0;i<listorderList.size();i++) {
                    int inputId=idOrder.get(i);
                    final SubmitOrder_Model saveSales=  listorderList.get(i);
                    callSubmitOrderApi(inputId,saveSales);
                }
            }


        }
    }

    //==========================StartAnimation================================
    public void lineAnimation(View view){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        //  name_et.startAnimation(anim);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_two);
        //  name_et.startAnimation(anim1);

        AnimationSet s = new AnimationSet(false);//false means don't share interpolators
        s.addAnimation(anim);
        s.addAnimation(anim1);
        view.startAnimation(s);
    }

    //==========================ReverseAnimation================================
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void Reverse_lineAnimation(final View view){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_reverse);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_rever_two);

        AnimationSet s = new AnimationSet(false);//false means don't share interpolators
        s.addAnimation(anim);
        s.addAnimation(anim1);
        view.startAnimation(s);


        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setElevation(-2f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }});

        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setElevation(-2f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    //==================================setMenu===================================
    public void setMenu(ImageView filter_img) {
        PopupMenu popup = new PopupMenu(this, filter_img);
        popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popup.show();
    }


    //================================enableProgress===============================
    public void enableProgress(ProgressBar progress) {
        controlBack=true;
        progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //===============================disableProgress===============================
    public void disableProgress(ProgressBar progress) {
        controlBack=false;
        progress.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //===============================DatePicker=====================================
    public DatePickerDialog.OnDateSetListener getDate(final EditText editText){
        DatePickerDialog.OnDateSetListener  date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(sdf.format(myCalendar.getTime()));
            }
        };


        return date;
    }


    //===============================HideKeyboard===================================
    public void hideKeyBoard(View view) {
        try  {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception e) {

        }
    }


    //==============================MainDialogBox===================================
    public void alertMessage( String message) {
        final Dialog dialog = new Dialog(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        lp.gravity = Gravity.CENTER;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView message_txt = dialog.findViewById(R.id.message_txt);
        Button ok_btn_dialog = dialog.findViewById(R.id.ok_btn_dialog);
        message_txt.setText(message);
        ok_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    //==============================checkInternetMethod=============================

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            internet=true;
            // Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        } else {

            // Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
            if(!showDialogbol)
            { internetDialog();}
        }


    }
    public void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected(this);
        showSnack(isConnected);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        if(MyApplication.getInstance()!=null)
        {
            MyApplication.getInstance().setConnectivityListener(this);}
    }

    //==============================InternetDialog==================================
    public void internetDialog() {
        internet=false;

        final Dialog dialog = new Dialog(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        lp.gravity = Gravity.CENTER;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView message_txt = dialog.findViewById(R.id.message_txt);
        Button ok_btn_dialog = dialog.findViewById(R.id.ok_btn_dialog);
        ImageView dialogLogo=dialog.findViewById(R.id.dialogLogo);
        dialogLogo.setImageResource(R.drawable.no_wifi);
        message_txt.setTextSize(19);
        message_txt.setText("No Internet connection. Make sure that Wi-Fi or Mobile data is turned on.");
        ok_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogbol=true;
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //============================getSharepreferanceData============================================

    public ArrayList<Get_CustomerModel> getHistoryCustomer(String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Get_CustomerModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<Brand_SubBrand_Model> getHistoryBrandItem(String key,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Brand_SubBrand_Model>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<FrequentlyModel>getHistoryFreqBought(String key,Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<FrequentlyModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<Brand_SubBrand_Model.Item> getHistoryBrandInner(String key,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Brand_SubBrand_Model.Item>>() {}.getType();
        return gson.fromJson(json, type);
    }

    //=========================saveToSp===============================================


    public void saveToSpBrandItem(ArrayList<Brand_SubBrand_Model.Item> allList) {
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

    public void saveToSpFreqItem(ArrayList<FrequentlyModel> allList)
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

    //=========================CallToastMessage=====================================
    public void callToast(Activity context, String message)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_view,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = layout.findViewById(R.id.toastmessage);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    private void callSalesSaverApi(final int inputId, SubmitSales_Model saveSales)
    {
           apiInterface= APIClient.getClient().create(ApiInterface.class);
            Call<SubmitSales_Model> call=apiInterface.submitsales(saveSales);
            call.enqueue(new Callback<SubmitSales_Model>()
        {
            @Override
            public void onResponse(Call<SubmitSales_Model> call, Response<SubmitSales_Model> response) {
                Log.e("salesSaveFromBase","ok");
                Log.e("fhdskfh",db.deleteRow_SaveSales(inputId)+"");
            }

            @Override
            public void onFailure(Call<SubmitSales_Model> call, Throwable t) {
               Log.e("salesSaveFromBase_ERROR",t.getMessage());
            }
        });}

    private void callSubmitOrderApi(final int inputId, SubmitOrder_Model placeOrder)
    {

        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<SubmitOrder_Model> call=apiInterface.submitOrder(placeOrder);

        call.enqueue(new Callback<SubmitOrder_Model>()
        {
            @Override
            public void onResponse(Call<SubmitOrder_Model> call, Response<SubmitOrder_Model> response) {
                Log.e("orderFromBase","ok");
                Log.e("sdfgsbds",db.deleteRow_Order_Save(inputId)+"");
            }

            @Override
            public void onFailure(Call<SubmitOrder_Model> call, Throwable t) {
                checkConnection();
                Log.e("OrderFromBase_ERROR",t.getMessage());

            }
        });}





//==============================companyNameApi========================================
/*public void companyNameApiC()

{

    apiInterface= APIClient.getClient().create(ApiInterface.class);
    Call<CompanyName_Model> call=apiInterface.comanyName();
    call.enqueue(new Callback<CompanyName_Model>() {
        @Override
        public void onResponse(Call<CompanyName_Model> call, Response<CompanyName_Model> response) {
            try {   Log.e("companyNameApi","ok");
                CompanyName_Model  companymodel=response.body();
                companyName=companymodel.getCompName();
                companyAddress=companymodel.getAddress();
                companyGstn=companymodel.getGstNo().toString();
            }
            catch (Exception e)
            {
            }
        }
        @Override
        public void onFailure(Call<CompanyName_Model> call, Throwable t) {
            Log.e("companyNameApi","fail");
        }
    });

}*/




}
