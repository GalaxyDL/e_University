package com.wangh.e_university;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/8/14.
 */
public class ExamFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExamAdapter adapter;
    private DatabaseManager databaseManager;
    private TextView examCount;
    private int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ExamAdapter(this.getActivity());
        databaseManager= new DatabaseManager(getContext());
        ArrayList<ExamItem> exams = (ArrayList<ExamItem>) databaseManager.queryExam();
        ArrayList<ClassItem> classes = (ArrayList<ClassItem>) databaseManager.queryClass();
        for(ExamItem aExam:exams){
            for (ClassItem aClass:classes){
                if(aClass.getTitle().equals(aExam.getTitle())){
                    aExam.setColorID(aClass.getColorID());
                }
            }
            adapter.addExam(aExam);
        }
        count=exams.size();
        databaseManager.closeDB();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOfExam);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        recyclerView.setAdapter(adapter);

        examCount=(TextView)view.findViewById(R.id.examCount);
        String examCountText;
        if(count==0){
            examCountText="还没安排好考试呢，同学先上课吧0w0";

        }else {
            examCountText="安排好"+count+"场考试了QwQ，同学加油哦~";
        }
        examCount.setText(examCountText);

        return view;
    }

}

