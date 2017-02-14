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

import java.util.ArrayList;

/**
 * Created by wangh on 2016/12/1.
 */

public abstract class ChoosingClassesFragment extends Fragment implements DataManager.UpdateListener {
    protected DataManager dataManager = new DataManager(getContext());
    private ArrayList<ClassForChoose> classForChooses;

    private RecyclerView recyclerView;
    private ClassForChooseAdapter adapter;

    private ProgressDialog progressDialog;

    private LoginHelper loginHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choose,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOfChoose);
        adapter = new ClassForChooseAdapter(getContext(),getActivity());

        loginHelper = new LoginHelper(getContext(), getActivity(), new LoginHelper.LoginListener() {
            @Override
            public void done() {
                progressDialog = ProgressDialog.show(getContext(),"","正在加载");
                updateView();
            }
        });
        loginHelper.login();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    protected abstract void updateView();

    @Override
    public void update() {
        progressDialog.dismiss();
        classForChooses=dataManager.getClassForChooses();
        for(ClassForChoose classForChoose:classForChooses){
            adapter.addClass(classForChoose);
        }
        recyclerView.refreshDrawableState();
        recyclerView.setAdapter(adapter);
    }

}
