package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.model.CustomerBill_Model;
import com.andoidproject.culluze_app.Activity.model.Salesorderlist_Model;
import com.andoidproject.culluze_app.Activity.report_area.CusProduct_AnaSub;
import com.andoidproject.culluze_app.Activity.report_area.Filter_Activity;
import com.andoidproject.culluze_app.Activity.report_area.Reports_Activity;
import com.andoidproject.culluze_app.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userBalance;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userName;

public class Collection_activity extends Base_Activity implements View.OnClickListener {
    ImageView back_img;
    Button onAccount_btn,billwise_btn;
    TextView toolbarText,accbalance,showTotal;
    EditText receptAmount_et;
    ProgressBar progress_bar ;
    List<CustomerBill_Model.Acct> accts= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_act);

        init();
        checkConnection();
        if(internet)
        {
            enableProgress(progress_bar);
             CustomerBill_Api();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void init()
    {
        back_img=findViewById(R.id.back_img);
        onAccount_btn=findViewById(R.id.onAccount_btn);
        toolbarText=findViewById(R.id.toolbarText);
        billwise_btn=findViewById(R.id.billwise_btn);
        accbalance=findViewById(R.id.accbalance);
        receptAmount_et=findViewById(R.id.receptAmount_et);
        showTotal=findViewById(R.id.showTotal);
        progress_bar=findViewById(R.id.progress_bar);

        toolbarText.setText(userName);

        float userBal= Float.parseFloat(userBalance);

         if(userBal>0)
         {
             accbalance.setTextColor(getResources().getColor(R.color.green));
         }

         else if(userBal==0)
        {
            accbalance.setTextColor(getResources().getColor(R.color.black));
        }

         else {
             accbalance.setTextColor(getResources().getColor(R.color.toolbarRed));
         }

        back_img.setOnClickListener(this);
        onAccount_btn.setOnClickListener(this);
        billwise_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(back_img==v)
        {
            onBackPressed();
        }

        if(onAccount_btn==v)
        {
            alertMessage("Amount must be entered for On Account process");
          /*  enableProgress(progress_bar);
            if(internet)
            {
                callSalesOrderApi();                }
            else
            {
                disableProgress(progress_bar);
                internetDialog();
            }*/
        }

        if(billwise_btn==v)
        {

           String getRecept= String.valueOf(receptAmount_et.getText());

            Intent intent= new Intent(this,Outstanding_activity.class);
            intent.putExtra("bills", (Serializable) accts);
            if(!getRecept.isEmpty()&& !getRecept.equals("0"))
            {
                intent.putExtra("getRecept",getRecept);
            }
            startActivity(intent);
            //alertMessage("Cannot Proceed without any Adjustment");
        }
    }

    //================================callBillApi====================================
    private void CustomerBill_Api()
    {
        Call<CustomerBill_Model> call=apiInterface.customerBill(custmer.getAcctId());
        call.enqueue(new Callback<CustomerBill_Model>() {
            @Override
            public void onResponse(Call<CustomerBill_Model> call, Response<CustomerBill_Model> response) {

                try
                {
                    CustomerBill_Model model=response.body();
                    accts=model.getAcct();
                    showTotal.setText(accts.size()+"");
                    accbalance.setText("₹"+model.getAcctBal());
                    disableProgress(progress_bar);
                }
                catch (Exception e)
                {     disableProgress(progress_bar);
                    Toast.makeText(Collection_activity.this, "No Customer Bill Found", Toast.LENGTH_SHORT).show();
                }

                Log.e("customerBill","success");

            }

            @Override
            public void onFailure(Call<CustomerBill_Model> call, Throwable t) {
                Log.e("customerBillfail",t.getMessage());
                disableProgress(progress_bar);
                checkConnection();
                if(!internet)
                {
                    Toast.makeText(Collection_activity.this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
