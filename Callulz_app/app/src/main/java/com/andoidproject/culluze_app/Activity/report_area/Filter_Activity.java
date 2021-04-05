package com.andoidproject.culluze_app.Activity.report_area;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.activity.Main_Menu_Activity;
import com.andoidproject.culluze_app.Activity.activity.Map_Activity;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.Orderlist_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;
import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;

public class Filter_Activity extends Base_Activity implements View.OnClickListener {


    TextView toolbarText;
    ImageView back_img;
    Button filterGenerate_btn;
    LinearLayout status_ll,custome_date_ll;
    Spinner spinCompany,spinDate,statusSpin;
    EditText to_et,from_et;
    ProgressBar progressBar;
    private  String[] company = {"X", "Y", "Z"};
    private  String[] date = {"Today", "Yesterday", "This Week","This Month","Select Range"};
    private  String[] status = {"All", "Pending", "Partial Supply","Full Supply","Deleted/Expired"};
    private DatePickerDialog mDatePickerDialog;
    Calendar myCalendar;
    ArrayList<String> mylist = new ArrayList<String>();
    int message;
    private ArrayList<Get_CustomerModel> modellist = new ArrayList<>();

    Get_CustomerModel selectedCustomer;
    String statusFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_);


        init();
    }


    private void init()
    {
        toolbarText=findViewById(R.id.toolbarText);
        toolbarText.setText("Filter");

        back_img=findViewById(R.id.back_img);

        to_et=findViewById(R.id.to_et);
        from_et=findViewById(R.id.from_et);
        progressBar=findViewById(R.id.progressBar);

        spinCompany=findViewById(R.id.spinCompany);
        spinDate=findViewById(R.id.spinDate);
        statusSpin=findViewById(R.id.statusSpin);

        filterGenerate_btn=findViewById(R.id.filterGenerate_btn);
        myCalendar = Calendar.getInstance();

        modellist = getHistoryList(loginModel.getUserId()+"allCustomer");

        for(int i=0;i<modellist.size();i++)
        {
            Get_CustomerModel model=modellist.get(i);
            mylist.add(model.getCustmrName());
        }

        status_ll=findViewById(R.id.status_ll);
        custome_date_ll=findViewById(R.id.custome_date_ll);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Filter_Activity.this,
                android.R.layout.simple_spinner_item,mylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCompany.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Filter_Activity.this,
                android.R.layout.simple_spinner_item,date);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDate.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Filter_Activity.this,
                android.R.layout.simple_spinner_item,status);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpin.setAdapter(adapter3);


        spinDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==4)
                {
                    custome_date_ll.setVisibility(View.VISIBLE);
                }
                else {
                    custome_date_ll.setVisibility(View.GONE);
                } }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statusSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusFilter=status[position];
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back_img.setOnClickListener(this);
        to_et.setOnClickListener(this);
        from_et.setOnClickListener(this);
        filterGenerate_btn.setOnClickListener(this);
        setBundle();

        spinCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)parent.getChildAt(0);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                selectedCustomer=modellist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }); }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }
        if(v==to_et)
        {
            new DatePickerDialog(Filter_Activity.this, getDate(to_et), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
        if(v==from_et)
        {
            new DatePickerDialog(Filter_Activity.this, getDate(from_et), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(v==filterGenerate_btn)
        {
            checkConnection();

            if(message==1)
            {
                Intent intent=new Intent(Filter_Activity.this, CusProduct_AnaSub.class);
                intent.putExtra("send",message);
                startActivity(intent);
            }
            if(message==2)
            {
                enableProgress(progressBar);
                if(internet)
                {
                    callOrderApi();                }
                else
                {
                    disableProgress(progressBar);
                    internetDialog();
                }
            }
            if(message==3)
            {
                Intent intent=new Intent(Filter_Activity.this, CusProduct_AnaSub.class);
                intent.putExtra("send",message);
                startActivity(intent);
            }
            if(message==4)
            {
                Intent intent=new Intent(Filter_Activity.this, Map_Activity.class);
                intent.putExtra("send",message);
                startActivity(intent);

            }
            if(message==5)
            {
                enableProgress(progressBar);
                if(internet)
                {
                    callSalesOrderApi(); }
                else {
                    disableProgress(progressBar);
                    internetDialog();
                }
            }
        } }


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

    private void setBundle()
    {
        Bundle bundle = getIntent().getExtras();
        message = bundle.getInt("send");
        if(message==2)
        {
            status_ll.setVisibility(View.VISIBLE);
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


    //================================callOrderSalesApi====================================

    private void callSalesOrderApi() {

        Call<ArrayList<Salesorderlist_Model>>call=apiInterface.salesOrderList(selectedCustomer.getId(),loginModel.getUserId());
        call.enqueue(new Callback<ArrayList<Salesorderlist_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Salesorderlist_Model>> call, Response<ArrayList<Salesorderlist_Model>> response) {
                Log.e("callOrderSalesApi","ok");
                Log.e("message",message+"");
                disableProgress(progressBar);
                ArrayList<Salesorderlist_Model>model=response.body();
                Intent intent=new Intent(Filter_Activity.this, CusProduct_AnaSub.class);
                intent.putExtra("send",message);
                intent.putExtra("statusFilter",statusFilter);
                Bundle args = new Bundle();
                args.putSerializable("arreyList",(Serializable)model);
                intent.putExtra("bundle",args);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ArrayList<Salesorderlist_Model>> call, Throwable t) {
                Log.e("hello",t.getMessage());
                failMethod(); }});}

    //================================callOrderApi====================================
    private void callOrderApi() {

        Call<ArrayList<Orderlist_Model>>call=apiInterface.orderList(selectedCustomer.getId(),loginModel.getUserId());
        call.enqueue(new Callback<ArrayList<Orderlist_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Orderlist_Model>> call, Response<ArrayList<Orderlist_Model>> response) {
                Log.e("callOrderApi","ok");
                Log.e("message",message+"");


                disableProgress(progressBar);
                ArrayList<Orderlist_Model>model=response.body();
                Intent intent=new Intent(Filter_Activity.this, CusProduct_AnaSub.class);
                intent.putExtra("send",message);
                intent.putExtra("statusFilter",statusFilter);
                Bundle args = new Bundle();
                args.putSerializable("arreyList",(Serializable)model);
                intent.putExtra("bundle",args);
                startActivity(intent); }

            @Override
            public void onFailure(Call<ArrayList<Orderlist_Model>> call, Throwable t) {
                Log.e("hello",t.getMessage());
                failMethod();
            }}); }


    private void failMethod()
    {
        disableProgress(progressBar);
        checkConnection();
        if(!internet)
        {
            Toast.makeText(Filter_Activity.this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Filter_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();

        }}



}
