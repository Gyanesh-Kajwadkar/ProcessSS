package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.model.OfflineSales_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.andoidproject.culluze_app.Activity.report_area.adapter.ReturnAdapter;
import com.andoidproject.culluze_app.Activity.utils.DataBase;
import com.andoidproject.culluze_app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;

public class ReturnActivity extends Base_Activity implements View.OnClickListener {


    ImageView  cornerImage,back_img;
    TextView toolbarText;
    LinearLayout custome_date_ll,parent_ll;
    Spinner spinDate;
    EditText to_et,from_et,inVoice_et;
    Calendar myCalendar;
    RecyclerView returnRecycler;
    Button clear_btn,filter_btn;
    View name_view;
    ProgressBar progress_bar;
    ReturnAdapter adapter;
    ArrayList<Salesorderlist_Model>passinList=new ArrayList<>();
    ArrayList<Salesorderlist_Model>listSet=new ArrayList<>();
    boolean pass=true,FromTo=false;
    Date fromDate,toDate;
    int source=0;
    ArrayList<SubmitSales_Model> listSubmitSales=new ArrayList<>();
    ArrayList<OfflineSales_Model> offlineSales=new ArrayList<>();
    ArrayList<SubmitOrder_Model> listorderList=new ArrayList<>();


    private  String[] date = {"Select","Today", "Yesterday", "This Week","This Month","Select Range"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        source=getIntent().getIntExtra("source",0);
        init();

    }
    private void init()
    {
        cornerImage=findViewById(R.id.cornerImage);
        back_img=findViewById(R.id.back_img);
        toolbarText=findViewById(R.id.toolbarText);
        custome_date_ll=findViewById(R.id.custome_date_ll);
        parent_ll=findViewById(R.id.parent_ll);
        spinDate=findViewById(R.id.spinDate);
        returnRecycler=findViewById(R.id.returnRecycler);
        clear_btn=findViewById(R.id.clear_btn);
        name_view=findViewById(R.id.name_view);
        inVoice_et=findViewById(R.id.inVoice_et);
        progress_bar=findViewById(R.id.progress_bar);
        filter_btn=findViewById(R.id.filter_btn);

        to_et=findViewById(R.id.to_et);
        from_et=findViewById(R.id.from_et);

        back_img.setOnClickListener(this);

        if(source==1)
        {
            cornerImage.setOnClickListener(this);
            clear_btn.setOnClickListener(this);
            filter_btn.setOnClickListener(this);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ReturnActivity.this,
                android.R.layout.simple_spinner_item,date);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDate.setAdapter(adapter2);

        to_et.setOnClickListener(this);
        from_et.setOnClickListener(this);

        myCalendar = Calendar.getInstance();
        spinDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inVoice_et.setText("");
                DateFormat df = new SimpleDateFormat( "dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();

                if(position==0)
                {
                    pass=false;
                }

                if(position==1)
                {
                    pass=true;
                    String date = df.format(cal.getTime());
                    Log.e("today",date);
                    for(int i=0;i<listSet.size();i++)
                    {
                        Salesorderlist_Model temp=listSet.get(i);
                        Log.e("getDateIs",temp.getTranDate());
                        if (temp.getTranDate().compareTo(date) == 0) {
                            passinList.add(temp);}
                    }
                }

                if(position==2)
                {
                    pass=true;
                    cal.add(Calendar.DATE, -1);
                    String date= df.format(cal.getTime());
                    for(int i=0;i<listSet.size();i++)
                    {
                        Salesorderlist_Model temp=listSet.get(i);
                        if (temp.getTranDate().compareTo(date) == 0) {
                            passinList.add(temp);}
                    }
                }

                if(position==3)
                {
                    pass=true;
                    for(int j=1;j<=7;j++)
                    {
                        callDate(j);

                    }
                }

                if(position==4)
                {
                    pass=true;
                    for(int j=1;j<=30;j++)
                    {
                        callDate(j);

                    }
                }

                if(position==5)
                {
                    to_et.setClickable(false);
                    to_et.setFocusable(false);
                    to_et.setEnabled(false);
                    FromTo=true;
                    pass=false;
                    custome_date_ll.setVisibility(View.VISIBLE);
                }
                else {
                    custome_date_ll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inVoice_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        enableProgress(progress_bar);
        checkConnection();
        if(internet)
        {
            callSalesOrderApi();
        }
        else
        {
            disableProgress(progress_bar);
            internetDialog();
        }}
        else if(source==2) {

            DataBase.Pair pair=db.getSaveSpecific(loginModel.getUserId().toString()) ;
            DataBase.Order order=db.getOrderSpecific(loginModel.getUserId().toString());
            listSubmitSales=pair.Saveorder();
            offlineSales=pair.SaveItem();


        /*    DataBase.Pair pair=db.saveAllList();
            listSubmitSales=pair.Saveorder();
            offlineSales=pair.SaveItem();*/

            toolbarText.setText("Save Sales");
            cornerImage.setVisibility(View.GONE);
            inVoice_et.setVisibility(View.GONE);

            returnRecycler.setLayoutManager(new LinearLayoutManager(ReturnActivity.this));
            returnRecycler.setItemAnimator(new DefaultItemAnimator());
            adapter=new ReturnAdapter(ReturnActivity.this,source,offlineSales);
            returnRecycler.setAdapter(adapter);
        }

        else {
            DataBase.Order order=db.getOrderSpecific(loginModel.getUserId().toString());
            listorderList=order.submitOrders();
            offlineSales=order.SaveItem();

            toolbarText.setText("Save Sales");
            cornerImage.setVisibility(View.GONE);
            inVoice_et.setVisibility(View.GONE);

            returnRecycler.setLayoutManager(new LinearLayoutManager(ReturnActivity.this));
            returnRecycler.setItemAnimator(new DefaultItemAnimator());
            adapter=new ReturnAdapter(ReturnActivity.this,source,offlineSales);
            returnRecycler.setAdapter(adapter);
        }



    }
    @Override
    public void onClick(View v) {

        if(v==filter_btn)
        {
            if(pass)
            {
                callRecyclerView(passinList);}

            if(FromTo)
            {
                String to=to_et.getText().toString();
                String fromet=from_et.getText().toString();
                if(TextUtils.isEmpty(to))
                {
                    callToast(ReturnActivity.this,"Date is Empty");
                    return;
                }
                if(TextUtils.isEmpty(fromet))
                {
                    callToast(ReturnActivity.this,"Date is Empty");
                    return;
                }


                DateFormat df = new SimpleDateFormat( "dd/MM/yyyy");
                try {
                    Date stertDate = df.parse(fromet);
                    Date enddate = df.parse(to);
                    List<Date> dates = new ArrayList<Date>();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(stertDate);
                    while (calendar.getTime().before(enddate))
                    {
                        Date result = calendar.getTime();
                        dates.add(result);
                        calendar.add(Calendar.DATE, 1);
                        String date = df.format(calendar.getTime());
                        Log.e("result",date+"");
                        callFilterDates(date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("ParseException",e.getMessage());
                    callToast(ReturnActivity.this,"Something is Wrong");
                }
                callRecyclerView(passinList);


            }
            parent_ll.setVisibility(View.GONE);
            inVoice_et.setVisibility(View.VISIBLE);
        }

        if(v==clear_btn)
        {   to_et.setText("");
            from_et.setText("");
            spinDate.setSelection(0);
            custome_date_ll.setVisibility(View.GONE);
            parent_ll.setVisibility(View.GONE);
            inVoice_et.setVisibility(View.VISIBLE);
        }

        if(v==back_img)
        {
            onBackPressed();
        }


        if(v==cornerImage)
        {
            inVoice_et.setVisibility(View.GONE);
            parent_ll.setVisibility(View.VISIBLE);
        }

        if(v==from_et)
        {

            DatePickerDialog dpDialog=  new DatePickerDialog(ReturnActivity.this, date2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dpDialog.getDatePicker().setMaxDate(new Date().getTime());
            dpDialog.show();
        }

        if(v==to_et)
        {
            DatePickerDialog dpDialog=  new DatePickerDialog(ReturnActivity.this, date1, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dpDialog.getDatePicker().setMinDate(fromDate.getTime());
            dpDialog.show();
        }}


    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            to_et.setText(sdf.format(myCalendar.getTime()));
            toDate=myCalendar.getTime();


        }};

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            from_et.setText(sdf.format(myCalendar.getTime()));
            fromDate=myCalendar.getTime();
            to_et.setText("");
            to_et.setClickable(true);
            to_et.setFocusable(true);
            to_et.setEnabled(true);
        }};


   /* public DatePickerDialog.OnDateSetListener getDate(final EditText editText){
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
    }*/


    //================================callOrderSalesApi====================================

    private void callSalesOrderApi() {

        Call<ArrayList<Salesorderlist_Model>> call=apiInterface.salesOrderList(custmer.getId(),loginModel.getUserId());
        call.enqueue(new Callback<ArrayList<Salesorderlist_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Salesorderlist_Model>> call, Response<ArrayList<Salesorderlist_Model>> response) {
                Log.e("callOrderSalesApi","ok");
                disableProgress(progress_bar);
                ArrayList<Salesorderlist_Model>model=response.body();
                callRecyclerView(model);
                callInvoice();
                listSet.addAll(model);
            }
            @Override
            public void onFailure(Call<ArrayList<Salesorderlist_Model>> call, Throwable t) {
                Log.e("hello",t.getMessage());
                failMethod(); }});}

    private void failMethod()
    {
        disableProgress(progress_bar);
        checkConnection();
        if(!internet)
        {
            Toast.makeText(ReturnActivity.this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(ReturnActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

        }}

    private  void callInvoice()
    {
        inVoice_et.addTextChangedListener(new TextWatcher() {
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

    //==========================================CallRecyclerMethod==================================

    private void callRecyclerView(ArrayList<Salesorderlist_Model>model)
    {
        returnRecycler.setLayoutManager(new LinearLayoutManager(ReturnActivity.this));
        returnRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter=new ReturnAdapter(ReturnActivity.this,model,source);
        returnRecycler.setAdapter(adapter);
    }

    //==========================================DatesMethods========================================
    private void callDate(int j)
    {
        DateFormat df2 = new SimpleDateFormat( "dd/MM/yyyy");
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_YEAR, -j);
        String date = df2.format(  cal2.getTime());
        Log.e("date",date);
        for(int i=0;i<listSet.size();i++)
        {
            Salesorderlist_Model temp=listSet.get(i);
            if (temp.getTranDate().compareTo(date) == 0) {
                passinList.add(temp);}
        }
    }

    private void callFilterDates(String date)
    {
        for(int i=0;i<listSet.size();i++)
        {
            Salesorderlist_Model temp=listSet.get(i);
            if (temp.getTranDate().compareTo(date) == 0) {
                passinList.add(temp);}
        }
    }

}
