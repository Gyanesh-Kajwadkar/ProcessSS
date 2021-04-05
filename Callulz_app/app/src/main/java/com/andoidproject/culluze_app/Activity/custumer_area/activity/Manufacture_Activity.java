package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.Base_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.adapter.All_item_Adapter;
import com.andoidproject.culluze_app.Activity.custumer_area.adapter.BrandSubList_Adapter;
import com.andoidproject.culluze_app.Activity.custumer_area.adapter.CustomerAdapter;
import com.andoidproject.culluze_app.Activity.model.BrandList_Model;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.Get_CustomerModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.Activity.utils.SharedPreferanceUtils;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.staticFreqList;

public class Manufacture_Activity extends Base_Activity implements View.OnClickListener {


    //This Activity has its own recycler view code in it.

    ImageView back_img;
    TextView toolbarText;
    RecyclerView manufacture_Recycler;
    View name_view;
    ImageView filter_img;
    EditText search_et;
    ArrayList<Brand_SubBrand_Model> brandList=new ArrayList<>();
    ProgressBar progress_circular;
    private ArrayList<Brand_SubBrand_Model> modellist = new ArrayList<>();
    ManufactureAdapter adapter;
    boolean hasItems=false,hasCart=false;
    SharedPreferanceUtils sp;

