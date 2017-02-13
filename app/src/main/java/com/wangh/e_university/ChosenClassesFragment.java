package com.wangh.e_university;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangh on 2017/2/13.
 */

public class ChosenClassesFragment extends Fragment {
    private DataManager dataManager;

    private RecyclerView recyclerView;

    private ClassForChooseAdapter adapter;

    private ProgressDialog progressDialog;

    private LoginHelper loginHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dataManager = new DataManager(getContext());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choose,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOfChoose);
        adapter = new ClassForChooseAdapter(getContext());

        loginHelper = new LoginHelper(getContext(), getActivity(), new LoginHelper.LoginListener() {
            @Override
            public void done() {
                progressDialog = ProgressDialog.show(getContext(),"","正在加载");
                dataManager.getChosenClass(new DataManager.ChosenClassesUpdateListener() {
                    @Override
                    public void done(List<ClassForChoose> result) {
                        updateView(result);
                    }
                });
            }
        });
        loginHelper.login();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void updateView(List<ClassForChoose> list){
        for(ClassForChoose classForChoose:list){
            adapter.addClass(classForChoose);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
