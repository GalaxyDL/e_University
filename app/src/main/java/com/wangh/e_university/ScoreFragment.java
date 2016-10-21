package com.wangh.e_university;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/8/12.
 */
public class ScoreFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView averageCredit;
    private ScoreAdapter adapter;
    private DatabaseManager databaseManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ScoreAdapter(getContext());
        databaseManager=new DatabaseManager(getContext());

        ArrayList<ScoreItem> scores = (ArrayList<ScoreItem>) databaseManager.queryScore();
        for(ScoreItem aScore:scores){
            adapter.addScore(aScore);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_score,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewOfScore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        averageCredit=(TextView)view.findViewById(R.id.averageCredit);
        String averageCredit=databaseManager.queryAverageCredit();
        if(averageCredit.equals("×")){
            this.averageCredit.setText("没有查到一科成绩呢QwQ");
        }else{
            this.averageCredit.setText(databaseManager.queryAverageCredit());
        }

        Log.d("average credit",databaseManager.queryAverageCredit());

        recyclerView.setAdapter(adapter);

        databaseManager.closeDB();

        return view;
    }
}
