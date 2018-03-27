package com.wangh.e_university;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class LostPropertyFragment extends Fragment {
    private static final String TAG = "LostPropertyFragment";

    private ArrayList<LostProperty> lostProperties = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    private LostPropertyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        BmobQuery<LostProperty> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.setMaxCacheAge(TimeUnit.HOURS.toMillis(1));
        query.findObjects(new FindListener<LostProperty>() {
            @Override
            public void done(List<LostProperty> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: " + list.size());
                    adapter.removeAll();
                    for (LostProperty lostProperty : list) {
                        adapter.addLostProperty(lostProperty);
                    }
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "done: adapter " + adapter.getItemCount());
                } else {
                    Log.d(TAG, "done: failed" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_property, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.lostRecyclerView);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.lostFloatButton);

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new LostPropertyAdapter(getContext());
        adapter.setListener(new LostPropertyAdapter.OnclickListener() {
            @Override
            public void onClick(View view, int position, LostPropertyAdapter.LostPropertyHolder holder) {
                LostProperty lp = adapter.getLostProperty(position);
                adapter.removeLostProperty(position);
                if (lp.getPhoto() != null) {
                    lp.getPhoto().delete();
                }
                lp.delete();
                //adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LostActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}

