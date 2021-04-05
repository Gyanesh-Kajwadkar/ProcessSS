package com.andoidproject.culluze_app.Activity.route_area;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.route_area.adapter.CommonAdapter;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

import static com.andoidproject.culluze_app.Activity.route_area.adapter.RoutePlanner_Adapter.setTitle;

public class Route_planner_sub extends Base_Activity implements View.OnClickListener {

    RecyclerView common_recylcer;
    ArrayList<String> beat=new ArrayList<>();
    ArrayList<String>market=new ArrayList<>();
    ArrayList<String>area=new ArrayList<>();
    TextView toolbarText;
    ImageView back_img;
    Button apply_route_btn;
    EditText search_et;
    View name_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planner_sub);


        beat.add("Local");
        beat.add("MONDAY");
        beat.add("TUESDAY");
        beat.add("WEDNESDAY");
        beat.add("THURSDAY");
        beat.add("FRIDAY");

        market.add("X MAIN ROAD");
        market.add("Y MAIN ROAD");
        market.add("Z MAIN ROAD");
        market.add("A MAIN ROAD");
        market.add("B MAIN ROAD");

        area.add("X AREA");
        area.add("Y AREA");
        area.add("Z AREA");
        area.add("A AREA");
        area.add("B AREA");

        init();
    }

    private void init()
    {
        common_recylcer=findViewById(R.id.common_recylcer);
        toolbarText=findViewById(R.id.toolbarText);
        back_img=findViewById(R.id.back_img);
        apply_route_btn=findViewById(R.id.apply_route_btn);

        name_view=findViewById(R.id.name_view);
        search_et=findViewById(R.id.search_et);

        common_recylcer.setLayoutManager(new LinearLayoutManager(this));
        common_recylcer.setItemAnimator(new DefaultItemAnimator());
        if(setTitle.equals("A"))
        {   toolbarText.setText("Area");
            CommonAdapter adapter= new CommonAdapter(area);
            common_recylcer.setAdapter(adapter);
        }
        if(setTitle.equals("B"))
        {
            toolbarText.setText("Beat");
            CommonAdapter adapter= new CommonAdapter(beat);
            common_recylcer.setAdapter(adapter);
        }
        if(setTitle.equals("C"))
        {
            toolbarText.setText("Market");
            CommonAdapter adapter= new CommonAdapter(market);
            common_recylcer.setAdapter(adapter);
        }

        back_img.setOnClickListener(this);
        apply_route_btn.setOnClickListener(this);



        search_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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


    }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }
        if(v==apply_route_btn)
        {
            onBackPressed();
        }

    }
}
