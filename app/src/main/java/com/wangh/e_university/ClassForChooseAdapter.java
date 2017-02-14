package com.wangh.e_university;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/9/7.
 */
public class ClassForChooseAdapter extends RecyclerView.Adapter<ClassForChooseAdapter.ClassForChooseHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final Activity activity;
    private ArrayList<ClassForChoose> classForChooses=new ArrayList<ClassForChoose>();
    private int wish;
    private LoginHelper loginHelper;
    private ClassForChoose choosingClass;

    public void addClass(ClassForChoose classForChoose){
        classForChooses.add(classForChoose);
    }

    public ClassForChooseAdapter(Context context,Activity activity){
        this.context=context;
        this.activity=activity;
        layoutInflater=LayoutInflater.from(context);
        loginHelper = new LoginHelper(context, activity, new LoginHelper.LoginListener() {
            @Override
            public void done() {
                choose();
            }
        });
    }

    @Override
    public ClassForChooseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ClassForChooseHolder classForChooseHolder= new ClassForChooseHolder(layoutInflater.inflate(R.layout.class_for_choose_item,parent,false));
        return classForChooseHolder;
    }

    @Override
    public void onBindViewHolder(ClassForChooseHolder holder, int position) {
        final ClassForChoose item = classForChooses.get(position);
        holder.title.setText(item.getTitle());
        holder.credit.setText(item.getCredit()+"");
        holder.hours.setText(item.getHours()+"");
        holder.sort.setText(item.getSort());
        holder.nature.setText(item.getNature());
        holder.category.setText(item.getCategory());
        holder.limit.setText(item.getMaximumNumber()+"");
        holder.now.setText(item.getCurrentNumber()+"");
        holder.timeAdapter = new ChoosingClassTimeAdapter(context);
        ChoosingClassTimeItem aClassTimeItem;
        for(int i=0;i<item.getData().size();i++){
            aClassTimeItem = new ChoosingClassTimeItem();
            aClassTimeItem.setTime(ClassInfoConst.DATA[item.getData().get(i)]+
                    " 第"+item.getTimeStart().get(i)+
                    "节-第"+item.getTimeEnd().get(i)+
                    "节 "+item.getWeek().get(i)
            );
            aClassTimeItem.setLocation(item.getLocation().get(i));
            holder.timeAdapter.addTime(aClassTimeItem);
        }
        holder.times.setAdapter(holder.timeAdapter);
        holder.times.setLayoutManager(new LinearLayoutManager(context));
        if(item.isChosen()){
            holder.choose.setVisibility(View.GONE);
        }else{
            holder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choosingClass = item;
                    loginHelper.login();
                }
            });
        }

    }

    private void choose(){
        new AlertDialog.Builder(context)
                .setTitle("请选择志愿")
                .setSingleChoiceItems(new String[]{"第一志愿", "第二志愿", "第三志愿"}
                        , 0
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                wish=i+1;
                            }
                        })
                .setPositiveButton("选课", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("choose", wish + " "+choosingClass.toString());
//                                    doneDialog(true);
                        DataManager dataManager = new DataManager(context);
                        dataManager.chooseClass(choosingClass, choosingClass.getChoosingSort(), wish, new DataManager.ChooseClassListener() {
                            @Override
                            public void done(boolean isSuccess) {
                                doneDialog(isSuccess);
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void doneDialog(final boolean isSuccess){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String title=isSuccess?"选课成功":"选课失败";
                new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classForChooses==null?0:classForChooses.size();
    }

    public static class ClassForChooseHolder extends RecyclerView.ViewHolder{
        LinearLayout info;
        TextView title;
        TextView credit;
        TextView hours;
        TextView sort;
        TextView nature;
        TextView category;
        TextView now;
        TextView limit;
        RecyclerView times;
        Button choose;

        ChoosingClassTimeAdapter timeAdapter;

        public ClassForChooseHolder(View view){
            super(view);
            info=(LinearLayout)view.findViewById(R.id.classForChooseInfo);
            title=(TextView)view.findViewById(R.id.classForChooseTitle);
            credit=(TextView)view.findViewById(R.id.classForChooseCredit);
            hours=(TextView)view.findViewById(R.id.classForChooseHours);
            sort=(TextView)view.findViewById(R.id.classForChooseSort);
            nature=(TextView)view.findViewById(R.id.classForChooseNature);
            category=(TextView)view.findViewById(R.id.classForChooseCategory);
            now=(TextView)view.findViewById(R.id.classForChooseNow);
            limit=(TextView)view.findViewById(R.id.classForChooseLimit);
            times=(RecyclerView) view.findViewById(R.id.classForChooseTimes);
            choose=(Button) view.findViewById(R.id.classForChooseSubmit);
        }

    }

}
