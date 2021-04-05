package com.andoidproject.culluze_app.Activity.route_area.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.andoidproject.culluze_app.Activity.route_area.Route_planner_sub;
import com.andoidproject.culluze_app.R;

public class RoutePlanner_Adapter extends RecyclerView.Adapter<RoutePlanner_Adapter.ViewHolders> {

    public static String setTitle;
    Context context;


    public RoutePlanner_Adapter(Context context)
        {
            this.context= context;
    }


    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_view,viewGroup,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolders viewHolders, int i) {

        viewHolders.area_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setTitle="A";
                Intent intent = new Intent(context, Route_planner_sub.class);
                context.startActivity(intent);

            }
        });
        viewHolders.beat_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTitle="B";
                Intent intent = new Intent(context, Route_planner_sub.class);
                context.startActivity(intent);
            }
        });

        viewHolders.market_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTitle="C";
                Intent intent = new Intent(context, Route_planner_sub.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        RelativeLayout area_rv,market_rv,beat_rv;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            area_rv=itemView.findViewById(R.id.area_rv);
            beat_rv=itemView.findViewById(R.id.beat_rv);
            market_rv=itemView.findViewById(R.id.market_rv);
        }
    }


}
