package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.activity.LoginActivity;
import com.andoidproject.culluze_app.Activity.activity.Main_Menu_Activity;
import com.andoidproject.culluze_app.Activity.model.SubmitCheckInModel;
import com.andoidproject.culluze_app.Activity.utils.SharedPreferanceUtils;
import com.andoidproject.culluze_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;

public class Check_in_Activity extends Base_Activity implements View.OnClickListener {

    EditText time_et,date_et,remark_et;
    LinearLayout remark_ll,date_time_ll;
    Button button_update;
    RadioGroup radio_group;
    RadioButton reschedule_rb,noOrder_rb;
    View name_view;
    TextView toolbarText;
    ImageView back_img;
    ProgressBar progress_circular;
    SharedPreferanceUtils sp;

    int checkFlag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_);
        init();

    }

    private void init()
    {
        time_et=findViewById(R.id.time_et);
        date_et=findViewById(R.id.date_et);
        remark_et=findViewById(R.id.remark_et);
        remark_ll=findViewById(R.id.remark_ll);
        date_time_ll=findViewById(R.id.date_time_ll);
        button_update=findViewById(R.id.button_update);
        radio_group=findViewById(R.id.radio_group);
        reschedule_rb=findViewById(R.id.reschedule_rb);
        noOrder_rb=findViewById(R.id.noOrder_rb);
        name_view=findViewById(R.id.name_view);
        progress_circular=findViewById(R.id.progress_circular);

        sp = new SharedPreferanceUtils(this);

        toolbarText=findViewById(R.id.toolbarText);
        toolbarText.setText("Check In");
        back_img=findViewById(R.id.back_img);


        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.noOrder_rb:
                        checkFlag=0;
                        remark_ll.setVisibility(View.VISIBLE);
                        date_time_ll.setVisibility(View.GONE);
                        break;
                    case R.id.reschedule_rb:
                        checkFlag=1;
                        date_time_ll.setVisibility(View.VISIBLE);
                        remark_ll.setVisibility(View.GONE);
                        break;

                }
            }
        });

        date_et.setOnClickListener(this);
        time_et.setOnClickListener(this);
        button_update.setOnClickListener(this);
        back_img.setOnClickListener(this);


        remark_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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


        date_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              date_et.setError(null);
            }
        });

        time_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                time_et.setError(null);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if(v==date_et)
        {
            new DatePickerDialog(Check_in_Activity.this, getDate(date_et), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();}
        if(v==time_et)
        {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            final int second = mcurrentTime.get(Calendar.SECOND);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(Check_in_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    time_et.setText( selectedHour + ":" + selectedMinute );
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }

        if(v==back_img)
        {
            onBackPressed();
        }
        if(v==button_update)
        {
            int userId = (int) sp.getParam("userId", 0);

            SubmitCheckInModel submitCheckInModel = new SubmitCheckInModel();
            submitCheckInModel.setUsrId(userId);
            submitCheckInModel.setCustId(custmer.getId());

            if( checkFlag==0)
            {
                submitCheckInModel.setChkFlg("NOR");
                submitCheckInModel.setResheDt("");
                submitCheckInModel.setResheTim("");
            }

            else {
                String date=date_et.getText().toString();
                String time=time_et.getText().toString();
                if(date.isEmpty())
                {
                    date_et.setError("Required");
                    date_et.requestFocus();
                    return;
                }
                date_et.setError(null);
                if(time.isEmpty())
                {
                    time_et.setError("Required");
                    time_et.requestFocus();
                    return;
                }
                time_et.setError(null);
                submitCheckInModel.setChkFlg("RSH");
                submitCheckInModel.setResheDt(date_et.getText().toString());
                submitCheckInModel.setResheTim(time_et.getText().toString()+":"+"00");
            }

            checkConnection();
            if(internet) {
                enableProgress(progress_circular);
                callCheckInApi(submitCheckInModel);
            }
            else {
                internetDialog();
            }
        }

    }

    private void callCheckInApi(  SubmitCheckInModel submitCheckInModel)
    {
        Call<String> call=apiInterface.submitCheck(submitCheckInModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    String results = jsonObject.getString("results");


                    if(results.equals("N"))
                    {
                        disableProgress(progress_circular);
                        Toast.makeText(Check_in_Activity.this, "Unable To proceed", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        disableProgress(progress_circular);
                        Toast.makeText(Check_in_Activity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

            } catch (JSONException e)
                {
                    Toast.makeText(Check_in_Activity.this, "Unable To proceed", Toast.LENGTH_SHORT).show();
                    disableProgress(progress_circular);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("error", t.getMessage());
                disableProgress(progress_circular);
                checkConnection();
                if(!internet)
                {
                    Toast.makeText(Check_in_Activity.this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}
