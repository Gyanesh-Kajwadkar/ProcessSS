package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.fragment.Cart_OrderFrag;
import com.andoidproject.culluze_app.Activity.custumer_area.fragment.Items_OrderFrag;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.Activity.utils.SharedPreferanceUtils;
import com.andoidproject.culluze_app.Activity.widgets.LockableViewPager;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;
import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;

public class Sales_Activity extends Base_Activity implements View.OnClickListener{

    ImageView back_img;
    TextView toolbarText;
    ImageView cornerImage;
    boolean imageback=false;
    public static ArrayList<FrequentlyModel>staticFreqList=new ArrayList<>();
    SharedPreferanceUtils sp;
    public static boolean shopMore=false;
    public  static int cartdigit=staticFreqList.size()+staticItemList.size();
    public static   TextView cartItemNumber_tv;
    private TabLayout tabLayout;
    private LockableViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.tag_white,
            R.drawable.cart_white
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_);
        init();
    }
    private void init()
    {
        sp = new SharedPreferanceUtils(this);
        back_img=findViewById(R.id.back_img);
        cornerImage=findViewById(R.id.cornerImage);
        cartItemNumber_tv=findViewById(R.id.cartItemNumber_tv);
        cornerImage.setImageResource(R.drawable.menu__dot);
        toolbarText=findViewById(R.id.toolbarText);
        if(order==1)
        { cartItemNumber_tv.setText("0");
            toolbarText.setText("Sales");
        }
        else
            {
                cartItemNumber_tv.setText("0");
                toolbarText.setText("Order");
            }


       /* if(order==2  )
        {*/
            List<Brand_SubBrand_Model.Item> questions = new ArrayList<Brand_SubBrand_Model.Item>();
            questions = (ArrayList<Brand_SubBrand_Model.Item>)getIntent().getSerializableExtra("itemList");
            boolean restoredText = (boolean) sp.getParam("staticList",false);
            if(restoredText)
            {   staticItemList = getHistoryBrandInner(loginModel.getUserId()+"staticItemList",this);
                staticFreqList = getHistoryFreqBought(loginModel.getUserId()+"staticFreqList",this);
                cartItemNumber_tv.setText(staticItemList.size()+staticFreqList.size()+"");}
            else {
                sp.setParam("staticList",true);
                saveToSpBrandItem(staticItemList);
                saveToSpFreqModel(staticFreqList);
                cartItemNumber_tv.setText("0");
            }
         //   }

        viewPager = (LockableViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        back_img.setOnClickListener(this);
        cornerImage.setOnClickListener(this);
        viewPager.setSwipeable(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position)
            {
                viewPager.getAdapter().notifyDataSetChanged();
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(viewPager.getWindowToken(), 0);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setupTabIcons();

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setupTabIcons();
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setupTabIcons();
            }
        });

        setupTabIcons();
    }


    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Items_OrderFrag(),"ITEMS");
        adapter.addFragment(new Cart_OrderFrag(), "CART");
        viewPager.setAdapter(adapter); }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    public void onClick(View v) {

        if(back_img==v)
        {
            imageback=true;
            onBackPressed();
        }

        if (v==cornerImage)
        {
            PopupWindow popupwindow_obj = popupDisplay();
            popupwindow_obj.showAsDropDown(cornerImage, -40, 18);
        }}

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {

         /*   tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);*/
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


        @Override
        public int getItemPosition(@NonNull Object object) {

            return POSITION_NONE;
        }
    }

    private void setMenu()
    {
        PopupMenu popup = new PopupMenu(this, cornerImage);
        popup.getMenuInflater().inflate(R.menu.menu_toolbar, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popup.show();
    }

    //=======================================popUpDispley============================
    public PopupWindow popupDisplay()
    {
       TextView hold_tv,clearMenu_tv;
        LinearLayout recall_ll;

        final PopupWindow popupWindow = new PopupWindow(this);

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.menu_popup, null);
        hold_tv=view.findViewById(R.id.hold_tv);
        recall_ll=view.findViewById(R.id.recall_ll);
        clearMenu_tv=view.findViewById(R.id.clearMenu_tv);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

       if(order==2)
       {
           recall_ll.setVisibility(View.GONE);
           clearMenu_tv.setVisibility(View.GONE);

           hold_tv.setText("Clear List");
           hold_tv.setTextColor(Color.BLACK);

           hold_tv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   popupWindow.dismiss();
                  // onBackPressed();
                   int sizes=staticItemList.size();
                   if(staticItemList.size()!=0 || staticFreqList.size()!=0) {

                       clearAlert();

                   }
               }
           });

       }

        return popupWindow;
    }
    //=======================================popUpDispley============================

    //=====================================cartSizeMethod=============================
    public void setItemList(String cartNumber)
    {
        cartItemNumber_tv.setText(cartNumber);
    }
    //=====================================cartSizeMethod=============================


    //==========================backPressMethod=======================================
    @Override
    public void onBackPressed() {

        staticItemList = getHistoryBrandInner(loginModel.getUserId()+"staticItemList",this);
        int sizes=staticItemList.size()+staticFreqList.size();
        if(sizes!=0)
        {
                Toast.makeText(this, "Cart update successfully", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();

      /*  int tabPosition=tabLayout.getSelectedTabPosition();

        if(tabPosition==1 && !imageback && shopMore)
        {
            superBackpress();
        }

        else if(tabPosition==1 && !imageback )
        {
            if(staticItemList.size()==0)
            {
                superBackpress();
            }

           else {

                final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
                final String message = "Are you sure to clear cart?";
                builder.setCancelable(false);
                builder.setTitle("Clear Cart");
                builder.setMessage(message)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        staticItemList.clear();
                                        saveToSp(staticItemList);
                                        superBackpress();
                                        d.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        d.cancel();
                                    }
                                });
                builder.create().show();
            }
        }


        else if(sizes>0) {
            imageback=false;
            final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
            final String message = "Are you sure to clear cart?";
            builder.setCancelable(false);
            builder.setTitle("Clear Cart");
            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    staticItemList.clear();
                                    saveToSp(staticItemList);
                                    superBackpress();
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();
        }
        else {
            imageback=false;
            superBackpress();
        }*/
    }

    public void superBackpress()
    {
        super.onBackPressed();

    }
    //==========================backPressMethod=======================================



    //=========================getFromSp===============================================
    public ArrayList<Brand_SubBrand_Model.Item> getHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Brand_SubBrand_Model.Item>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<FrequentlyModel> getHistoryFreqList(String key,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<FrequentlyModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    //=========================getFromSp===============================================


    //==========================showClearAlert========================================

    private void clearAlert()
    {
        getWindow().getDecorView().clearFocus();
        final AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        final String message = "Are you sure to clear cart?";
        builder.setCancelable(false);
        builder.setTitle("Clear Cart");
        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                staticItemList.clear();
                                staticFreqList.clear();
                                saveToSpBrandItem(staticItemList);
                                saveToSpFreqModel(staticFreqList);
                               onBackPressed();
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }
    //==========================showClearAlert========================================


    //=========================saveToSp===============================================
    public void saveToSpBrandItem(ArrayList<Brand_SubBrand_Model.Item> allList)
    {
        Gson gson;
        SharedPreferences preferences;
        SharedPreferences.Editor prefsEditor;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticItemList", json);
        prefsEditor.apply();
    }

    public void saveToSpFreqModel(ArrayList<FrequentlyModel> allList)
    {
        Gson gson;
        SharedPreferences preferences;
        SharedPreferences.Editor prefsEditor;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticFreqList", json);
        prefsEditor.apply();
    }

}
