package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.activity.Main_Menu_Activity;
import com.andoidproject.culluze_app.Activity.activity.Map_Activity;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.LoginModel;
import com.andoidproject.culluze_app.Activity.model.OfflineSales_Model;
import com.andoidproject.culluze_app.Activity.model.Orderlist_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.andoidproject.culluze_app.Activity.utils.DataBase;
import com.andoidproject.culluze_app.Activity.utils.GPSTracker;
import com.andoidproject.culluze_app.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;

public class Detail_CustomSub_act extends Base_Activity implements View.OnClickListener {

    ImageView back_img,call_img,location_img;
    LinearLayout sales_ll,checkIn_ll,collection_ll,return_ll,order_ll,offline_ll,orderOffline_ll;
    LinearLayout offline_mainll;
    public static int order;
    TextView name_tv,mobNo_tv,address_tv,gstn_number,outstn_amount;
    public static String userName;
    public static String userBalance;
    public static int userId;
    ProgressBar progress_circular;
    public static Get_CustomerModel custmer;
    boolean callFirst=true,cleckLocationClick=false;
    private Dialog mDialog;
    Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__custom_sub);
        init();
    }
    private void init()
    {
        back_img=findViewById(R.id.back_img);
        collection_ll=findViewById(R.id.collection_ll);
        sales_ll=findViewById(R.id.sales_ll);
        order_ll=findViewById(R.id.order_ll);
        return_ll=findViewById(R.id.return_ll);
        checkIn_ll=findViewById(R.id.checkIn_ll);
        call_img=findViewById(R.id.call_img);
        location_img=findViewById(R.id.location_img);
        progress_circular=findViewById(R.id.progress_circular);
        offline_mainll=findViewById(R.id.offline_mainll);

        name_tv=findViewById(R.id.name_tv);
        mobNo_tv=findViewById(R.id.mobNo_tv);
        address_tv=findViewById(R.id.address_tv);
        gstn_number=findViewById(R.id.gstn_number);
        outstn_amount=findViewById(R.id.outstn_amount);
        locationButton=findViewById(R.id.locationButton);
        offline_ll=findViewById(R.id.offline_ll);
        orderOffline_ll=findViewById(R.id.orderOffline_ll);

        Intent i = getIntent();
        custmer = (Get_CustomerModel)i.getSerializableExtra("customerDetail");

        userName=custmer.getCustmrName();
        userBalance=custmer.getAvilBal()+"";
        userId=custmer.getId();
        name_tv.setText(custmer.getCustmrName());
        mobNo_tv.setText(custmer.getMobl());
        address_tv.setText(custmer.getAddress());
        gstn_number.setText(custmer.getGstin());
        outstn_amount.setText(custmer.getAvilBal());

//=========================ClickEvent==========================
        back_img.setOnClickListener(this);
        collection_ll.setOnClickListener(this);
        order_ll.setOnClickListener(this);
        return_ll.setOnClickListener(this);
        sales_ll.setOnClickListener(this);
        checkIn_ll.setOnClickListener(this);
        call_img.setOnClickListener(this);
        location_img.setOnClickListener(this);
        locationButton.setOnClickListener(this);
        offline_ll.setOnClickListener(this);
        orderOffline_ll.setOnClickListener(this);
//=========================ClickEvent==========================


        if(!TextUtils.isEmpty(custmer.getLatitude()) && !TextUtils.isEmpty(custmer.getLongitude()))
        { float lat= Float.parseFloat(custmer.getLatitude());
          float longt= Float.parseFloat(custmer.getLongitude());

        if(lat!=0 && longt!=0)
        {
            location_img.setVisibility(View.VISIBLE);
            locationButton.setText("Update Location");
        }}
        else {
            location_img.setVisibility(View.GONE);
            locationButton.setText("Fetch Location");
        }

      /*  DataBase.Pair pair=db.saveAllList();

        listSubmitSales=pair.Saveorder();
        offlineSales=pair.SaveItem();

        if(listSubmitSales.size()!=0)
        {
            offline_ll.setVisibility(View.VISIBLE);
            offline_mainll.setVisibility(View.VISIBLE);
        }
        else {
            offline_ll.setVisibility(View.GONE);
            offline_mainll.setVisibility(View.GONE);
        }*/


    }

    //=====================================clickEvent======================================
    @Override
    public void onClick(View v) {

        if(back_img==v)
        {
            onBackPressed();
        }

        if(collection_ll==v)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, Collection_activity.class);
            startActivity(intent);
        }

        if(order_ll==v)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, Manufacture_Activity.class);
            startActivity(intent);
            order=2;
        }

        if(sales_ll==v)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, Manufacture_Activity.class);
            startActivity(intent);
            order=1;
        }
        if(checkIn_ll==v)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, Check_in_Activity.class);
            startActivity(intent);
        }
        if(return_ll==v)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, ReturnActivity.class);
            intent.putExtra("source",1);
            startActivity(intent);
        }

        if(offline_ll==v)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, ReturnActivity.class);
            intent.putExtra("source",2);
            startActivity(intent);
        }


        if(v==call_img)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+custmer.getMobl()));
            startActivity(intent);
        }

        if(v==location_img)
        {
            Intent intent= new Intent(this, Map_Activity.class);
            intent.putExtra("call",1);
            startActivity(intent);
        }

        if(v==locationButton)
        { cleckLocationClick=true;
            checkGps();
        }

        if(v==orderOffline_ll)
        {
            Intent intent=new Intent(Detail_CustomSub_act.this, ReturnActivity.class);
            intent.putExtra("source",3);
            startActivity(intent);
        }


    }
    //=====================================clickEvent======================================



    //=======================================latLongApi====================================
    private void callLatLong_Api()
    {
        GPSTracker gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        JsonObject postParam = new JsonObject();
        postParam.addProperty("custId",custmer.getId()) ;
        postParam.addProperty("latitude",latitude) ;
        postParam.addProperty("longitude",longitude) ;

        Call<String> call = apiInterface.latlong(postParam);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String>callback, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    String results = jsonObject.getString("results");
                    if(results.equals("N"))
                    {
                        disableProgress(progress_circular);
                        Toast.makeText(Detail_CustomSub_act.this, "Unable to update Location", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        disableProgress(progress_circular);
                        //  Toast.makeText(Detail_CustomSub_act.this, "Location update Successfully", Toast.LENGTH_SHORT).show();
                        alertMessage("Location update Successfully. Waiting for admin clearance ");
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
                    Toast.makeText(Detail_CustomSub_act.this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
        callFirst=false;
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
            { enableProgress(progress_circular);
               callLatLong_Api();
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

        ArrayList<SubmitSales_Model> listSubmitSales=new ArrayList<>();
        ArrayList<OfflineSales_Model> offlineSales=new ArrayList<>();
        ArrayList<SubmitOrder_Model> listorderList=new ArrayList<>();

        DataBase.Pair pair=db.getSaveSpecific(loginModel.getUserId().toString()) ;
        DataBase.Order order=db.getOrderSpecific(loginModel.getUserId().toString());
        listSubmitSales=pair.Saveorder();
        offlineSales=pair.SaveItem();
        listorderList=order.submitOrders();

            if (listSubmitSales.size() != 0 && listorderList.size() != 0) {
                offline_ll.setVisibility(View.VISIBLE);
                offline_mainll.setVisibility(View.VISIBLE);
                orderOffline_ll.setVisibility(View.VISIBLE);
            } else if (listSubmitSales.size() != 0) {
                offline_ll.setVisibility(View.VISIBLE);
                offline_mainll.setVisibility(View.VISIBLE);
            } else if (listorderList.size() != 0) {
                orderOffline_ll.setVisibility(View.VISIBLE);
                offline_ll.setVisibility(View.GONE);
                offline_mainll.setVisibility(View.VISIBLE);
            } else  {
                offline_ll.setVisibility(View.GONE);
                orderOffline_ll.setVisibility(View.GONE);
                offline_mainll.setVisibility(View.GONE);
            }

    }
}
