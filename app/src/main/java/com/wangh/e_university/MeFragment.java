package com.wangh.e_university;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wangh on 2016/8/5.
 */
public class MeFragment extends Fragment {
    private InfoAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private TextView name;
    private SharedPreferences sharedPreferences;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private LoginFragment loginFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me,container,false);
        name=(TextView) view.findViewById(R.id.name);
        adapter=new InfoAdapter(getContext());
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerViewOfMe);
        floatingActionButton=(FloatingActionButton) view.findViewById(R.id.floatButton);
        sharedPreferences=getActivity().getSharedPreferences("account", Context.MODE_APPEND);

        if(sharedPreferences.getBoolean("isLogin",false)){
            name.setText(sharedPreferences.getString("name",""));
            if(!sharedPreferences.getString("id","").isEmpty())adapter.addInfo(new InfoItem(sharedPreferences.getString("id",""),"学号"));
            if(!sharedPreferences.getString("department","").isEmpty())adapter.addInfo(new InfoItem(sharedPreferences.getString("department",""),"学院"));
            if(!sharedPreferences.getString("major","").isEmpty())adapter.addInfo(new InfoItem(sharedPreferences.getString("major",""),"专业"));
        }else {
            adapter.addInfo(new InfoItem("你还没有导入数据呢，点击更新按钮吧！","当你需要查询新的课程时，再次更新数据就好啦OwO"));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        floatingActionButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToLogin();
            }
        });

        return view;
    }

    private void changeToLogin(){
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        if(loginFragment==null){
            loginFragment=new LoginFragment();
        }
        fragmentTransaction.replace(getId(),loginFragment);
        fragmentTransaction.commit();
    }
}
