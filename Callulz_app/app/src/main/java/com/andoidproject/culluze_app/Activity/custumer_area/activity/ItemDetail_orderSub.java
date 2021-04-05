package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.custumer_area.fragment.BlankFragment;
import com.andoidproject.culluze_app.Activity.custumer_area.fragment.Items_OrderFrag;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;
import java.util.List;

import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;

public class ItemDetail_orderSub extends AppCompatActivity implements View.OnClickListener{

    ImageView back_img;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String currentPosition;
    EditText orderQuentity_et,freeQuantity_et;
    TextView save_tv,title_name,subTitle_name,stock_tv,mrp_tv,rate_tv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_order_sub);
        init();
    }

    private void init()
    {
        back_img=findViewById(R.id.back_img);
        orderQuentity_et=findViewById(R.id.orderQuentity_et);
        freeQuantity_et=findViewById(R.id.freeQuantity_et);
        save_tv=findViewById(R.id.save_tv);
        title_name=findViewById(R.id.title_name);
        subTitle_name=findViewById(R.id.subTitle_name);
        stock_tv=findViewById(R.id.stock_tv);
        mrp_tv=findViewById(R.id.mrp_tv);
        rate_tv=findViewById(R.id.rate_tv);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //Toast.makeText(this, currentPosition, Toast.LENGTH_SHORT).show();
        back_img.setOnClickListener(this);
        save_tv.setOnClickListener(this);

        intent = getIntent();
        if (intent.hasExtra("currentPosition") && intent.hasExtra("list")) {
            currentPosition = intent.getStringExtra("currentPosition");
            Brand_SubBrand_Model.Item mode = (Brand_SubBrand_Model.Item)intent.getSerializableExtra("list");

          //  Log.e("quantity",mode.getQuantity());
            if(mode.getQuantity()!=0)
            { orderQuentity_et.setText(mode.getQuantity()+"");
            orderQuentity_et.setSelection(orderQuentity_et.getText().length());}
            else {
                orderQuentity_et.setText("");
            }
            freeQuantity_et.setText(mode.getFreetv());
            title_name.setText(mode.getItmName());
            subTitle_name.setText(mode.getBrand());
            stock_tv.setText(mode.getAvilStk()+"");
            mrp_tv.setText(mode.getMrp()+"");

            String customerRate=custmer.getCustRate();

            if(customerRate.equals("ITM-RT"))
            {
               rate_tv.setText(mode.getRTSWT()+"");
            }
            else if(customerRate.equals("ITM-WH"))
            {
             rate_tv.setText(mode.getWHSWT()+"");
            }
            else if(customerRate.equals("ITM-DI"))
            {
                rate_tv.setText(mode.getDTSWT()+"");
            }
            else if(customerRate.equals("ITM-EX"))
            {
                rate_tv.setText(mode.getEXSWT()+"");
            }


            if(mode.getAvilStk()<=0)
            {
                stock_tv.setTextSize(16);
                stock_tv.setText("STOCK: Out of Stock");
                stock_tv.setTextColor(Color.RED);
                orderQuentity_et.setFocusable(false);
                orderQuentity_et.setClickable(false);
                orderQuentity_et.setCursorVisible(false);
            }


        }

        if (intent.hasExtra("currentPosition2") && intent.hasExtra("list2")) {
            currentPosition = intent.getStringExtra("currentPosition2");
           FrequentlyModel mode = (FrequentlyModel) intent.getSerializableExtra("list2");

            //  Log.e("quantity",mode.getQuantity());
            if(mode.getQuantity()!=0)
            {
            orderQuentity_et.setText(mode.getQuantity()+"");
            orderQuentity_et.setSelection(orderQuentity_et.getText().length());}
            else {
                orderQuentity_et.setText("");
            }

            freeQuantity_et.setText(mode.getFreetv());
            title_name.setText(mode.getItmName());
            subTitle_name.setText(mode.getBrand());
            stock_tv.setText(mode.getAvilStk()+"");
            mrp_tv.setText(mode.getMrp()+"");

            String customerRate=custmer.getCustRate();

            if(customerRate.equals("ITM-RT"))
            {
                rate_tv.setText(mode.getRTSWT()+"");
            }
            else if(customerRate.equals("ITM-WH"))
            {
                rate_tv.setText(mode.getWHSWT()+"");
            }
            else if(customerRate.equals("ITM-DI"))
            {
                rate_tv.setText(mode.getDTSWT()+"");
            }
            else if(customerRate.equals("ITM-EX"))
            {
                rate_tv.setText(mode.getEXSWT()+"");
            }


            if(mode.getAvilStk()<=0)
            {
                stock_tv.setTextSize(16);
                stock_tv.setText("STOCK: Out of Stock");
                stock_tv.setTextColor(Color.RED);
                orderQuentity_et.setFocusable(false);
                orderQuentity_et.setClickable(false);
                orderQuentity_et.setCursorVisible(false);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ItemDetail_orderSub.ViewPagerAdapter adapter = new ItemDetail_orderSub.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BlankFragment(),"ITEMS");
        adapter.addFragment(new BlankFragment(), "FOCUSED");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        if(back_img==v)
        {

            onBackPressed();
         /*   Intent intent = new Intent();
            intent.putExtra("quantity", orderQuentity_et.getText().toString());
            intent.putExtra("currentPosition", currentPosition);
            setResult(RESULT_OK,intent);
            finish();*/
        }

    if(v==save_tv)
    {
        if (intent.hasExtra("currentPosition") && intent.hasExtra("list")) {
       /* if(order==2)
        {*/
        if(!orderQuentity_et.getText().toString().isEmpty() && !freeQuantity_et.getText().toString().isEmpty())
        {
            int qty= Integer.parseInt(orderQuentity_et.getText().toString());
            int free= Integer.parseInt(freeQuantity_et.getText().toString());
            if(qty<free)
            {
                Toast.makeText(this, "Order Qty must be greater then Free Qty", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent intent = new Intent();
        intent.putExtra("quantity", orderQuentity_et.getText().toString());
        intent.putExtra("free", freeQuantity_et.getText().toString());
        intent.putExtra("currentPosition", currentPosition);
        setResult(RESULT_OK,intent);
        finish();
    //}
        }
        if (intent.hasExtra("currentPosition2") && intent.hasExtra("list2")) {
            if(order==2)
            {
                if(!orderQuentity_et.getText().toString().isEmpty() && !freeQuantity_et.getText().toString().isEmpty())
                {
                    int qty= Integer.parseInt(orderQuentity_et.getText().toString());
                    int free= Integer.parseInt(freeQuantity_et.getText().toString());
                    if(qty<free)
                    {
                        Toast.makeText(this, "Order Qty must be greater then Free Qty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent();
                intent.putExtra("quantity", orderQuentity_et.getText().toString());
                intent.putExtra("free", freeQuantity_et.getText().toString());
                intent.putExtra("currentPosition", currentPosition);
                setResult(RESULT_OK,intent);
                finish();
            }}



    }

    onBackPressed();

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);}

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
