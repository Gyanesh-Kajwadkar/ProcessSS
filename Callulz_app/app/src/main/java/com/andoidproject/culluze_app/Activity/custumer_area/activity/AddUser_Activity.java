package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.activity.Map_Activity;
import com.andoidproject.culluze_app.Activity.model.AddCustomer_Model;
import com.andoidproject.culluze_app.Activity.utils.GPSTracker;
import com.andoidproject.culluze_app.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;

public class AddUser_Activity extends Base_Activity implements View.OnClickListener {


    ImageView back_img,call_img,location_img;
    LinearLayout detail_ll,enterData_ll;
    TextView name_tv,mobile_tv,address_tv,gstn_tv,save_tv;
    EditText name_et,mobile_et,address_et,gstn_et;
    ProgressBar progress_circular;
    private Dialog mDialog;
    boolean callFirst=true,cleckLocationClick=false;
    Button locationButton;
    View name_view,mobile_view,address_view, gstn_view;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_);

        init();
    }


    private void init(){
        back_img=findViewById(R.id.back_img);
        detail_ll=findViewById(R.id.detail_ll);
        enterData_ll=findViewById(R.id.enterData_ll);
        name_tv=findViewById(R.id.name_tv);
        mobile_tv=findViewById(R.id.mobile_tv);
        address_tv=findViewById(R.id.address_tv);
        gstn_tv=findViewById(R.id.gstn_tv);
        name_et=findViewById(R.id.name_et);
        mobile_et=findViewById(R.id.mobile_et);
        address_et=findViewById(R.id.address_et);
        gstn_et=findViewById(R.id.gstn_et);
        save_tv=findViewById(R.id.save_tv);
        call_img=findViewById(R.id.call_img);
        location_img=findViewById(R.id.location_img);
        locationButton=findViewById(R.id.locationButton);
        progress_circular=findViewById(R.id.progress_circular);


        name_view=findViewById(R.id.name_view);
        mobile_view=findViewById(R.id.mob_view);
        address_view=findViewById(R.id.address_view);
        gstn_view=findViewById(R.id.gstn_view);

        back_img.setOnClickListener(this);
        save_tv.setOnClickListener(this);
        call_img.setOnClickListener(this);
        location_img.setOnClickListener(this);
        locationButton.setOnClickListener(this);

        focusChanged();
    }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }

        if(v==locationButton)
        {
            cleckLocationClick=true;
            checkGps();
        }


        if(v==save_tv)
        {
            String name=name_et.getText().toString().trim();
            String mobile=mobile_et.getText().toString().trim();
            String address=address_et.getText().toString().trim();
            String gstn=gstn_et.getText().toString().trim();

            if(TextUtils.isEmpty(name))
            {
                name_et.requestFocus();
                name_et.setError("Required");
                return;
            }

            if(TextUtils.isEmpty(mobile))
            {
                mobile_et.requestFocus();
                mobile_et.setError("Required");
                return;
            }

            if(TextUtils.isEmpty(address))
            {
               address_et.requestFocus();
               address_et.setError("Required");
                return;
            }

            if(TextUtils.isEmpty(gstn))
            {
                gstn_et.requestFocus();
                gstn_et.setError("Required");
                return;
            }
            enableProgress(progress_circular);
            addCustomer_Api();

        }


        if(v==call_img)
        {
          /*  Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0123456789"));
            startActivity(intent);*/
        }

        if(v==location_img)
        {
            /*Intent intent= new Intent(AddUser_Activity.this, Map_Activity.class);
            startActivity(intent);*/
        }


    }


    private void focusChanged()
    {
        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        mobile_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    mobile_view.setElevation(2f);
                    lineAnimation(mobile_view);
                }
                else {

                    Reverse_lineAnimation(mobile_view);
                } }});

        address_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    address_view.setElevation(2f);
                    lineAnimation(address_view);
                }
                else {

                    Reverse_lineAnimation(address_view);
                } }});

        gstn_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    gstn_view.setElevation(2f);
                    lineAnimation(gstn_view);
                }
                else {

                    Reverse_lineAnimation(gstn_view);
                } }});
    }


    //=======================================latLongApi====================================
    private void addCustomer_Api()
    {

        AddCustomer_Model customer= new AddCustomer_Model();

        Log.e("lat",latitude+"");
        Log.e("longitude",longitude+"");

        name_tv.setText(name_et.getText().toString().trim());
        mobile_tv.setText(mobile_et.getText().toString().trim());
        address_tv.setText(address_et.getText().toString().trim());
        gstn_tv.setText(gstn_et.getText().toString().trim());

        customer.setUsrId(loginModel.getUserId());
        customer.setAddress(address_et.getText().toString().trim());
        customer.setCustName(name_et.getText().toString().trim());
        customer.setGst(gstn_et.getText().toString().trim());
        customer.setLocLatt(String.valueOf(latitude));
        customer.setLocLongt(String.valueOf(longitude));
        customer.setMobNo(mobile_et.getText().toString());


        Call<String> call = apiInterface.addCustomer(customer);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String>callback, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    String results = jsonObject.getString("results");
                    Log.e("results",results+"");
                    if(results.equals("N"))
                    {
                        disableProgress(progress_circular);
                        Toast.makeText(AddUser_Activity.this, "Unable to add user", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        disableProgress(progress_circular);
                        //  Toast.makeText(Detail_CustomSub_act.this, "Location update Successfully", Toast.LENGTH_SHORT).show();
                        alertMessage("Customer will be add after admin clearance");
                        detail_ll.setVisibility(View.VISIBLE);
                        enterData_ll.setVisibility(View.GONE);

                        name_tv.setText(name_et.getText().toString().trim());
                        mobile_tv.setText(mobile_et.getText().toString().trim());
                        address_tv.setText(address_et.getText().toString().trim());
                        gstn_tv.setText(gstn_et.getText().toString().trim());

                        save_tv.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    disableProgress(progress_circular);
                }

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("fail",t.getMessage());
                disableProgress(progress_circular);
                checkConnection();
                if(!internet)
                {
                    Toast.makeText(AddUser_Activity.this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //=======================================latLongApi====================================


    //=======================================CheckGps======================================
    private void checkGps()
    {

        if (mDialog != null) {
            mDialog.dismiss();
        }

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (statusOfGPS){
            checkConnection();
            if(callFirst && internet)
            {
                GPSTracker gps = new GPSTracker(this);
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                Toast.makeText(this, "Location update successfully", Toast.LENGTH_SHORT).show();
            }}
        else{
            gpsEnabling(this);
        }


        Log.e("gpsgps",statusOfGPS+"");

    }

    public void gpsEnabling(final Activity activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Do you want open GPS setting?";
        builder.setCancelable(false);
        builder.setTitle("Location access Required");
        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                                d.cancel();
                            }
                        });
              /*  .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });*/
        mDialog= builder.show();
    }
    //=======================================CheckGps======================================


    @Override
    protected void onResume()
    {
        super.onResume();
        if(cleckLocationClick)
        { checkGps();
            cleckLocationClick=false;
        }
    }


}
