package com.wangh.e_university;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by MXZ on 2017/10/1.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> mTasks;
    private Context mContext;
    private SharedPreferences sharedPreferences;


    public TaskAdapter(Context context, List<Task> tasks) {
        mTasks = tasks;
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences("account", Context.MODE_PRIVATE);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.task_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        holder.title.setText(mTasks.get(position).getTitle());
        holder.describe.setText(mTasks.get(position).getDescribe());
        holder.points.setText(mTasks.get(position).getPoints() + "");
        if (mTasks.get(position).isReceived()) {
            holder.received.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(null);
        } else {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences = mContext.getSharedPreferences("account", Context.MODE_PRIVATE);
                    if (sharedPreferences.getBoolean("isLogin", false)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("确定接受任务？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTasks.get(position).setReceived(true);

                                final String id = sharedPreferences.getString("id", null);
                                if (id != null) {
                                    mTasks.get(position).setReceiver(sharedPreferences.getString("id", null));
                                }

                                mTasks.get(position).update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null && id != null) {
                                            Toast.makeText(mContext, "接受成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, "出现了未知错误，请稍后重试", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                holder.received.setVisibility(View.VISIBLE);
                                holder.itemView.setOnClickListener(null);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).setCancelable(false).show();
                    } else {
                        Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView describe;
        private TextView points;
        private TextView received;

        public TaskViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            describe = (TextView) itemView.findViewById(R.id.tv_describe);
            points = (TextView) itemView.findViewById(R.id.tv_points);
            received = (TextView) itemView.findViewById(R.id.tv_received);
        }
    }
}