package com.andoidproject.culluze_app.Activity.help_area.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andoidproject.culluze_app.R;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolders> {
    Context context;
    ArrayList<String> arrayList;
    public VideoAdapter(FragmentActivity context, ArrayList<String> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_adapter_view,viewGroup,false);
        return new ViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders viewHolders, int i) {
         String str = arrayList.get(i);
        viewHolders.item_tv.setText(str);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView item_tv;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.item_tv);

        }
    }
}
