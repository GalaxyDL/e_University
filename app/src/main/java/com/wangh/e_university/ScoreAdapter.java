package com.wangh.e_university;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/8/12.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private ArrayList<ScoreItem> scores = new ArrayList<ScoreItem>();

    public ScoreAdapter(Context context){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }

    public void addScore(ScoreItem scoreItem){
        scores.add(scoreItem);
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    @Override
    public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScoreHolder scoreHolder = new ScoreHolder(layoutInflater.inflate(R.layout.score_item,parent,false));
        return scoreHolder;
    }

    @Override
    public void onBindViewHolder(ScoreHolder holder, int position) {
        holder.title.setText(scores.get(position).getTitle());
        holder.score.setText(String.valueOf(scores.get(position).getScore()));
        holder.credit.setText(String.valueOf(scores.get(position).getCredit()));
    }

    public static class ScoreHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView score;
        TextView credit;

        ScoreHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.scoreTitle);
            score=(TextView)view.findViewById(R.id.scoreNumber);
            credit=(TextView)view.findViewById(R.id.scoreCredit);
        }


    }
}
