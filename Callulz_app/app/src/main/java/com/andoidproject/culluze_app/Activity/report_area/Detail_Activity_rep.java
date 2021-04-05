package com.andoidproject.culluze_app.Activity.report_area;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.report_area.adapter.DetilAdapter;
import com.andoidproject.culluze_app.R;

public class Detail_Activity_rep extends AppCompatActivity implements View.OnClickListener{

    ImageView back_img;
    TextView toolbarText;
    int message;
    RecyclerView detailRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__rep);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getInt("detail");
        init();
    }
    private void init()
    {

        back_img=findViewById(R.id.back_img);
        toolbarText=findViewById(R.id.toolbarText);
        toolbarText.setText("Detail");

        detailRecycler = findViewById(R.id.detailRecycler);
        detailRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailRecycler.setItemAnimator(new DefaultItemAnimator());

        if(message==1) {
             int sizeList=1;
            DetilAdapter adapter = new DetilAdapter(this, message,sizeList);
            detailRecycler.setAdapter(adapter);
        }
        if( message==2) {

            int sizeList=5;
            DetilAdapter adapter = new DetilAdapter(this, message,sizeList);
            detailRecycler.setAdapter(adapter);
        }

        back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }
    }
}
