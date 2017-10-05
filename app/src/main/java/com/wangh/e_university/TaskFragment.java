package com.wangh.e_university;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by MXZ on 2017/9/30.
 */

public class TaskFragment extends Fragment {
    RecyclerView mRvTask;
    private List<Task> tasks;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queryTasks();
        mRvTask = (RecyclerView) view.findViewById(R.id.rv_task);
        mRvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    private void queryTasks() {
        BmobQuery<Task> query = new BmobQuery<Task>();
        query.order("-createdAt");// 按照时间降序
        query.findObjects(new FindListener<Task>() {
            @Override
            public void done(List<Task> list, BmobException e) {
                if(e==null){
                    tasks=list;
                    mRvTask.setAdapter(new TaskAdapter(getActivity(), tasks));
                }else {
                    e.printStackTrace();
                }
            }
        });
    }
}