    public static ArrayList<Brand_SubBrand_Model.Item>staticItemList=new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.tag_white,
            R.drawable.star,
            R.drawable.cart_white
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);

     init();
    }

    private void init()
    {
        manufacture_Recycler=findViewById(R.id.manufacture_Recycler);
        name_view=findViewById(R.id.name_view);
        search_et=findViewById(R.id.search_et);
        filter_img=findViewById(R.id.filter_img);
        back_img=findViewById(R.id.back_img);
        toolbarText=findViewById(R.id.toolbarText);
        progress_circular=findViewById(R.id.progress_circular);
        toolbarText.setText("Manufacturer");
        sp = new SharedPreferanceUtils(this);

        callRecycler(hasItems,hasCart);

        back_img.setOnClickListener(this);
        filter_img.setOnClickListener(this);

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

        if(back_img==v)
        {
            onBackPressed();
        }

        if(filter_img==v)
        {
            setMenuCustomer(filter_img);
        }

    }


    //===================================recyclerAdapter======================================
    private class ManufactureAdapter extends RecyclerView.Adapter<ManufactureAdapter.ViewHolders> implements Filterable
        {

            boolean hasItems,hasCartt;

           public ManufactureAdapter()
           {

           }

            public ManufactureAdapter(boolean hasItems,boolean hasCart ) {
                this.hasItems=hasItems;
                this.hasCartt=hasCart;
            }

            @NonNull
        @Override
        public ManufactureAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
           View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manufacture_view,viewGroup,false);
            return new ViewHolders(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ManufactureAdapter.ViewHolders viewHolders, int i) {

            final Brand_SubBrand_Model brand=modellist.get(i);
            final ArrayList<Brand_SubBrand_Model.Item>items= (ArrayList<Brand_SubBrand_Model.Item>) brand.getItem();

            viewHolders.textClick.setText(brand.getBrandName());

            viewHolders.textClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //enableProgress(progress_circular);
                    if(items.size()!=0)
                    {
                        saveToSp(items);
                        Intent intent = new Intent(Manufacture_Activity.this, Sales_Activity.class);
                        startActivity(intent);
                    }
                    else
                        {
                            alertMessage("Out of Stock");
                        }

                  //  ItemList_Api(brand.getId(),brand.getBrandName());
                   // order=2;
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(Manufacture_Activity.this, LinearLayoutManager.VERTICAL, false);
            viewHolders.recyclerView2.setLayoutManager(layoutManager);
            viewHolders.recyclerView2.setHasFixedSize(true);


            if(items.size()!=0 && hasItems && !hasCartt) {
                BrandSubList_Adapter adapter = new BrandSubList_Adapter(items);
                viewHolders.recyclerView2.setAdapter(adapter);
            }

            if(items.size()!=0 && hasItems && hasCartt) {

                ArrayList<Brand_SubBrand_Model.Item> staticList=new ArrayList<>();
                for (Brand_SubBrand_Model.Item item : items) {

                    for (Brand_SubBrand_Model.Item model : staticItemList) {

                        int modelId=model.getId();
                        int brandId=  item.getId();
                        if(modelId==brandId)
                        {
                            staticList.add(model);
                        }
                    }}
            //    LinearLayoutManager layoutManager = new LinearLayoutManager(Manufacture_Activity.this, LinearLayoutManager.VERTICAL, false);
                BrandSubList_Adapter adapter = new BrandSubList_Adapter(items,hasCartt,staticList);
                viewHolders.recyclerView2.setAdapter(adapter);
              //  viewHolders.recyclerView2.setHasFixedSize(true);
              //  viewHolders.recyclerView2.setLayoutManager(layoutManager);
            } }


        @Override
        public int getItemCount() {
            return modellist.size();
        }

        public class ViewHolders extends RecyclerView.ViewHolder {
            TextView textClick;
            RecyclerView recyclerView2;
            public ViewHolders(@NonNull View itemView) {
                super(itemView);
                textClick=itemView.findViewById(R.id.textClick);
                recyclerView2=itemView.findViewById(R.id.recyclerView2);
            }
        }

        @Override
        public Filter getFilter(){
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    modellist.clear();
                    final FilterResults results = new FilterResults();
                    if(constraint.length() == 0){
                        modellist.addAll(brandList);
                    }
                    else{

                        final String filterPattern =constraint.toString().toLowerCase().trim();
                        for(Brand_SubBrand_Model listcountry : brandList){
                            String nameIs=listcountry.getBrandName();
                            // String mobile=listcountry.getMobile();

                            if(nameIs.toLowerCase().startsWith(filterPattern)){

                                modellist.add(listcountry);
                            }
                        }
                    }
                    results.values = modellist;
                    results.count = modellist.size();
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();
                }
            };
        }


    }
    //===================================recyclerAdapter======================================


    //====================================getSharePreferance===================================
    public ArrayList<Brand_SubBrand_Model> getHistoryList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Brand_SubBrand_Model>>() {}.getType();
        return gson.fromJson(json, type);
    }
    //====================================getSharePreferance===================================


    public void setMenuCustomer(ImageView filter_img) {
        PopupMenu popup = new PopupMenu(this, filter_img);
        popup.getMenuInflater().inflate(R.menu.brand_manu, popup.getMenu());
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
                if(id.equals("Brand name") )
                {
                    hasItems=false;
                    hasCart=false;
                    search_et.setText("");
                    modellist = getHistoryList(loginModel.getUserId()+"brandItem");
                   // modellist.addAll(brandList);

                    Collections.sort(modellist, new Comparator<Brand_SubBrand_Model>(){
                        public int compare(Brand_SubBrand_Model s1, Brand_SubBrand_Model s2) {
                            return s1.getBrandName().compareToIgnoreCase(s2.getBrandName());
                        }
                    });

                    adapter= new ManufactureAdapter();
                  //  manufacture_Recycler.addItemDecoration(new DividerItemDecoration(Manufacture_Activity.this, LinearLayout.VERTICAL));
                    manufacture_Recycler.setAdapter(adapter);
                    manufacture_Recycler.setLayoutFrozen(true);

                }

                else if(id.equals("All"))
                {
                    hasItems=false;
                    hasCart=false;
                    search_et.setText("");
                    callRecycler(hasItems,hasCart);
                }


                else if(id.equals("All Items"))
                {
                    hasCart=false;
                    hasItems=true;
                    search_et.setText("");
                    callRecycler(hasItems,hasCart);
                }

                else if(id.equals("Cart Items"))
                {
                    hasCart=true;
                    hasItems=true;
                    search_et.setText("");
                    try{
                        staticItemList = getHistoryBrandInner(loginModel.getUserId()+"staticItemList",Manufacture_Activity.this);

                        if(staticItemList.size()!=0)
                        {
                            brandList.clear();
                            modellist.clear();
                            callRecycler(hasItems,hasCart);
                           /* brandList=getHistoryList(loginModel.getUserId()+"brandItem");
                            modellist.addAll(brandList);
                            adapter= new ManufactureAdapter(hasItems,hasCart);
                            manufacture_Recycler.setAdapter(adapter);
                            manufacture_Recycler.setLayoutManager(new LinearLayoutManager(Manufacture_Activity.this));
                            manufacture_Recycler.setItemAnimator(new DefaultItemAnimator());
                            manufacture_Recycler.setItemAnimator(new DefaultItemAnimator());*/
                            }
                        else
                            {
                            callToast(Manufacture_Activity.this,"Cart is Empty");
                             }
                    }
                    catch (Exception e)
                    {
                        Log.e("exception",e.getMessage());
                        callToast(Manufacture_Activity.this,"Cart is Empty");
                        staticItemList=new ArrayList<>();
                    }
                }
                return false;
            }
        });

    }

    //===================================callRecycler===========================================
    private void callRecycler(boolean hasItems,boolean hasCart)
    {
        brandList=getHistoryList(loginModel.getUserId()+"brandItem");
        modellist.addAll(brandList);
        adapter= new ManufactureAdapter(hasItems,hasCart);
        manufacture_Recycler.setAdapter(adapter);
        manufacture_Recycler.setLayoutManager(new LinearLayoutManager(this));
        manufacture_Recycler.setItemAnimator(new DefaultItemAnimator());
        manufacture_Recycler.setItemAnimator(new DefaultItemAnimator());
        manufacture_Recycler.setLayoutFrozen(true);

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
                manufacture_Recycler.setLayoutFrozen(false);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    //=========================saveToSp===============================================
    public void saveToSp(ArrayList<Brand_SubBrand_Model.Item> allList)
    {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString("allItems", json);
        prefsEditor.apply();
    }
    //=========================saveToSp===============================================

    private void ItemList_Api(int id, final String brandName)
    {
        ApiInterface apiInterface;
        apiInterface= APIClient.getClient().create(ApiInterface.class);

        retrofit2.Call<ArrayList<ItemList_Model>> call = apiInterface.itemList(id);
        call.enqueue(new Callback<ArrayList<ItemList_Model>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<ItemList_Model>> call, Response<ArrayList<ItemList_Model>> response) {
                ArrayList<ItemList_Model> brand = response.body();

                if (brand.size() == 0)
                {

                    disableProgress(progress_circular);
                }
                else
                {
                    Gson gson;
                    SharedPreferences preferences;
                    preferences = PreferenceManager
                            .getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor prefsEditor = preferences.edit();
                    gson = new Gson();
                    String json = gson.toJson(brand);
                    prefsEditor.putString("allItems", json);
                    prefsEditor.apply();

                    Intent intent= new Intent(Manufacture_Activity.this, Sales_Activity.class);
                    intent.putExtra("brandId",1);

                    startActivity(intent);
                    disableProgress(progress_circular);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemList_Model>> call, Throwable t) {
                disableProgress(progress_circular);
                Log.e("itemListfail", t.getMessage());

            }
        });





    }
}


