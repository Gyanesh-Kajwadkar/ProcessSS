package com.andoidproject.culluze_app.Activity.help_area;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andoidproject.culluze_app.Activity.help_area.adapter.FaqItemDetailsAdapter;
import com.andoidproject.culluze_app.R;


public class FaqDetailsActivity extends AppCompatActivity implements View.OnClickListener{
   RecyclerView faqDetails_rv;
   FaqItemDetailsAdapter faqItemDetailsAdapter;
   ImageView back_img;



  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq_details, container, false);
        initView(view);
        return view;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_faq_details);
        initView();


    }
    public void initView()
    {
        faqDetails_rv = findViewById(R.id.faqDetails_rv);
        back_img = findViewById(R.id.back_img);

        back_img.setOnClickListener(this);

        callFaqItemDetailsAdapter();
    }

    public void callFaqItemDetailsAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        faqDetails_rv.setLayoutManager(layoutManager);
        faqItemDetailsAdapter = new FaqItemDetailsAdapter(this);
        faqDetails_rv.setAdapter(faqItemDetailsAdapter);
        faqItemDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v==back_img){
            onBackPressed();
        }
    }
}
