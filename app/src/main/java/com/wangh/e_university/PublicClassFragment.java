package com.wangh.e_university;

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
 * Created by wangh on 2016/8/22.
 */
public class PublicClassFragment extends ChoosingClassesFragment{
    private DataManager dataManager = new DataManager(getContext());
    private ArrayList<ClassForChoose> classForChooses;

    private RecyclerView recyclerView;
    private ClassForChooseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choose,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOfChoose);
        adapter = new ClassForChooseAdapter(getContext());

        dataManager.updatePublicClasses(this);


//        classForChooses=dataManager.getClassForChooses();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        for(ClassForChoose classForChoose:classForChooses){
//            adapter.addClass(classForChoose);
//        }



        return view;
    }

    @Override
    public void update() {
        classForChooses=dataManager.getClassForChooses();
        for(ClassForChoose classForChoose:classForChooses){
            adapter.addClass(classForChoose);
        }
        recyclerView.refreshDrawableState();
        recyclerView.setAdapter(adapter);
    }
}
