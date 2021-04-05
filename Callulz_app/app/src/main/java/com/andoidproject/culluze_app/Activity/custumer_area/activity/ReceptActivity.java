package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.DatePickerDialog;
import android.media.effect.Effect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.model.PaymentMode_Model;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userBalance;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userName;

public class ReceptActivity extends Base_Activity implements View.OnClickListener {

    Spinner spinner_payment;
    TextView toolbarText,balanceText,receptText;
    ImageView backImg;
    LinearLayout cash_ll,cheque_ll,dd_ll,chequeFill_ll,ddFill_ll;
    EditText remark_et,date_et,datedd_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);
        init();

    }

    public void init()
    {
        cash_ll=findViewById(R.id.cash_ll);
        cheque_ll=findViewById(R.id.cheque_ll);
        dd_ll=findViewById(R.id.dd_ll);
        toolbarText=findViewById(R.id.toolbarText);
        backImg=findViewById(R.id.back_img);
        remark_et=findViewById(R.id.remark_et);
        chequeFill_ll=findViewById(R.id.chequeFill_ll);
        date_et=findViewById(R.id.date_et);
        ddFill_ll=findViewById(R.id.ddFill_ll);
        datedd_et=findViewById(R.id.datedd_et);
        balanceText=findViewById(R.id.balanceText);
        spinner_payment=findViewById(R.id.spinner_payment);
        receptText=findViewById(R.id.receptText);


        toolbarText.setText(userName);

        float userBal= Float.parseFloat(userBalance);

        if(userBal>0)
        {
            balanceText.setTextColor(getResources().getColor(R.color.green));
        }

        else if(userBal==0)
        {
            balanceText.setTextColor(getResources().getColor(R.color.black));
        }

        else {
            balanceText.setTextColor(getResources().getColor(R.color.toolbarRed));
        }
        balanceText.setText(userBalance);


        cash_ll.setOnClickListener(this);
        cheque_ll.setOnClickListener(this);
        dd_ll.setOnClickListener(this);
        backImg.setOnClickListener(this);
        date_et.setOnClickListener(this);
        datedd_et.setOnClickListener(this);

        PaymentMethod_Api("CASH");


       String sessionId = getIntent().getStringExtra("getRecept");
        receptText.setText(sessionId);
    }
    @Override
    public void onClick(View v) {

        if(cash_ll==v)
        {
            setDefaultColour();
            chequeFill_ll.setVisibility(View.GONE);
            ddFill_ll.setVisibility(View.GONE);
            cash_ll.setBackgroundResource(R.color.backColour);
            PaymentMethod_Api("CASH");

        }
        if(cheque_ll==v)
        {
            chequeFill_ll.setVisibility(View.VISIBLE);
            ddFill_ll.setVisibility(View.GONE);
            setDefaultColour();
            cheque_ll.setBackgroundResource(R.color.backColour);
            PaymentMethod_Api("CHEQUE");
        }
        if(dd_ll==v)
        {
            chequeFill_ll.setVisibility(View.GONE);
            ddFill_ll.setVisibility(View.VISIBLE);
            setDefaultColour();
            dd_ll.setBackgroundResource(R.color.backColour);
            PaymentMethod_Api("DD");
        }

        if(v==backImg)
        {
            onBackPressed();
        }

        if(v==date_et)
        {
            new DatePickerDialog(this, getDate(date_et), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

        if(v==datedd_et)
        {
            new DatePickerDialog(this, getDate(datedd_et), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } }

    private void setDefaultColour()
    {
        cash_ll.setBackgroundResource(R.color.white);
        cheque_ll.setBackgroundResource(R.color.white);
        dd_ll.setBackgroundResource(R.color.white);
    }

    private void PaymentMethod_Api(final String mode)
    {
        final ArrayList<String>lists=new ArrayList<>();
        Call<ArrayList<PaymentMode_Model>> call=apiInterface.paymentMode(mode);
        call.enqueue(new Callback<ArrayList<PaymentMode_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentMode_Model>> call, Response<ArrayList<PaymentMode_Model>> response) {

                ArrayList<PaymentMode_Model>brand=response.body();
                for(int i=0;i<brand.size();i++)
                {
                    PaymentMode_Model model=brand.get(i);
                    lists.add(model.getAcctName());
                }
                Log.e("payment","sucess");

                ArrayAdapter  adapter =
                        new ArrayAdapter (getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, lists);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spinner_payment.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<PaymentMode_Model>> call, Throwable t)
            {
                Log.e("paymentfail",t.getMessage());
            }
        });

    }
}
