package com.andoidproject.culluze_app.Activity.custumer_area.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity;
import com.andoidproject.culluze_app.Activity.model.Brand_SubBrand_Model;
import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

import static com.andoidproject.culluze_app.Activity.custumer_area.activity.Manufacture_Activity.staticItemList;

public class BrandSubList_Adapter extends RecyclerView.Adapter<BrandSubList_Adapter.ViewHolders>{

  private  ArrayList<Brand_SubBrand_Model.Item> items;
  private boolean hasCart;
    ArrayList<Brand_SubBrand_Model.Item> staticList;

    public BrandSubList_Adapter(ArrayList<Brand_SubBrand_Model.Item> items) {
        this.items=items;
        this.hasCart=false;
    }

    public BrandSubList_Adapter(ArrayList<Brand_SubBrand_Model.Item> items, boolean hasCart,ArrayList<Brand_SubBrand_Model.Item> staticList) {
        this.items=items;
        this.hasCart=hasCart;
        this.staticList=staticList;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brandsub_list,viewGroup,false);
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {



        if(hasCart)
        {
            Brand_SubBrand_Model.Item brand=staticList.get(i);
            viewHolders.textClick.setVisibility(View.GONE);
            viewHolders.cartRelative.setVisibility(View.VISIBLE);
            viewHolders.cartItem.setText(brand.getItmName()+"");
            viewHolders.cartqty.setText(brand.getQuantity()+"");

          /*  for(Brand_SubBrand_Model.Item model:staticList)
            {
                int modelId=model.getId();
                int brandId=  brand.getId();
                if(modelId==brandId)
                {
                    viewHolders.cartItem.setText(model.getItmName()+"");
                    viewHolders.cartqty.setText(model.getQuantity()+"");
                }
                else {

                }
            }
*/

        /*    for(int number=0;number<staticItemList.size();number++)
            {
                Brand_SubBrand_Model.Item model=staticItemList.get(number);

            }
*/
        }
        else {
            Brand_SubBrand_Model.Item brand=items.get(i);
            viewHolders.textClick.setText(brand.getItmName()+"");
            viewHolders.cartRelative.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        int count=0;
        if(hasCart)
        {
            count=staticList.size();
        }
        else {
            count=items.size();
        }
        return count;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView textClick,cartItem,cartqty;
        RelativeLayout cartRelative;
        View view_line;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            textClick=itemView.findViewById(R.id.textClick);
            cartRelative=itemView.findViewById(R.id.cartRelative);
            cartItem=itemView.findViewById(R.id.cartItem);
            cartqty=itemView.findViewById(R.id.cartqty);


        }
    }
}
