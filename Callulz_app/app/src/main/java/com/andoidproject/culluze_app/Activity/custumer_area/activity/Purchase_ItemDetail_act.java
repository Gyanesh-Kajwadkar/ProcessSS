package com.andoidproject.culluze_app.Activity.custumer_area.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.Activity.model.FrequentlyModel;
import com.andoidproject.culluze_app.Activity.model.ItemList_Model;
import com.andoidproject.culluze_app.R;

import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Detail_CustomSub_act.custmer;

public class Purchase_ItemDetail_act extends AppCompatActivity implements View.OnClickListener {


    Button purchaseDone_btn;
    RecyclerView purchaseRecycler;
    ImageView back_img;
    TextView toolbarText;
    private String currentPosition;
    Intent intent;
    int value=0;

    TextView title_tv;
    EditText quantity_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase__item_detail);

        init();
    }

    private  void init()
    {

        purchaseRecycler=findViewById(R.id.purchaseRecycler);
        purchaseDone_btn=findViewById(R.id.purchaseDone_btn);
        back_img=findViewById(R.id.back_img);
        back_img.setImageResource(R.drawable.multiply);
        toolbarText=findViewById(R.id.toolbarText);
        toolbarText.setText("XYZ Product");

        purchaseRecycler.setLayoutManager(new LinearLayoutManager(this));
        purchaseRecycler.setItemAnimator(new DefaultItemAnimator());


        back_img.setOnClickListener(this);
        purchaseDone_btn.setOnClickListener(this);


        intent = getIntent();
        if (intent.hasExtra("currentPosition") && intent.hasExtra("list")) {
            currentPosition = getIntent().getStringExtra("currentPosition");
            Brand_SubBrand_Model.Item mode= (Brand_SubBrand_Model.Item) getIntent().getSerializableExtra("list");
            Purchase_itemAdapter adapter= new Purchase_itemAdapter(mode);
            purchaseRecycler.setAdapter(adapter);
            value=1;

        }
        if (intent.hasExtra("currentPosition2") && intent.hasExtra("list2")) {
            currentPosition = getIntent().getStringExtra("currentPosition2");
            FrequentlyModel mode= (FrequentlyModel) getIntent().getSerializableExtra("list2");
            Purchase_itemAdapter adapter= new Purchase_itemAdapter(mode);
            purchaseRecycler.setAdapter(adapter);
            value=2;

        }



    }

    @Override
    public void onClick(View v) {

        if(v==purchaseDone_btn)
        {

            if (intent.hasExtra("currentPosition") && intent.hasExtra("list")) {
            Intent intent = new Intent();
            intent.putExtra("quantity", quantity_tv.getText().toString());
            intent.putExtra("currentPosition", currentPosition);
            setResult(RESULT_OK,intent);
            finish();
            return;
        }

            if (intent.hasExtra("currentPosition2") && intent.hasExtra("list2")) {
                Intent intent = new Intent();
                intent.putExtra("quantity", quantity_tv.getText().toString());
                intent.putExtra("currentPosition", currentPosition);
                setResult(RESULT_OK,intent);
                finish();
                return;
            }
        onBackPressed();
        }


        if(v==back_img)
        {
            onBackPressed();
        }
    }


    public class Purchase_itemAdapter extends RecyclerView.Adapter<Purchase_itemAdapter.ViewHolders> {

        Brand_SubBrand_Model.Item model;
        FrequentlyModel mode;

        public Purchase_itemAdapter(Brand_SubBrand_Model.Item model)
        {
            this.model=model;
        }

        public Purchase_itemAdapter(FrequentlyModel mode) {

            this.mode=mode;
        }


        @NonNull
        @Override
        public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchase_view,viewGroup,false);
            return new ViewHolders(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {

            if(value==1)
            {
                if(model.getQuantity()!=0)
                {quantity_tv.setText(model.getQuantity()+"");
                quantity_tv.setSelection(quantity_tv.getText().length());}
                else {
                    quantity_tv.setText("");
                }

                title_tv.setText(model.getItmName());
                toolbarText.setText(model.getItmName());
                viewHolders.stock_tv.setText("STOCK: "+model.getAvilStk());
                viewHolders.mrp_tv.setText(model.getMrp()+"");

                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    viewHolders.rate_tv.setText(model.getRTSWT()+"");
                }
                else if(customerRate.equals("ITM-WH"))
                {
                    viewHolders.rate_tv.setText(model.getWHSWT()+"");
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    viewHolders.rate_tv.setText(model.getDTSWT()+"");
                }
                else if(customerRate.equals("ITM-EX"))
                {
                    viewHolders.rate_tv.setText(model.getEXSWT()+"");
                }

                if(model.getAvilStk()<=0)
                {
                    viewHolders.stock_tv.setText("STOCK: Out of Stock");
                    viewHolders.stock_tv.setTextColor(Color.RED);
                    quantity_tv.setFocusable(false);
                    quantity_tv.setClickable(false);
                    quantity_tv.setCursorVisible(false);
                }

            }

            if(value==2)
            {
                if(mode.getQuantity()!=0)
                { quantity_tv.setText(mode.getQuantity()+"");
                quantity_tv.setSelection(quantity_tv.getText().length());}
                else {
                    quantity_tv.setText("");
                }

                title_tv.setText(mode.getItmName());
                toolbarText.setText(mode.getItmName());
                viewHolders.stock_tv.setText("STOCK: "+mode.getAvilStk());
                viewHolders.mrp_tv.setText(mode.getMrp()+"");

                String customerRate=custmer.getCustRate();

                if(customerRate.equals("ITM-RT"))
                {
                    viewHolders.rate_tv.setText(mode.getRTSWT()+"");
                }
                else if(customerRate.equals("ITM-WH"))
                {
                    viewHolders.rate_tv.setText(mode.getWHSWT()+"");
                }
                else if(customerRate.equals("ITM-DI"))
                {
                    viewHolders.rate_tv.setText(model.getDTSWT()+"");
                }
                else if(customerRate.equals("ITM-EX"))
                {
                    viewHolders.rate_tv.setText(mode.getEXSWT()+"");
                }

                if(mode.getAvilStk()<=0)
                {
                    viewHolders.stock_tv.setText("STOCK: Out of Stock");
                    viewHolders.stock_tv.setTextColor(Color.RED);
                    quantity_tv.setFocusable(false);
                    quantity_tv.setClickable(false);
                    quantity_tv.setCursorVisible(false);
                }

            }


        }

        @Override
        public int getItemCount() {
            return 1;
        }

        public class ViewHolders extends RecyclerView.ViewHolder {

            TextView stock_tv,rate_tv,mrp_tv;
            public ViewHolders(@NonNull View itemView) {
                super(itemView);
                quantity_tv=itemView.findViewById(R.id.quantity_et);
                title_tv=itemView.findViewById(R.id.title_tv);
                stock_tv=itemView.findViewById(R.id.stock_tvPurchase);
                rate_tv=itemView.findViewById(R.id.rate_tv);
                mrp_tv=itemView.findViewById(R.id.mrp_tv);
            }
        }
    }
}
