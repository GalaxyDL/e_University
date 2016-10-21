package com.wangh.e_university;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/8/14.
 */
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private ArrayList<InfoItem> infos=new ArrayList<InfoItem>();

    public void addInfo(InfoItem infoItem){
        infos.add(infoItem);
    }

    public InfoAdapter(Context context){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InfoHolder infoHolder= new InfoHolder(layoutInflater.inflate(R.layout.info_item,parent,false));
        return infoHolder;
    }

    @Override
    public void onBindViewHolder(InfoHolder holder, int position) {
        holder.infoText.setText(infos.get(position).getText());
        holder.infoTitle.setText(infos.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return infos==null?0:infos.size();
    }

    public static class InfoHolder extends RecyclerView.ViewHolder{
        TextView infoTitle;
        TextView infoText;

        InfoHolder(View view){
            super(view);
            infoTitle=(TextView)view.findViewById(R.id.titleOfMe);
            infoText=(TextView)view.findViewById(R.id.textOfMe);
        }

    }
}