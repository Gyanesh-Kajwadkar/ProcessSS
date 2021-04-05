package com.andoidproject.culluze_app.Activity.custumer_area.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.Activity.activity.PrintActivity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.CheckOut_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity;
import com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.andoidproject.culluze_app.Activity.network.APIClient;
import com.andoidproject.culluze_app.Activity.network.ApiInterface;
import com.andoidproject.culluze_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.order;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.shopMore;
import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Sales_Activity.staticFreqList;
import static com.andoidproject.culluze_app.Activity.custumer_area.fragment.Items_OrderFrag.search_et;

public class Cart_OrderFrag extends Fragment  implements View.OnClickListener {

    //one recycler view

    RecyclerView checkout_recycle,freqBought_recycle;
    Button checkout_btn,done_btn;
    CheckOut_Adapter adapter;
    CartFreqAdapter adapter2;
    TextView totalRate,totalQty,totalitem_tv;
    int totalAmount = 0;
    ProgressBar progressBar;
    public  ApiInterface apiInterface;
    ArrayList<SubmitSales_Model.SalesDet>list=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_route_planner_frg, container, false);
        init(v);
            String cartNumber;
            cartNumber = String.valueOf(staticItemList.size()+staticFreqList.size());
            Sales_Activity sales_activity = new Sales_Activity();
            sales_activity.setItemList(cartNumber);
        return v;
    }

    //=================================initView==================================
    private void init(View v) {

        checkout_recycle = v.findViewById(R.id.checkout_recycle);
        freqBought_recycle = v.findViewById(R.id.freqBought_recycle);
        checkout_btn = v.findViewById(R.id.checkout_btn);
        totalRate = v.findViewById(R.id.totalRate);
        totalQty = v.findViewById(R.id.totalQty);
        done_btn = v.findViewById(R.id.done_btn);
        totalitem_tv = v.findViewById(R.id.totalitem_tv);
        progressBar = v.findViewById(R.id.progressBar);

        checkout_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        checkout_recycle.setItemAnimator(new DefaultItemAnimator());
        adapter = new CheckOut_Adapter();
        checkout_recycle.setNestedScrollingEnabled(false);
        checkout_recycle.setAdapter(adapter);

        apiInterface= APIClient.getClient().create(ApiInterface.class);


        freqBought_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        freqBought_recycle.setItemAnimator(new DefaultItemAnimator());
        adapter2 = new CartFreqAdapter();
        freqBought_recycle.setNestedScrollingEnabled(false);
        freqBought_recycle.setAdapter(adapter2);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(checkout_recycle);

        ItemTouchHelper itemTouchHelper1 = new
                ItemTouchHelper(new SwipeToDeleteCallback2(adapter2));
        itemTouchHelper1.attachToRecyclerView(freqBought_recycle);

      //-------------------------checkStaticList-------------------------------------------
        if (staticItemList.size() != 0 || staticFreqList.size()!=0) {
         //   done_btn.setVisibility(View.VISIBLE);
            checkout_btn.setBackgroundColor(getResources().getColor(R.color.toolbarRed));
            checkout_btn.setTextColor(getResources().getColor(R.color.white));
            checkout_btn.setOnClickListener(this);
            done_btn.setOnClickListener(this);
            setTotalCalculation();
            saveToSp(staticItemList);
            saveToSp2(staticFreqList);
        }
        else {
            checkout_btn.setBackgroundColor(getResources().getColor(R.color.grey));
        }
        //-------------------------checkStaticList-------------------------------------------

        search_et.setText("");
    }


    //================================clickEvent=================================
    @Override
    public void onClick(View v) {

        if (v == checkout_btn)
        {
            if(order==2)
            {int pendingBill=custmer.getPendingBillCount();
            if(pendingBill==0)
            {
                Intent intent = new Intent(getActivity(), CheckOut_Activity.class);
                intent.putExtra("totalMoney",totalRate.getText().toString()+"");
                intent.putExtra("type","purchase");
                startActivity(intent);
            }
            else
                {


                     alertMessage("This order will not be processed until pending amount cleared");
              }}

            else {
                    Intent intent= new Intent(getActivity(),PrintActivity.class);
                    intent.putExtra("totalMoney",totalRate.getText().toString()+"");
                    startActivity(intent);

            }}

        if(v==done_btn)
        {
            shopMore=true;
            saveToSp(staticItemList);
            getActivity().onBackPressed();
        }
    }
    //================================clickEvent=================================


    //========================================AdapterFreqBought============================
    public class CartFreqAdapter extends RecyclerView.Adapter<CartFreqAdapter.ViewHolder> {

        float subTotal=0;
        int totalQ;
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkout_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

            final FrequentlyModel itemList = staticFreqList.get(i);
            viewHolder.free_tv.setText(itemList.getFreetv());

            if(!viewHolder.free_tv.getText().toString().isEmpty())
            {
                viewHolder.free_ll.setVisibility(View.VISIBLE);

            }

            viewHolder.itemName.setText(itemList.getItmName());

            float total = 0;

            viewHolder.quantity_et.setText(itemList.getQuantity()+"");

            viewHolder.mrp_tv.setText(itemList.getMrp()+"");


            String customerRate=custmer.getCustRate();

            if(customerRate.equals("ITM-RT"))
            {
                subTotal=itemList.getQuantity()* itemList.getRTSWT();
                viewHolder.price_tv.setText(itemList.getRTSWT()+"");
            }
            else if(customerRate.equals("ITM-WH"))
            {
                subTotal=itemList.getQuantity()* itemList.getWHSWT();
                viewHolder.price_tv.setText(itemList.getWHSWT()+"");
            }
            else if(customerRate.equals("ITM-DI"))
            {
                subTotal=itemList.getQuantity()* itemList.getDTSWT();
                viewHolder.price_tv.setText(itemList.getDTSWT()+"");
            }
            else if(customerRate.equals("ITM-EX"))
            {
                subTotal=itemList.getQuantity()* itemList.getEXSWT();
                viewHolder.price_tv.setText(itemList.getEXSWT()+"");
            }
            float gstCalculation=total*itemList.getTaxPerc()/100;
            final float totalAmount=total+gstCalculation;
            //viewHolders.rate_tv.setText(String.valueOf(totalAmount));
            viewHolder.rate_tv.setText("₹"+String.format("%.2f",subTotal));
            subTotal = totalAmount + subTotal;
            totalQ = itemList.getQuantity() + totalQ;


            viewHolder.quantity_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try{
                        int quantity=Integer.parseInt(s.toString());
                        if(quantity!=0)
                        { if(!viewHolder.quantity_et.getText().toString().isEmpty()) {
                            itemList.setQuantity(quantity);
                            float returnValue = setAmountCart2(itemList, quantity);
                            viewHolder.rate_tv.setText(String.valueOf(returnValue));
                            setTotalCalculation();
                        }}
                        else {
                            itemList.setQuantity(0);
                            float returnValue = setAmountCart2(itemList, 0);
                            viewHolder.rate_tv.setText(String.valueOf(returnValue));
                            setTotalCalculation();
                        }}
                    catch (Exception e)
                    {
                        itemList.setQuantity(0);
                        float returnValue = setAmountCart2(itemList, 0);
                        viewHolder.rate_tv.setText(String.valueOf(returnValue));
                        setTotalCalculation();
                    }
                }
            });
        }

        public void deleteItem(int position) {
            FrequentlyModel model=staticFreqList.get(position);
            model.setQuantity(0);
            model.setFreetv("");
            model.setBasic(-1);
            model.setCart(-1);

            staticFreqList.remove(position);
            saveToSp2(staticFreqList);
            adapter2.notifyItemRemoved(position);
           // adapter2.notifyDataSetChanged();
            setTotalCalculation();
            String cartNumber;
            cartNumber = String.valueOf(staticItemList.size()+staticFreqList.size());
            Sales_Activity sales_activity = new Sales_Activity();
            sales_activity.setItemList(cartNumber);
            // saveToSp(allList);
            if (staticItemList.size() ==0 && staticFreqList.size()==0) {
                checkout_btn.setBackgroundColor(getResources().getColor(R.color.grey));
                checkout_btn.setTextColor(getResources().getColor(R.color.dark_grey));
                checkout_btn.setEnabled(false);
                checkout_btn.setClickable(false);
                totalRate.setText("0.00");
                totalQty.setText("0 Qty");
                totalitem_tv.setText("0");
            }
        }
        @Override
        public int getItemCount() {
            return staticFreqList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemName,rate_tv,free_tv;
            EditText quantity_et;
            LinearLayout free_ll;
            TextView mrp_tv,price_tv;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemName = itemView.findViewById(R.id.itemName);
                rate_tv = itemView.findViewById(R.id.rate_tv);
                quantity_et = itemView.findViewById(R.id.quantity_et);
                free_tv = itemView.findViewById(R.id.free_tv);
                free_ll = itemView.findViewById(R.id.free_ll);

                mrp_tv = itemView.findViewById(R.id.mrp_tv);
                price_tv = itemView.findViewById(R.id.price_tv);
            }
        }
    }

    //==================================recyclerAdapter=============================
    private class CheckOut_Adapter extends RecyclerView.Adapter<CheckOut_Adapter.ViewHolders> {
        float subTotal=0;
        int totalQ;
        @NonNull
        @Override
        public CheckOut_Adapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkout_view, viewGroup, false);
            return new ViewHolders(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CheckOut_Adapter.ViewHolders viewHolders, int i) {

            final Brand_SubBrand_Model.Item itemList = staticItemList.get(i);
            viewHolders.free_tv.setText(itemList.getFreetv());

            if(!viewHolders.free_tv.getText().toString().isEmpty())
            {
                viewHolders.free_ll.setVisibility(View.VISIBLE);

            }

            viewHolders.itemName.setText(itemList.getItmName());

            float total = 0;

            viewHolders.quantity_et.setText(itemList.getQuantity()+"");

            viewHolders.mrp_tv.setText(itemList.getMrp()+"");


            String customerRate=custmer.getCustRate();

          /*  if(customerRate.equals("ITM-RT"))
            {
                viewHolders.price_tv.setText(itemList.getRTSWT()+"");
                total= itemList.getRTSWT()+itemList.getMrp()*Float.parseFloat(itemList.getQuantity());
            }
            else if(customerRate.equals("ITM-WH"))
            {
                viewHolders.price_tv.setText(itemList.getWHSWT()+"");
                total= itemList.getWHSWT()+itemList.getMrp()*Float.parseFloat(itemList.getQuantity());
            }
            else if(customerRate.equals("ITM-DI"))
            {
                viewHolders.price_tv.setText(itemList.getDTSWT()+"");
                total= itemList.getDTSWT()+itemList.getMrp()*Float.parseFloat(itemList.getQuantity());
            }
            else if(customerRate.equals("ITM-EX"))
            {
                viewHolders.price_tv.setText(itemList.getEXSWT()+"");
                total= itemList.getEXSWT()+itemList.getMrp()*Float.parseFloat(itemList.getQuantity());
            }*/


            if(customerRate.equals("ITM-RT"))
            {
                subTotal=itemList.getQuantity()* itemList.getRTSWT();
                viewHolders.price_tv.setText(itemList.getRTSWT()+"");
            }
            else if(customerRate.equals("ITM-WH"))
            {
                subTotal=itemList.getQuantity()* itemList.getWHSWT();
                viewHolders.price_tv.setText(itemList.getWHSWT()+"");
            }
            else if(customerRate.equals("ITM-DI"))
            {
                subTotal=itemList.getQuantity()* itemList.getDTSWT();
                viewHolders.price_tv.setText(itemList.getDTSWT()+"");
            }
            else if(customerRate.equals("ITM-EX"))
            {
                subTotal=itemList.getQuantity()* itemList.getEXSWT();
                viewHolders.price_tv.setText(itemList.getEXSWT()+"");
            }



      /*      float gstCalculation=total*itemList.getTaxPerc()/100;
            final float totalAmount=total+gstCalculation;*/
            //viewHolders.rate_tv.setText(String.valueOf(totalAmount));
            viewHolders.rate_tv.setText("₹"+String.format("%.2f",subTotal));
            subTotal = totalAmount + subTotal;
            totalQ = itemList.getQuantity() + totalQ;


            viewHolders.quantity_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                   try{
                    int  quantity=Integer.parseInt(s.toString());
                    if(quantity!=0)
                    {if(!viewHolders.quantity_et.getText().toString().isEmpty()) {
                        itemList.setQuantity(quantity);
                        float returnValue = setAmountCart(itemList, quantity);
                        viewHolders.rate_tv.setText(String.valueOf(returnValue));
                        setTotalCalculation();
                    }}
                    else {
                        itemList.setQuantity(0);
                        float returnValue = setAmountCart(itemList, 0);
                        viewHolders.rate_tv.setText(String.valueOf(returnValue));
                        setTotalCalculation();
                    }}
                   catch (Exception e)
                   {
                       itemList.setQuantity(0);
                       float returnValue = setAmountCart(itemList, 0);
                       viewHolders.rate_tv.setText(String.valueOf(returnValue));
                       setTotalCalculation();
                   }
                }
            }); }

        @Override
        public int getItemCount() {
            return staticItemList.size();
        }

        public void deleteItem(int position) {
            Brand_SubBrand_Model.Item model=staticItemList.get(position);
            model.setQuantity(0);
            model.setFreetv("");
            model.setBasic(-1);
            model.setCart(-1);

            staticItemList.remove(position);
            saveToSp(staticItemList);
            adapter.notifyItemRemoved(position);
           // adapter.notifyDataSetChanged();
            setTotalCalculation();
            String cartNumber;
            cartNumber = String.valueOf(staticItemList.size()+staticFreqList.size());
            Sales_Activity sales_activity = new Sales_Activity();
            sales_activity.setItemList(cartNumber);
            // saveToSp(allList);
            if (staticItemList.size() == 0 && staticFreqList.size()==0) {
                checkout_btn.setBackgroundColor(getResources().getColor(R.color.grey));
                checkout_btn.setTextColor(getResources().getColor(R.color.dark_grey));
                checkout_btn.setEnabled(false);
                checkout_btn.setClickable(false);
                totalRate.setText("0.00");
                totalQty.setText("0 Qty");
                totalitem_tv.setText("0");
            }
        }

        public class ViewHolders extends RecyclerView.ViewHolder {

            TextView itemName,rate_tv,free_tv;
            EditText quantity_et;
            LinearLayout free_ll;
            TextView mrp_tv,price_tv;

            public ViewHolders(@NonNull View itemView) {
                super(itemView);
                itemName = itemView.findViewById(R.id.itemName);
                rate_tv = itemView.findViewById(R.id.rate_tv);
                quantity_et = itemView.findViewById(R.id.quantity_et);
                free_tv = itemView.findViewById(R.id.free_tv);
                free_ll = itemView.findViewById(R.id.free_ll);

                mrp_tv = itemView.findViewById(R.id.mrp_tv);
                price_tv = itemView.findViewById(R.id.price_tv);

            }
        }
    }
    //==================================recyclerAdapter=============================



    //=================================swipeDeleteClass=============================
    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private CheckOut_Adapter mAdapter;
        private Drawable icon;
        private final ColorDrawable background;

        public SwipeToDeleteCallback(CheckOut_Adapter adapter) {
            super(0, ItemTouchHelper.
                    LEFT );
            mAdapter = adapter;
            icon = ContextCompat.
                    getDrawable(getActivity(),
                            R.drawable.
                                    delete);
            background = new ColorDrawable(Color.
                    RED);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX,
                    dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;

            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + icon.getIntrinsicHeight();

            if (dX > 0) { // Swiping to the right
                int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                int iconRight = itemView.getLeft() + iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());
            } else if (dX < 0) { // Swiping to the left
                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }

            background.draw(c);
            icon.draw(c);

        }

    }

    public class SwipeToDeleteCallback2 extends ItemTouchHelper.SimpleCallback {
        private CartFreqAdapter mAdapter;
        private Drawable icon;
        private final ColorDrawable background;

        public SwipeToDeleteCallback2(CartFreqAdapter adapter) {
            super(0, ItemTouchHelper.
                    LEFT );
            mAdapter = adapter;
            icon = ContextCompat.
                    getDrawable(getActivity(),
                            R.drawable.
                                    delete);
            background = new ColorDrawable(Color.
                    RED);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX,
                    dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20;

            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + icon.getIntrinsicHeight();

            if (dX > 0) { // Swiping to the right
                int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                int iconRight = itemView.getLeft() + iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                        itemView.getBottom());
            } else if (dX < 0) { // Swiping to the left
                int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }

            background.draw(c);
            icon.draw(c);

        }

    }

    //=================================swipeDeleteClass=============================


    //=====================================saveToSp=================================
    private void saveToSp(ArrayList<Brand_SubBrand_Model.Item> allList) {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticItemList", json);
        prefsEditor.apply();
    }

    private void saveToSp2(ArrayList<FrequentlyModel> allList)
    {
        Gson gson;
        SharedPreferences preferences;
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = preferences.edit();
        gson = new Gson();
        String json = gson.toJson(allList);
        prefsEditor.putString(loginModel.getUserId()+"staticFreqList", json);
        prefsEditor.apply();
    }

    //=====================================saveToSp=================================


    //======================================setAmount==================================
    private float setAmountCart(Brand_SubBrand_Model.Item itemList,int quantity) {
        float total = 0;
        String customerRate=custmer.getCustRate();

        if(customerRate.equals("ITM-RT"))
        {
            total=itemList.getQuantity()* itemList.getRTSWT();

        }
        else if(customerRate.equals("ITM-WH"))
        {
            total=itemList.getQuantity()* itemList.getWHSWT();
        }
        else if(customerRate.equals("ITM-DI"))
        {
            total=itemList.getQuantity()* itemList.getDTSWT();

        }
        else if(customerRate.equals("ITM-EX"))
        {
            total=itemList.getQuantity()* itemList.getEXSWT();
        }
        float gstCalculation=total*itemList.getTaxPerc()/100;
        float totalAmount=total+gstCalculation;
        // rate_tv.setText(String.valueOf(totalAmount));

        return  total;

    }

    private float setAmountCart2(FrequentlyModel itemList,int quantity) {
        float total = 0;
        String customerRate=custmer.getCustRate();

        if(customerRate.equals("ITM-RT"))
        {
            total=itemList.getQuantity()* itemList.getRTSWT();

        }
        else if(customerRate.equals("ITM-WH"))
        {
            total=itemList.getQuantity()* itemList.getWHSWT();
        }
        else if(customerRate.equals("ITM-DI"))
        {
            total=itemList.getQuantity()* itemList.getDTSWT();

        }
        else if(customerRate.equals("ITM-EX"))
        {
            total=itemList.getQuantity()* itemList.getEXSWT();
        }
        float gstCalculation=total*itemList.getTaxPerc()/100;
        float totalAmount=total+gstCalculation;
        // rate_tv.setText(String.valueOf(totalAmount));

        return  total;

    }

    //======================================setAmount==================================


    //======================================calculationMethod============================
    private void setTotalCalculation() {
        float subTotal=0,totalHold=0,printTotal=0;
        int totalQ=0;
        int totalitemcalculation=0;
        //  int  totalQ = 0;
        for (int i = 0; i < staticItemList.size(); i++) {
            Brand_SubBrand_Model.Item model = staticItemList.get(i);

            String customerRate=custmer.getCustRate();
            float total = 0;
            if(customerRate.equals("ITM-RT"))
            {
                subTotal=model.getQuantity()* model.getRTSWT();
                // total= model.getRTSWT()+model.getMrp()*Float.parseFloat(model.getQuantity());

            }
            else if(customerRate.equals("ITM-WH"))
            {
                subTotal=model.getQuantity()* model.getWHSWT();

                //total= model.getWHSWT()+model.getMrp()*Float.parseFloat(model.getQuantity());
            }
            else if(customerRate.equals("ITM-DI"))
            {
                subTotal=model.getQuantity()* model.getDTSWT();
                //total= model.getDTSWT()+model.getMrp()*Float.parseFloat(model.getQuantity());
            }
            else if(customerRate.equals("ITM-EX"))
            {
                subTotal=model.getQuantity()* model.getEXSWT();
                // total= model.getEXSWT()+model.getMrp()*Float.parseFloat(model.getQuantity());
            }
            float gstCalculation=total*model.getTaxPerc()/100;
            float totalAmount=total+gstCalculation;

                int convertitem= model.getQuantity();
                totalitemcalculation=convertitem+totalitemcalculation;



             /*   totalitem_tv.setText(totalitemcalculation+"");*/

            totalHold = totalHold + subTotal;
            totalQ = model.getQuantity() + totalQ;

        }
        for (int i = 0; i < staticFreqList.size(); i++) {
            FrequentlyModel model = staticFreqList.get(i);

            String customerRate=custmer.getCustRate();
            float total = 0;
            if(customerRate.equals("ITM-RT"))
            {
                subTotal=model.getQuantity()* model.getRTSWT();

            }
            else if(customerRate.equals("ITM-WH"))
            {
                subTotal=model.getQuantity()* model.getWHSWT();
            }
            else if(customerRate.equals("ITM-DI"))
            {
                subTotal=model.getQuantity()* model.getDTSWT();

            }
            else if(customerRate.equals("ITM-EX"))
            {
                subTotal=model.getQuantity()* model.getEXSWT();
            }


            float gstCalculation=total*model.getTaxPerc()/100;
            float totalAmount=total+gstCalculation;

            int convertitem= model.getQuantity();
            totalitemcalculation=convertitem+totalitemcalculation;


            totalHold = totalHold + subTotal;
            totalQ = model.getQuantity() + totalQ;
           // totalRate.setText(String.format("%.2f",subTotal));

            // totalRate.setText(String.valueOf(subTotal));
            totalQty.setText(String.valueOf(totalQ) + " Qty");

        }
        totalitem_tv.setText(staticItemList.size()+staticFreqList.size()+"");
        totalRate.setText(String.format("%.2f",totalHold));
        totalQty.setText(String.valueOf(totalQ) + " Qty");
    }


    //======================================calculationMethod============================


    //==========================================AlertMessage==============================
    public void alertMessage( String message) {
        final Dialog dialog = new Dialog(getActivity());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        lp.gravity = Gravity.CENTER;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView message_txt = dialog.findViewById(R.id.message_txt);
        Button ok_btn_dialog = dialog.findViewById(R.id.ok_btn_dialog);
        message_txt.setText(message);
        ok_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckOut_Activity.class);
                intent.putExtra("totalMoney",totalRate.getText().toString()+"");
                intent.putExtra("type","pending");
                startActivity(intent);

                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    //==========================================AlertMessage==============================

    //========================================AdapterFreqBought============================

    //================================enableProgress===============================
    public void enableProgress(ProgressBar progress) {
        progress.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //===============================disableProgress===============================
    public void disableProgress(ProgressBar progress) {
        progress.setVisibility(View.GONE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //===============================saveOrderApi===================================
   /* private void callSubmitOrderApi()
    {

        SubmitSales_Model saveSales= new SubmitSales_Model();
        saveSales.setCustmrId(custmer.getId());
        saveSales.setUsrId(loginModel.getUserId());
        saveSales.setTotSaleAmt(Float.parseFloat(totalRate.getText().toString()));
        saveSales.setGrandTotAmt(1);
        saveSales.setOtherCharge(1);
        saveSales.setRoundingAmt(1);
        saveSales.setTranOverheads(1);

        for(int i=0;i<staticItemList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            Brand_SubBrand_Model.Item model=staticItemList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list.add(cartList);
        }

        for(int i=0;i<staticFreqList.size();i++)
        {
            SubmitSales_Model.SalesDet cartList = new SubmitSales_Model.SalesDet();
            FrequentlyModel model=staticFreqList.get(i);
            cartList.setUnitPriceWiTax(String.valueOf(model.getTaxPerc()));
            cartList.setItmId(model.getId());
            cartList.setQty(model.getQuantity());
            list.add(cartList);
        }

        saveSales.setSalesDet(list);

        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<SubmitSales_Model> call=apiInterface.submitsales(saveSales);
        call.enqueue(new Callback<SubmitSales_Model>()
        {
            @Override
            public void onResponse(Call<SubmitSales_Model> call, Response<SubmitSales_Model> response) {

                Toast.makeText(getActivity(), "Order save Successfully", Toast.LENGTH_SHORT).show();
                staticItemList.clear();
                saveToSp(staticItemList);
                staticFreqList.clear();
                saveToSp2(staticFreqList);
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        disableProgress(progressBar);
                     getActivity().onBackPressed();
                    }
                },2000);
            }

            @Override
            public void onFailure(Call<SubmitSales_Model> call, Throwable t) {
                disableProgress(progressBar);
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();

            }
        });}*/



}