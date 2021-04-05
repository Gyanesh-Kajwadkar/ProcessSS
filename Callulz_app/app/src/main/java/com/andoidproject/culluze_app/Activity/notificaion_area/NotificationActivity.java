package com.andoidproject.culluze_app.Activity.notificaion_area;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.andoidproject.culluze_app.Activity.notificaion_area.fragment.MessageFragment;
import com.andoidproject.culluze_app.Activity.notificaion_area.fragment.NotificationFragment;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView back_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        init();
    }
    private void init()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        back_img=findViewById(R.id.back_img);


        back_img.setOnClickListener(this);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        NotificationActivity.ViewPagerAdapter adapter = new NotificationActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NotificationFragment(),"NOTIFICATION");
        adapter.addFragment(new MessageFragment(), "MESSAGE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        if(v==back_img)
        {
            onBackPressed();
        }

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
