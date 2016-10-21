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

    }

    @Override
    public int getItemCount() {
        return exams==null?0:exams.size();
    }

    public static class ExamHolder extends RecyclerView.ViewHolder{
        TextView examTitle;
        TextView examLocation;
        TextView examTime;

        ExamHolder(View view){
            super(view);
            examTitle=(TextView)view.findViewById(R.id.examTitle);
            examLocation=(TextView)view.findViewById(R.id.examLocation);
            examTime=(TextView)view.findViewById(R.id.examTime);
        }

    }
}