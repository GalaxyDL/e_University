package com.wangh.e_university;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/9/7.
 */
public class ClassForChooseAdapter extends RecyclerView.Adapter<ClassForChooseAdapter.ClassForChooseHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private ArrayList<ClassForChoose> classForChooses=new ArrayList<ClassForChoose>();

    public void addClass(ClassForChoose classForChoose){
        classForChooses.add(classForChoose);
    }

    public ClassForChooseAdapter(Context context){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ClassForChooseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClassForChooseHolder classForChooseHolder= new ClassForChooseHolder(layoutInflater.inflate(R.layout.class_for_choose_item,parent,false));
        return classForChooseHolder;
    }

    @Override
    public void onBindViewHolder(ClassForChooseHolder holder, int position) {
        holder.title.setText(classForChooses.get(position).getTitle());
        holder.category.setText(classForChooses.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return classForChooses==null?0:classForChooses.size();
    }

    public static class ClassForChooseHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView category;

        ClassForChooseHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.classForChooseTitle);
            category=(TextView)view.findViewById(R.id.classForChooseCategory);
        }

    }

}
