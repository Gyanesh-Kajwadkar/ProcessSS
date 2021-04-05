package com.andoidproject.culluze_app.Activity.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.model.CompanyName_Model;
import com.andoidproject.culluze_app.Activity.model.LoginModel;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.Activity.utils.DataBase;
import com.andoidproject.culluze_app.Activity.utils.SharedPreferanceUtils;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.ID;

public class LoginActivity extends Base_Activity implements View.OnClickListener {

   public static String companyName,companyAddress,companyGstn;
    Button login_btn;
    TextView forgot_tv,companyName_tv;
    EditText name_et,password_et;
    View name_view,password_view;
    CardView card_view;
    ApiInterface apiInterface;
    ProgressBar progress_circular;
    public static LoginModel loginModel;
    Cursor cursor;
    DataBase db;
    SharedPreferanceUtils sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startAnimation();


        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    name_view.setElevation(2);
                    lineAnimation(name_view);
                }
                else {

                    Reverse_lineAnimation(name_view);
                } }});


        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    password_view.setElevation(2f);
                    lineAnimation(password_view);
                }
                else {

                    Reverse_lineAnimation(password_view);
                } }});



    }


    private void init()
    {
        sp = new SharedPreferanceUtils(this);
        db=new DataBase(this);

        login_btn=findViewById(R.id.login_btn);
        forgot_tv=findViewById(R.id.forgot_tv);
        name_et=findViewById(R.id.name_et);
        name_view=findViewById(R.id.name_view);
        password_et=findViewById(R.id.password_et);
        password_view=findViewById(R.id.password_view);
        card_view=findViewById(R.id.card_view);
        progress_circular=findViewById(R.id.progress_circular);
        companyName_tv = findViewById(R.id.companyName_tv);

        login_btn.setOnClickListener(this);
        forgot_tv.setOnClickListener(this);

        boolean restoredText = (boolean) sp.getParam("login",false);
        if (restoredText) {
            Intent intent= new Intent(LoginActivity.this, Main_Menu_Activity.class);
            startActivity(intent);
            finish();
        }
       companyNameApi();

     /*   if(!TextUtils.isEmpty(companyName))
        {
            companyName_tv.setText(companyName);
            companyName_tv.setVisibility(View.VISIBLE);
        }
        else {
            companyName_tv.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onClick(View v) {



        if(v==login_btn)
        {
            hideKeyBoard(v);

        /*    Intent intent= new Intent(LoginActivity.this, Main_Menu_Activity.class);
            startActivity(intent);*/

            String user=name_et.getText().toString().trim();
            String pass= password_et.getText().toString().trim();

            if(user.isEmpty())
            {
                name_et.requestFocus();
                name_et.setError("Required");
                return;
            }

            if(pass.isEmpty())
            {
               password_et.requestFocus();
               password_et.setError("Required");
                return;
            }

            boolean goToNext=db.getValue();
            if(goToNext)
            {
                callCursor(user,pass);
            }
            else {
                checkConnection();
                if(internet)
                {
                    callLoginApi(user,pass);
                    // callApi(Integer.parseInt(id),name,fullname,followers);
                }
                else
                {
                    internetDialog();
                }
            }
            /*    checkConnection();

            if(internet)
            {

               // callLoginApi(user,pass);
            }
            else {

                internetDialog();
               }*/
        }

        if(v==forgot_tv)
        {
            Intent intent= new Intent(LoginActivity.this, Forgot_Activity.class);
            startActivity(intent);
        }
    }


    private void startAnimation()
    {
        ScaleAnimation animate = new ScaleAnimation(name_et.getWidth(), 1, name_et.getHeight(), 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        name_et.startAnimation(animate);
        name_view.startAnimation(animate);
        login_btn.startAnimation(animate);
        password_et.startAnimation(animate);
        forgot_tv.startAnimation(animate);
        password_view.startAnimation(animate);
        card_view.startAnimation(animate);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {

        super.onResume();
    }

    private void callLoginApi(String user, final String pass)
    {
        enableProgress(progress_circular);
        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<String>call=apiInterface.loginData(user,pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    String results = jsonObject.getString("results");
                    Gson gson = new Gson();
                   loginModel =  gson.fromJson(String.valueOf(jsonObject),LoginModel.class);
                   Log.e("loginModel",loginModel.getUserId()+"");

                    if(results.equals("N"))
                    {
                        disableProgress(progress_circular);
                        Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                   /* Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent= new Intent(LoginActivity.this, Main_Menu_Activity.class);
                            startActivity(intent);
                            disableProgress(progress_circular);
                        }
                    },1500);*/
                    }

                    else {

                         loginModel.setPassword(pass);
                         Gson gson1 = new Gson();
                         String toStoreObject = gson1.toJson(loginModel, LoginModel.class);
                         db.addUser(toStoreObject);
                         userDetail(loginModel);
                         sp.setParam("login",true);
                         sp.setParam("staticList",false);
                         sp.setParam("userId",loginModel.getUserId());
                         sp.setParam("userName",loginModel.getUsrName());

                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(LoginActivity.this, Main_Menu_Activity.class);
                        startActivity(intent);
                        finish();
                        disableProgress(progress_circular);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    disableProgress(progress_circular);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                disableProgress(progress_circular);

            }
        }); }

    private void callCursor(String user, String pass)
    {


         cursor=db.fetch();
        if (cursor.moveToFirst())
        {
            do
            {
                enableProgress(progress_circular);

                String name = cursor.getString(cursor.getColumnIndex("name"));
                Gson gson1 = new Gson();
                LoginModel modelObject = gson1.fromJson(name, LoginModel.class);

                String SaveUser=modelObject.getUsrName();
                String SavePassword=modelObject.getPassword();
                int compareValue1 = SaveUser.compareToIgnoreCase(user);
                int compareValue2 = SavePassword.compareToIgnoreCase(pass);

                if(compareValue1==0 && compareValue2==0)
                {

                    sp.setParam("login",true);
                    sp.setParam("staticList",false);
                    sp.setParam("userId",modelObject.getUserId());
                    sp.setParam("userName",modelObject.getUsrName());
                    loginModel=modelObject;
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(LoginActivity.this, Main_Menu_Activity.class);
                    startActivity(intent);
                    finish();
                    disableProgress(progress_circular);

                    userDetail(modelObject);

                    checkConnection();
                    if(internet)
                    {
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Offline Login Successfully", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                    {
                 checkConnection();
                 if(internet)
                 {
                    callLoginApi(user,pass);
                   // callApi(Integer.parseInt(id),name,fullname,followers);
                 }
                else
                {
                    internetDialog();
                }}

            }
            while(cursor.moveToNext());
            {
                disableProgress(progress_circular);
                Log.e("last","===========");

            }
        }
        cursor.close();
    }

    private void userDetail(LoginModel model)
    {
        SharedPreferences.Editor prefsEditor;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        prefsEditor = preferences.edit();
        Gson gson;
        gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString("userDetail", json);
        prefsEditor.apply();
    }

    private void companyNameApi()
    {
        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<CompanyName_Model>call=apiInterface.comanyName();
        call.enqueue(new Callback<CompanyName_Model>() {
            @Override
            public void onResponse(Call<CompanyName_Model> call, Response<CompanyName_Model> response) {
                try {   Log.e("companyNameApi","ok");
                    CompanyName_Model model=response.body();
                    companyName_tv.setText(model.getCompName());
                    companyName_tv.setVisibility(View.VISIBLE);
                    companyName=model.getCompName();
                    companyAddress=model.getAddress();
                    companyGstn=model.getGstNo().toString();
                }
                catch (Exception e)
                {
                }
            }
            @Override
            public void onFailure(Call<CompanyName_Model> call, Throwable t) {
                companyName_tv.setVisibility(View.GONE);
                Log.e("companyNameApi","fail");
            }
        }); }
}
