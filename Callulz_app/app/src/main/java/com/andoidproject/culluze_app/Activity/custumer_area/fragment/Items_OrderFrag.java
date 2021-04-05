package com.andoidproject.culluze_app.Activity.custumer_area.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.ItemDetail_orderSub;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.adapter.All_item_Adapter;
import com.andoidproject.culluze_app.Activity.custumer_area.adapter.FreqBought_Adapter;
import com.andoidproject.culluze_app.Activity.model.BrandList_Model;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;

public class Items_OrderFrag extends Fragment implements PopupMenu.OnMenuItemClickListener  {

    RecyclerView freqItem_recycler,allItems_recycler;
    LinearLayout freq_ll,allItem_ll;
    ImageView filter_img;
    public static  EditText search_et;
    View name_view;
    ApiInterface apiInterface;
    TextView nothinText;
    boolean oneTime=true;
    All_item_Adapter adapter;
    FreqBought_Adapter adapter2;
    ArrayList<Brand_SubBrand_Model.Item> allList= new ArrayList<>();
    ArrayList<FrequentlyModel>allFreqBought;
    public static boolean editInput=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_customers_frg, container, false);
        init(view);
    return view;
    }

    private void init(View view)
    {
        allItems_recycler=view.findViewById(R.id.allItems_recycler);
        freqItem_recycler=view.findViewById(R.id.freqItem_recycler);
        allItem_ll=view.findViewById(R.id.allItem_ll);
        freq_ll=view.findViewById(R.id.freq_ll);
        freq_ll=view.findViewById(R.id.freq_ll);
        filter_img=view.findViewById(R.id.filter_img);
        search_et=view.findViewById(R.id.search_et);
        name_view=view.findViewById(R.id.name_view);
        nothinText=view.findViewById(R.id.nothinText);
         allList =getHistoryList("allItems");

         search_et.setText("");
     /*   allItems_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        allItems_recycler.setItemAnimator(new DefaultItemAnimator());
        All_item_Adapter adapter= new All_item_Adapter(getActivity(),allBrand);*/

     /*   if(order==1)
        {
            nothinText.setVisibility(View.GONE);
            allItems_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            allItems_recycler.setItemAnimator(new DefaultItemAnimator());
            All_item_Adapter adapter= new All_item_Adapter(getActivity(),this);
            allItems_recycler.setAdapter(adapter);
            allItems_recycler.setNestedScrollingEnabled(false);
       }
*/

            callorderTWO_Recycler();
            focusChanged();

        apiInterface=APIClient.getClient().create(ApiInterface.class);
        filter_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMenu();
            }});

    }

    public void setMenu()
    {
        PopupMenu popup = new PopupMenu(getActivity(), filter_img);
            popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String id = (String) item.getTitle();

                if(id.equals("Item name") )
                {
                    search_et.setText("");
                    Collections.sort(allList, new Comparator<Brand_SubBrand_Model.Item>(){
                        public int compare(Brand_SubBrand_Model.Item s1, Brand_SubBrand_Model.Item s2) {
                            return s1.getBrand().compareToIgnoreCase(s2.getBrand());
                        }
                    });

                    if(allList.size()!=0) {
                        //   int id = getActivity().getIntent().getExtras().getInt("brandId");
                        //  ItemList_Api(id);
                        nothinText.setVisibility(View.GONE);
                        allItems_recycler.getRecycledViewPool().clear();
                        allItems_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        allItems_recycler.setItemAnimator(new DefaultItemAnimator());
                        adapter = new All_item_Adapter(getActivity(),true,allList,Items_OrderFrag.this);
                        allItems_recycler.setAdapter(adapter);
                        allItems_recycler.setNestedScrollingEnabled(false);
                    }

                    allFreqBought=getHistoryList2(loginModel.getUserId()+"freqBought");
                    Collections.sort(allFreqBought, new Comparator<FrequentlyModel>(){
                        public int compare(FrequentlyModel s1, FrequentlyModel s2) {
                            return s1.getBrand().compareToIgnoreCase(s2.getBrand());
                        }
                    });

                    freqItem_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    freqItem_recycler.setItemAnimator(new DefaultItemAnimator());
                    adapter2= new FreqBought_Adapter(getActivity(),allFreqBought,Items_OrderFrag.this);
                    freqItem_recycler.setAdapter(adapter2);
                    freqItem_recycler.setNestedScrollingEnabled(false);

                }

                else if(id.equals("All"))
                {
                    search_et.setText("");
                    callorderTWO_Recycler();

                }


                return false;
            }}); }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.search_item:
                // do your code
                return true;
            case R.id.upload_item:
                // do your code
                return true;
            case R.id.copy_item:
                // do your code
                return true;
            case R.id.print_item:
                // do your code
                return true;
            case R.id.share_item:
                // do your code
                return true;
            case R.id.bookmark_item:
                // do your code
                return true;*/
            default:
                return false;
        }
    }

    private void focusChanged() {
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


    public void lineAnimation(View view){
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
        //  name_et.startAnimation(anim);
        Animation anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_two);
        //  name_et.startAnimation(anim1);

        AnimationSet s = new AnimationSet(false);//false means don't share interpolators
        s.addAnimation(anim);
        s.addAnimation(anim1);
        view.startAnimation(s);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void Reverse_lineAnimation(final View view){
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_reverse);
        Animation anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rever_two);

        AnimationSet s = new AnimationSet(false);//false means don't share interpolators
        s.addAnimation(anim);
        s.addAnimation(anim1);
        view.startAnimation(s);


        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setElevation(-2f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setElevation(-2f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    public ArrayList<Brand_SubBrand_Model.Item> getHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Brand_SubBrand_Model.Item>>() {}.getType();
        return gson.fromJson(json, type);
    }


    /*private void ItemList_Api(int id)
    {

            retrofit2.Call<ArrayList<ItemList_Model>> call = apiInterface.itemList(id);
            call.enqueue(new Callback<ArrayList<ItemList_Model>>() {
                @Override
                public void onResponse(retrofit2.Call<ArrayList<ItemList_Model>> call, Response<ArrayList<ItemList_Model>> response) {
                    ArrayList<ItemList_Model> brand = response.body();

                    if (brand.size() == 0) {

                    } else {


                        Gson gson;
                        SharedPreferences preferences;

                        preferences = PreferenceManager
                                .getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor prefsEditor = preferences.edit();
                        gson = new Gson();
                        String json = gson.toJson(brand);
                        prefsEditor.putString("allItems", json);
                        prefsEditor.apply();


                        nothinText.setVisibility(View.GONE);
                        allItems_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        allItems_recycler.setItemAnimator(new DefaultItemAnimator());
                         adapter = new All_item_Adapter(getActivity(), brand);
                        allItems_recycler.setAdapter(adapter);
                        allItems_recycler.setNestedScrollingEnabled(false);


                    }


                }

                @Override
                public void onFailure(Call<ArrayList<ItemList_Model>> call, Throwable t) {
                    Log.e("itemListfail", t.getMessage());
                }
            });

    }*/

     @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5) {
            if (resultCode == getActivity().RESULT_OK) {
                String currentPosition = data.getStringExtra("currentPosition");
                String strquantity = data.getStringExtra("quantity");
                if(TextUtils.isEmpty(strquantity))
                {
                    strquantity="0";
                }
                String strfree = data.getStringExtra("free");
               // Toast.makeText(getActivity(), currentPosition, Toast.LENGTH_SHORT).show();
                int posInt = Integer.parseInt(currentPosition);
                Brand_SubBrand_Model.Item itemListModel = adapter.getItemListModel().get(posInt);
                itemListModel.setQuantity(Integer.parseInt(strquantity));
                itemListModel.setFreetv(strfree);
                adapter.notifyItemChanged(posInt);
            }

            else if (resultCode == 0) {
                System.out.println("RESULT CANCELLED");
            }
        }

         if(requestCode==6)
         {
             if (resultCode == getActivity().RESULT_OK) {

                 String currentPosition = data.getStringExtra("currentPosition");
                 String strquantity = data.getStringExtra("quantity");
                 if(TextUtils.isEmpty(strquantity))
                 {
                     strquantity="0";
                 }

                 // Toast.makeText(getActivity(), currentPosition, Toast.LENGTH_SHORT).show();
                 int posInt = Integer.parseInt(currentPosition);
                 Brand_SubBrand_Model.Item itemListModel = adapter.getItemListModel().get(posInt);
                 itemListModel.setQuantity(Integer.parseInt(strquantity));
                 adapter.notifyItemChanged(posInt);
             }

             else if (resultCode == 0) {
                 System.out.println("RESULT CANCELLED");
             }
         }

         if(requestCode==7)
         {
             if (resultCode == getActivity().RESULT_OK) {

                 String currentPosition = data.getStringExtra("currentPosition");
                 String strquantity = data.getStringExtra("quantity");
                 if(TextUtils.isEmpty(strquantity))
                 {
                     strquantity="0";
                 }
                 // Toast.makeText(getActivity(), currentPosition, Toast.LENGTH_SHORT).show();
                 int posInt = Integer.parseInt(currentPosition);
                FrequentlyModel freqModel = adapter2.getItemListModel().get(posInt);
                 freqModel.setQuantity(Integer.parseInt(strquantity));
                 adapter2.notifyItemChanged(posInt);
             }
             else if (resultCode == 0) {
                 System.out.println("RESULT CANCELLED");
             } }

         if(requestCode==8)
         {
             if (resultCode == getActivity().RESULT_OK) {

                 String currentPosition = data.getStringExtra("currentPosition");
                 String strquantity = data.getStringExtra("quantity");
                 if(TextUtils.isEmpty(strquantity))
                 {
                     strquantity="0";
                 }
                 int posInt = Integer.parseInt(currentPosition);
                 FrequentlyModel freqModel = adapter2.getItemListModel().get(posInt);
                 freqModel.setQuantity(Integer.parseInt(strquantity));
                 adapter2.notifyItemChanged(posInt);
             }

             else if (resultCode == 0) {
                 System.out.println("RESULT CANCELLED");
             }}}


    private void callorderTWO_Recycler()
    {
        if(allList.size()!=0) {
            allItems_recycler.getRecycledViewPool().clear();
            nothinText.setVisibility(View.GONE);
            allItems_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            allItems_recycler.setItemAnimator(new DefaultItemAnimator());

            adapter = new All_item_Adapter(getActivity(),this,true);
            allItems_recycler.setAdapter(adapter);
            allItems_recycler.setNestedScrollingEnabled(false);
        }

        else {
            nothinText.setVisibility(View.VISIBLE);
        }

        freq_ll.setVisibility(View.VISIBLE);
        allItem_ll.setVisibility(View.VISIBLE);

        freqItem_recycler.getRecycledViewPool().clear();
        freqItem_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        freqItem_recycler.setItemAnimator(new DefaultItemAnimator());
        adapter2= new FreqBought_Adapter(getActivity(),this);
        freqItem_recycler.setAdapter(adapter2);
        freqItem_recycler.setNestedScrollingEnabled(false);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }
            @Override
            public void afterTextChanged(final Editable editable) {

                        editInput=false;
                       //filter(editable.toString());
                        adapter.getFilter().filter(editable.toString());
                        adapter2.getFilter().filter(editable.toString());
            }
        });       }
        },1000);
    }

    public ArrayList<FrequentlyModel> getHistoryList2(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<FrequentlyModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

  /*  void filter(String text){
        ArrayList<Brand_SubBrand_Model.Item> temp = new ArrayList();
        for(Brand_SubBrand_Model.Item d: allList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getItmName().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter.updateList(temp);
    }*/

}
