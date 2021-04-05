package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.model.CustomerBill_Model;
import com.andoidproject.culluze_app.Activity.report_area.adapter.ReturnAdapter;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userBalance;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.userId;

public class Outstanding_activity extends Base_Activity implements View.OnClickListener {


    // --------------------------------------have its own recycler class
    ImageView cornerImage,back_img;
    Button outstanding_btn;
    RecyclerView outstanding_recycler;
    List<CustomerBill_Model.Acct>accts;
    LinearLayout payment_ll,balance_ll;
    TextView payment_tv,adjustment_tv,balance_tv;
    String sessionId;
    float calculationAmount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstanding_activity);
        init();
    }


    private void init()
    {
        cornerImage=findViewById(R.id.cornerImage);
        back_img=findViewById(R.id.back_img);
        outstanding_recycler=findViewById(R.id.outstanding_recycler);
        outstanding_btn=findViewById(R.id.outstanding_btn);

        payment_ll=findViewById(R.id.payment_ll);
        balance_ll=findViewById(R.id.balance_ll);

        payment_tv=findViewById(R.id.payment_tv);
        adjustment_tv=findViewById(R.id.adjustment_tv);
        balance_tv=findViewById(R.id.balance_tv);


        back_img.setOnClickListener(this);
        cornerImage.setOnClickListener(this);
        outstanding_btn.setOnClickListener(this);

        outstanding_recycler.setLayoutManager(new LinearLayoutManager(Outstanding_activity.this));
        outstanding_recycler.setItemAnimator(new DefaultItemAnimator());
        Outstanding_Adapter adapter= new Outstanding_Adapter();
        outstanding_recycler.setAdapter(adapter);


         sessionId = getIntent().getStringExtra("getRecept");

        if(sessionId!=null)
        {

            formula(userBalance);
            payment_ll.setVisibility(View.VISIBLE);
            balance_ll.setVisibility(View.VISIBLE);
        }

            accts= (List<CustomerBill_Model.Acct>) getIntent().getSerializableExtra("bills");
            Log.e("size",accts.size()+"");
        //  CustomerBill_Api();

    }


    @Override
    public void onClick(View v) {

        if(cornerImage==v)
        {

        }

        if(back_img==v)
        {
            onBackPressed();
        }

        if(v==outstanding_btn)
        {
           // alertMessage("Cannot Proceed without any Adjustment");
            Intent intent= new Intent(Outstanding_activity.this,ReceptActivity.class);
            intent.putExtra("getRecept",payment_tv.getText().toString());
            startActivity(intent);
        }

    }

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


   private class Outstanding_Adapter extends  RecyclerView.Adapter<Outstanding_Adapter.ViewHolders>
    {

        Boolean tick=true;
        @NonNull
        @Override
        public Outstanding_Adapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.outstanding_view,viewGroup,false);
            return new ViewHolders(v); }

        @Override
        public void onBindViewHolder(@NonNull final Outstanding_Adapter.ViewHolders viewHolders, int i) {

            final CustomerBill_Model.Acct acc=accts.get(i);
            viewHolders.mainAmount.setText(acc.getSalesAmt()+"");
            setTextColour(acc.getSalesAmt(),viewHolders.mainAmount);

            viewHolders.clickable_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
             /*       Intent intent= new Intent(Outstanding_activity.this,ReceptActivity.class);
                    startActivity(intent);*/

             if(tick)
             {
                 viewHolders.check_radio.setChecked(true);

                 viewHolders.mainAmount.setText(acc.getSalesAmt()+"");
                 setTextColour(acc.getSalesAmt(),viewHolders.mainAmount);
                 viewHolders.copyOfMain.setText(acc.getSalesAmt()+"");
                 setTextColour(acc.getSalesAmt(),viewHolders.copyOfMain);
                 viewHolders.copyOfMain.setVisibility(View.VISIBLE);
                 tick=false;}

             else {
                 viewHolders.check_radio.setChecked(false);
                 viewHolders.copyOfMain.setVisibility(View.GONE);
                 viewHolders.mainAmount.setText(acc.getSalesAmt()+"");
                 setTextColour(acc.getSalesAmt(),viewHolders.mainAmount);
                 tick=true;

             }
                }
            });
            if(sessionId!=null)
            {

               viewHolders.check_radio.setChecked(true);
            }

            viewHolders.titleOut.setText(acc.getTranNo());
            viewHolders.dateOut.setText(acc.getTranDate());

            viewHolders.check_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if(isChecked)
                  {
                      viewHolders.mainAmount.setText(acc.getSalesAmt()+"");
                      setTextColour(acc.getSalesAmt(),viewHolders.mainAmount);
                      viewHolders.copyOfMain.setText(acc.getSalesAmt()+"");
                      setTextColour(acc.getSalesAmt(),viewHolders.copyOfMain);
                      viewHolders.copyOfMain.setVisibility(View.VISIBLE);
                      formula(acc.getSalesAmt()+"");
                  }
                  else {
                      viewHolders.copyOfMain.setVisibility(View.GONE);
                      setTextColour(acc.getSalesAmt(),viewHolders.mainAmount);
                      //formula("0");
                      reverseFormula(acc.getSalesAmt()+"");
                  }
                }
            });


        }

        @Override
        public int getItemCount() {
            return accts.size();
        }

        public class ViewHolders extends RecyclerView.ViewHolder {
            LinearLayout clickable_ll;
            SwitchCompat check_radio;
            TextView titleOut,dateOut,copyOfMain,mainAmount;
            public ViewHolders(@NonNull View itemView) {
                super(itemView);
                clickable_ll=itemView.findViewById(R.id.clickable_ll);
                check_radio=itemView.findViewById(R.id.check_radio);
                titleOut=itemView.findViewById(R.id.titleOut);
                dateOut=itemView.findViewById(R.id.dateOut);
                copyOfMain=itemView.findViewById(R.id.copyOfMain);
                mainAmount=itemView.findViewById(R.id.mainAmount);
            }
        }
    }

    private void formula(String adjustment)
    {
        calculationAmount=calculationAmount+Float.parseFloat(adjustment);
        float userBal= Float.parseFloat(adjustment);
        setTextColour(calculationAmount,adjustment_tv);
        adjustment_tv.setText(calculationAmount+"");


        if(sessionId!=null)
        {
            setTextColour(Float.parseFloat(sessionId),payment_tv);
            payment_tv.setText(sessionId);

            float balanceTotal=calculationAmount-Float.parseFloat(sessionId);
            setTextColour(balanceTotal,balance_tv);
            balance_tv.setText(balanceTotal+"");
        }

    }

    private void setTextColour(float textColour,TextView text)
    {
        if(textColour>0)
        {
            text.setTextColor(getResources().getColor(R.color.green));
        }

        else if(textColour==0)
        {
            text.setTextColor(getResources().getColor(R.color.black));
        }

        else {
            text.setTextColor(getResources().getColor(R.color.toolbarRed));
        }

    }


    private void CustomerBill_Api()
    {
        Call<CustomerBill_Model> call=apiInterface.customerBill(21);
        call.enqueue(new Callback<CustomerBill_Model>() {
            @Override
            public void onResponse(Call<CustomerBill_Model> call, Response<CustomerBill_Model> response) {

                try
                {
                    CustomerBill_Model model=response.body();
                    accts=model.getAcct();


                }
                catch (Exception e)
                {
                    Toast.makeText(Outstanding_activity.this, "No Customer Bill Found", Toast.LENGTH_SHORT).show();
                }

                Log.e("customerBill","success");

            }

            @Override
            public void onFailure(Call<CustomerBill_Model> call, Throwable t) {
                Log.e("customerBillfail",t.getMessage());
            }
        });
    }

    private void reverseFormula(String adjustment)
    {
        calculationAmount=calculationAmount-Float.parseFloat(adjustment);


        float userBal= Float.parseFloat(adjustment);
        setTextColour(calculationAmount,adjustment_tv);
        adjustment_tv.setText(calculationAmount+"");

        if(sessionId!=null)
        {
            setTextColour(Float.parseFloat(sessionId),payment_tv);
            payment_tv.setText(sessionId);

            float balanceTotal=calculationAmount-Float.parseFloat(sessionId);
            setTextColour(balanceTotal,balance_tv);
            balance_tv.setText(balanceTotal+"");
        }
    }


}
