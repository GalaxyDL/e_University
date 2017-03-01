package com.wangh.e_university;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangh on 2017/2/13.
 */

public class ChoosingIndexFragment extends Fragment {
    private RecyclerView baseInfoRV;
    private RecyclerView nowStateRV;
    private RecyclerView chosenCountRV;

    private InfoAdapter baseInfoAdapter;
    private InfoAdapter nowStateAdapter;
    private InfoAdapter chosenCountAdapter;

    private NestedScrollView nestedScrollView;

    private ProgressDialog progressDialog;

    private LoginHelper loginHelper;

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        baseInfoAdapter = new InfoAdapter(getContext());
        nowStateAdapter = new InfoAdapter(getContext());
        chosenCountAdapter = new InfoAdapter(getContext());
        loginHelper = new LoginHelper(getContext(), getActivity(), new LoginHelper.LoginListener() {
            @Override
            public void done() {
                context = getContext();
                if(context!=null){
                    progressDialog = ProgressDialog.show(getContext(),"","正在加载");
                    getInfo();
                }

            }
        });
        loginHelper.login();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choosing_index,container,false);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
        baseInfoRV = (RecyclerView) view.findViewById(R.id.baseInfo);
        nowStateRV = (RecyclerView) view.findViewById(R.id.nowState);
        chosenCountRV = (RecyclerView) view.findViewById(R.id.chosenCount);

        nestedScrollView.setVisibility(View.INVISIBLE);

        baseInfoRV.setLayoutManager(new LinearLayoutManager(getContext()));
        nowStateRV.setLayoutManager(new LinearLayoutManager(getContext()));
        chosenCountRV.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void getInfo(){
        DataManager dataManager = new DataManager(getContext());
        dataManager.getChoosingInfo(new DataManager.ChoosingInfoUpdateListener() {
            @Override
            public void done(ChoosingInfo choosingInfo) {
                progressDialog.dismiss();
                baseInfoAdapter.addInfo(new InfoItem("姓名",choosingInfo.getName()));
                baseInfoAdapter.addInfo(new InfoItem("年级",choosingInfo.getGrade()));
                baseInfoAdapter.addInfo(new InfoItem("学院",choosingInfo.getDepartment()));
                baseInfoAdapter.addInfo(new InfoItem("专业",choosingInfo.getMajor()));
                baseInfoAdapter.addInfo(new InfoItem("班级",choosingInfo.getClasses()));
                baseInfoAdapter.addInfo(new InfoItem("学位",choosingInfo.getDegree()));

                nowStateAdapter.addInfo(new InfoItem("起始时间",choosingInfo.getStartTime()));
                nowStateAdapter.addInfo(new InfoItem("结束时间",choosingInfo.getEndTime()));

                chosenCountAdapter.addInfo(new InfoItem("毕业学分要求",""+choosingInfo.getLimitCredit()));
                chosenCountAdapter.addInfo(new InfoItem("当前已选学分",""+choosingInfo.getNowCredit()));
                chosenCountAdapter.addInfo(new InfoItem("选课学分上限",""+choosingInfo.getMaximumCredit()));

                updateView();
            }
        });
    }

    private void updateView(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.setVisibility(View.VISIBLE);
                baseInfoRV.setAdapter(baseInfoAdapter);
                nowStateRV.setAdapter(nowStateAdapter);
                chosenCountRV.setAdapter(chosenCountAdapter);
            }
        });
    }
}
