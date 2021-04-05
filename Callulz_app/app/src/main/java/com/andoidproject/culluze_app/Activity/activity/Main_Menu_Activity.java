package com.andoidproject.culluze_app.Activity.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.analytic_area.Analytics_activity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.CustomerActivity;
import com.andoidproject.culluze_app.Activity.fragment.Qr_Fragment;
import com.andoidproject.culluze_app.Activity.help_area.HelpActivity;
import com.andoidproject.culluze_app.Activity.model.BranchUser_Model;
import com.andoidproject.culluze_app.Activity.model.BrandList_Model;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.CompanyName_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.Activity.model.LoginModel;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.Activity.notificaion_area.NotificationActivity;
import com.andoidproject.culluze_app.Activity.report_area.Reports_Activity;
import com.andoidproject.culluze_app.Activity.route_area.RoutePlanner_Main;
import com.andoidproject.culluze_app.Activity.setting_area.SettingsActivity;
import com.andoidproject.culluze_app.Activity.utils.SharedPreferanceUtils;
import com.andoidproject.culluze_app.R;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;

public class Main_Menu_Activity extends Base_Activity  implements View.OnClickListener {

    ApiInterface apiInterface;
    Button       home_btn,route_btn,customs_btn,report_btn,notification_btn,analytics_btn,setting_btn,sync_btn,
            help_btn,bug_btn;
    boolean      callingSync = false, callingCustomer=false;
    FrameLayout  frameMain;
    FrameLayout  qrFrame;
    Gson         gson;
    ProgressBar  progress_bar;
    TextView     reset_tv,companyName_tv;
    SharedPreferences preferences;
    SharedPreferences.Editor prefsEditor;
    RelativeLayout logout_rl,qrScan_rl;
    static TextView toolbarText;
    SharedPreferanceUtils sp;
    public static boolean showDialogbol=false;
    private boolean callApi=false;
    ArrayList<Get_CustomerModel> modellist;
    ArrayList<Brand_SubBrand_Model> modellist2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    Log.e("userId",loginModel.getUsrName());
    Log.e("userId",loginModel.getUserId()+"");
    }

    //=============================================Init=============================================
    private void init() {
      /*  Fabric.with(this, new Crashlytics());
        logUser();*/
        sp = new SharedPreferanceUtils(this);
        frameMain=findViewById(R.id.frameMain);
        toolbarText = findViewById(R.id.toolbarText);
        reset_tv = findViewById(R.id.reset_tv);
        home_btn=findViewById(R.id.home_btn);
        route_btn = findViewById(R.id.route_btn);
        customs_btn = findViewById(R.id.customs_btn);
        report_btn =findViewById(R.id.report_btn);
        notification_btn = findViewById(R.id.notification_btn);
        analytics_btn = findViewById(R.id.analytics_btn);
        setting_btn = findViewById(R.id.setting_btn);
        sync_btn = findViewById(R.id.sync_btn);
        help_btn =findViewById(R.id.help_btn);
        bug_btn = findViewById(R.id.bug_btn);
        progress_bar = findViewById(R.id.progress_bar);
        logout_rl = findViewById(R.id.logout_rl);
        qrScan_rl = findViewById(R.id.qrScan_rl);
        qrFrame = findViewById(R.id.qrFrame);
        companyName_tv = findViewById(R.id.companyName_tv);

        preferences = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = preferences.edit();
        apiInterface=APIClient.getClient().create(ApiInterface.class);
        loginModel=getHistoryUser("userDetail");
        requestPermission();


       /* Date date1 = new Date();
        date1.setDate(5);
        Date date2 = new Date();
        date2.setDate(10);


        long diff = date2.getTime() - date1.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        Log.e("==========",days+"");

        //int k=1;
        for(int j=1;j<=days;j++)
        {

            callDate(j,date2);
           // k=k++;
        }
*/

    }

   /* private void callDate(int k,Date date2)
    {
        DateFormat df = new SimpleDateFormat( "dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date2);
        cal.add(Calendar.DAY_OF_YEAR, -k);
        String date = df.format(  cal.getTime());
        Log.e("date",date);
    }*/



    //================================InitilizeClick================================================
    private void chickEvent() {
        home_btn.setOnClickListener(this);
        route_btn.setOnClickListener(this);
        customs_btn.setOnClickListener(this);
        analytics_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        report_btn.setOnClickListener(this);
        sync_btn.setOnClickListener(this);
        bug_btn.setOnClickListener(this);
        help_btn.setOnClickListener(this);
        notification_btn.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        logout_rl.setOnClickListener(this);
        qrScan_rl.setOnClickListener(this);
    }


    //==================================ClickEvent=================================================
    @Override
    public void onClick(View v) {

        if(v==home_btn)
        {
            /*Intent intent = new Intent(this, PrintActivity.class);
            startActivity(intent);*/
            customerCallingMethod();
        }

        if(v==route_btn)
        {
            Intent intent= new Intent(Main_Menu_Activity.this, RoutePlanner_Main.class);
            startActivity(intent);
        }

        if(v==customs_btn)
        {
            customerCallingMethod();
        }

        if(v==analytics_btn)
        {
            Intent intent= new Intent(Main_Menu_Activity.this, Analytics_activity.class);
            startActivity(intent);
        }

        if(v==setting_btn)
        {
            startActivity(new Intent(Main_Menu_Activity.this, SettingsActivity.class));
        }

        if(v==report_btn)
        {
            Intent intent= new Intent(Main_Menu_Activity.this, Reports_Activity.class);
            startActivity(intent);
        }

        if(v==sync_btn)
        {
            checkConnection();
            if(internet)
            {
                callingSync=true;
                enableProgress(progress_bar);
                callCustomerList_Api();
                branchUserApi();

            }
            else {
                internetDialog();
            }
            // callBrandList_Api();

        }

        if(v==notification_btn)
        {
            Intent intent= new Intent(Main_Menu_Activity.this, NotificationActivity.class);
            startActivity(intent);
        }

        if(v==bug_btn)
        {
            Intent intent= new Intent(Main_Menu_Activity.this, ReportBugActivity.class);
            startActivity(intent);
        }
        if(v==help_btn)
        {
            Intent intent= new Intent(Main_Menu_Activity.this, HelpActivity.class);
            startActivity(intent);
        }

        if(v==logout_rl)
        {
            logoutAlert();
        }

        if(v==qrScan_rl)
        {
            int greenColorValue = Color.parseColor("#D9ffffff");

            Fragment fragment = new Qr_Fragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.qrFrame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        }


    }

    //====================================ApiCalling================================================
    private void callCustomerList_Api() {

        String name= (String) sp.getParam("userName","");
        Call<ArrayList<Get_CustomerModel>> call=apiInterface.getCustomers(name);
        call.enqueue(new Callback<ArrayList<Get_CustomerModel>>() {
            @Override
            public void onResponse(Call<ArrayList<Get_CustomerModel>> call, Response<ArrayList<Get_CustomerModel>> response) {
                Log.e("CustomerListOnResponse",response.message());
                ArrayList<Get_CustomerModel>model=response.body();
                gson = new Gson();
                Get_CustomerModel add = null;
                String json = gson.toJson(model);
                prefsEditor.putString(loginModel.getUserId()+"allCustomer", json);
                prefsEditor.apply();
                callBrandApi();
            }

            @Override
            public void onFailure(Call<ArrayList<Get_CustomerModel>> call, Throwable t) {
                Log.e("CustomerListfail",t.getMessage());
                disableProgress(progress_bar);
                checkConnection();

                if(!internet)
                {
                    callToast(Main_Menu_Activity.this,"Internet Connection problem" );
                }
                else {
                    callToast(Main_Menu_Activity.this,"Server Error" );
                }
            }});}

    private void callFrequentlyBought_Api() {
        String name= (String) sp.getParam("userName","");
        Call<String> call=apiInterface.frequentlyBought("FRE",loginModel.getUserId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    String data = response.body();
                    Object json1 = new JSONTokener(data).nextValue();
                    if (json1 instanceof JSONObject)
                    {
                        Log.e("object","object");
                        ArrayList<FrequentlyModel>model=new ArrayList<>();
                        gson = new Gson();
                        String json = gson.toJson(model);
                        prefsEditor.putString(loginModel.getUserId()+"freqBought", json);
                        prefsEditor.apply();

                    }

                    else if (json1 instanceof JSONArray)
                    {
                        Log.e("Array","Array");
                        TypeToken<ArrayList<FrequentlyModel>> token = new TypeToken<ArrayList<FrequentlyModel>>() {
                        };
                        ArrayList<FrequentlyModel>model= gson.fromJson(data,token.getType());
                        gson = new Gson();
                        Get_CustomerModel add = null;
                        String json = gson.toJson(model);
                        prefsEditor.putString(loginModel.getUserId()+"freqBought", json);
                        prefsEditor.apply();

                    }
                    if(callingSync ) {
                        callToast(Main_Menu_Activity.this,"Sync Successfully" );
                    }

                    try {
                        modellist = getHistoryCustomer(loginModel.getUserId()+"allCustomer",Main_Menu_Activity.this);
                        modellist2 = getHistoryBrandItem(loginModel.getUserId()+"brandItem",Main_Menu_Activity.this);
                        if(modellist.size()!=0 && modellist2.size()!=0)
                        {
                            if(callingCustomer){
                                Intent intent= new Intent(Main_Menu_Activity.this, CustomerActivity.class);
                                startActivity(intent);
                                callingCustomer=false;} }

                        else if(callingCustomer)
                        {
                            callToast(Main_Menu_Activity.this,"Server Error" );
                        }
                    }
                    catch (NullPointerException e)
                    {
                        Log.e("fromFreqApi",e.getMessage());
                        callToast(Main_Menu_Activity.this,"Server Error" );
                    }
                    disableProgress(progress_bar);

                } catch (JSONException e) {
                    e.printStackTrace();
                    disableProgress(progress_bar);
                }}

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("freqBoughtfail",t.getMessage());
                disableProgress(progress_bar);
                checkConnection();
                Log.e("checkConnection",internet+"");
                if(!internet)
                {
                    callToast(Main_Menu_Activity.this,"Internet Connection problem" );
                }
                else {
                    callToast(Main_Menu_Activity.this,"Server Error" );
                }
            }});}

    private void callBrandApi() {

        Call<ArrayList<Brand_SubBrand_Model>> call=apiInterface.brandItem(loginModel.getUserId().toString());
        call.enqueue(new Callback<ArrayList<Brand_SubBrand_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Brand_SubBrand_Model>> call, Response<ArrayList<Brand_SubBrand_Model>> response) {

                Log.e("brandItem","brandItem");
                ArrayList<Brand_SubBrand_Model>brand=response.body();
                gson = new Gson();
                String json = gson.toJson(brand);
                prefsEditor.putString(loginModel.getUserId()+"brandItem", json);
                prefsEditor.apply();
                //disableProgress(progress_bar);
                callFrequentlyBought_Api();
            }

            @Override
            public void onFailure(Call<ArrayList<Brand_SubBrand_Model>> call, Throwable t)
            {

                Log.e("hello",t.getMessage());
                disableProgress(progress_bar);
                checkConnection();
                if(!internet)
                {
                    callToast(Main_Menu_Activity.this,"Internet Connection problem" );
                }
                else {
                    callToast(Main_Menu_Activity.this,"Server Error" );

                }

            }});
    }


    private void branchUserApi()
    {
            Call<ArrayList<BranchUser_Model>>call=apiInterface.branchUser("1");
            call.enqueue(new Callback<ArrayList<BranchUser_Model>>() {
                @Override
                public void onResponse(Call<ArrayList<BranchUser_Model>> call, Response<ArrayList<BranchUser_Model>> response) {
                    try {   Log.e("branchApi","ok");
                        ArrayList<BranchUser_Model> model=response.body();
                        gson = new Gson();
                        String json = gson.toJson(model);
                        prefsEditor.putString("branchUser", json);
                        prefsEditor.apply();
                        for(int i=0;i<model.size();i++)
                        {
                            BranchUser_Model mod=model.get(i);
                            companyName_tv.setText(mod.getBrnchName());
                            companyName_tv.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (Exception e)
                    {
                        companyName_tv.setVisibility(View.GONE);
                    }

                }
                @Override
                public void onFailure(Call<ArrayList<BranchUser_Model>> call, Throwable t) {
                    companyName_tv.setVisibility(View.GONE);
                    Log.e("companyNameApi","fail");
                }
            });


    }



    //=========================Onresume=============================================================
    @Override
    protected void onResume() {
        super.onResume();
        if(callApi)
        {
            requestPermission();
            callApi=false;
        }
    }

    //============================getSharepreferanceData============================================


    public LoginModel getHistoryUser(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<LoginModel>() {}.getType();
        return gson.fromJson(json, type);
    }

    //=============================================PermissionsMethod================================
    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                if (report.areAllPermissionsGranted()) {
                    chickEvent();
                    if(internet)
                    {
                        enableProgress(progress_bar);
                        callCustomerList_Api();
                        branchUserApi();
                    }

                    // callBrandList_Api();
                    }
                else {
                    showSettingsDialog();
                }}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
        callApi=true;
    }

    //=============================================ExtraMethod======================================

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("dhakaddocument@gmail.com");
        Crashlytics.setUserName("Dhakad Technosoft");
    }
    private void customerCallingMethod() {
        try{
            modellist = getHistoryCustomer(loginModel.getUserId()+"allCustomer",this);
            modellist2 = getHistoryBrandItem(loginModel.getUserId()+"brandItem",this);
            ArrayList<FrequentlyModel>modelList3=getHistoryFreqBought(loginModel.getUserId()+"freqBought",this);
            if(modellist.size()!=0 && modellist2.size()!=0 /*&& modelList3.size()!=0*/)
            {
                Intent intent= new Intent(Main_Menu_Activity.this, CustomerActivity.class);
                startActivity(intent);
            }
            else {
                checkConnection();
                if(internet)
                {
                    callingCustomer=true;
                    enableProgress(progress_bar);
                    callCustomerList_Api();
                    branchUserApi();
                }
                else {
                    internetDialog();
                }
            }
        }
        catch (Exception e)
        {
            checkConnection();
            if(internet)
            {
                callingCustomer=true;
                enableProgress(progress_bar);
                callCustomerList_Api();
                branchUserApi();
            }
            else {
                internetDialog();
            }
        }
    }
    private void logoutAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Logout");
        alertDialog
                .setMessage("Are you sure you want to Logout?");
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sp.setParam("login",false);
                        Intent intent= new Intent(Main_Menu_Activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();

    }
}
