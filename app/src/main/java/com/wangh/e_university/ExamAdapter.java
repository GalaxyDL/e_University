package com.wangh.e_university;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/8/14.
 */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private ArrayList<ExamItem> exams=new ArrayList<ExamItem>();

    public void addExam(ExamItem examItem){
        exams.add(examItem);
    }

    public ExamAdapter(Context context){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ExamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ExamHolder examHolder= new ExamHolder(layoutInflater.inflate(R.layout.exam_item,parent,false));
        return examHolder;
    }

    @Override
    public void onBindViewHolder(ExamHolder holder, int position) {
        holder.examTime.setText(exams.get(position).getTime());
        holder.examTitle.setText(exams.get(position).getTitle());
        holder.examLocation.setText(exams.get(position).getLocation());
        switch (exams.get(position).getColorID()){
            case 0:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor0)); break;
            case 1:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor1)); break;
            case 2:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor2)); break;
            case 3:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor3)); break;
            case 4:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor4)); break;
            case 5:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor5)); break;
            case 6:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor6)); break;
            case 7:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor7)); break;
            case 8:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor8)); break;
            case 9:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor9)); break;
            case 10:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor10)); break;
            case 11:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor11)); break;
            case 12:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor12)); break;
            case 13:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor13)); break;
            case 14:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor14)); break;
            case 15:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor15)); break;
            case 16:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor16)); break;
            case 17:holder.examBlock.setBackgroundColor(context.getResources().getColor(R.color.classColor17)); break;
        }
    }

    @Override
    public int getItemCount() {
        return exams==null?0:exams.size();
    }

    public static class ExamHolder extends RecyclerView.ViewHolder{
        TextView examTitle;
        TextView examLocation;
        TextView examTime;
        LinearLayout examBlock;
        ExamHolder(View view){
            super(view);
            examTitle=(TextView)view.findViewById(R.id.examTitle);
            examLocation=(TextView)view.findViewById(R.id.examLocation);
            examTime=(TextView)view.findViewById(R.id.examTime);
            examBlock=(LinearLayout)view.findViewById(R.id.examBlock);
        }

    }
}