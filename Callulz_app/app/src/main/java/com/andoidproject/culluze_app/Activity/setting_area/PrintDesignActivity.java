package com.andoidproject.culluze_app.Activity.setting_area;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andoidproject.culluze_app.Activity.setting_area.fragment.PrintDesignFragment;
import com.andoidproject.culluze_app.R;

public class PrintDesignActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout copmanyInfo_rl,billInfo_rl,summary_rl;
    ImageView back_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_design);
        initView();
    }

    public void initView(){
        copmanyInfo_rl = findViewById(R.id.copmanyInfo_rl);
        billInfo_rl = findViewById(R.id.billInfo_rl);
        summary_rl = findViewById(R.id.summary_rl);
        back_img=findViewById(R.id.back_img);

        copmanyInfo_rl.setOnClickListener(this);
        billInfo_rl.setOnClickListener(this);
        summary_rl.setOnClickListener(this);
        back_img.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }

        if (v==copmanyInfo_rl){
              goNext(new PrintDesignFragment(),"CompanyInfo");
        }

        if (v==billInfo_rl){
            goNext(new PrintDesignFragment(),"BillInfo");
        }
        if (v==summary_rl){
            goNext(new PrintDesignFragment(),"Summary");
        }
    }

    private void goNext(Fragment fragment, String itemName)
    {
        Bundle bundle = new Bundle();
        bundle.putString("itemName",itemName);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.printDesign_framl,fragment)
                  .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        //clearBackStack();
    }
    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
