package com.wangh.e_university;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2017/2/14.
 */

public class ChoosingClassTimeAdapter extends RecyclerView.Adapter<ChoosingClassTimeAdapter.ChoosingClassTimeHolder>{
    private final LayoutInflater inflater;
    private final Context context;
    private ArrayList<ChoosingClassTimeItem> classTimeItems = new ArrayList<ChoosingClassTimeItem>();

    public void addTime(ChoosingClassTimeItem classTimeItem){
        classTimeItems.add(classTimeItem);
    }

    public ChoosingClassTimeAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ChoosingClassTimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChoosingClassTimeHolder classTimeHolder = new ChoosingClassTimeHolder(inflater.inflate(R.layout.class_for_choose_time_item,parent,false));
        return classTimeHolder;
    }

    @Override
    public void onBindViewHolder(ChoosingClassTimeHolder holder, int position) {
        holder.time.setText(classTimeItems.get(position).getTime());
        holder.location.setText(classTimeItems.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return classTimeItems==null?0:classTimeItems.size();
    }

    public static class ChoosingClassTimeHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView location;

        public ChoosingClassTimeHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.classForChooseTime);
            location = (TextView) itemView.findViewById(R.id.classForChooseLocation);
        }
    }
}
