package com.wangh.e_university;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wangh on 2016/8/2.
 */
public class ClassFragment extends Fragment {
    private final static int[] MONTH_DAY={31,28,31,30,31,30,31,31,30,31,30,31};
    private final static int[] CLASS_END_HOUR={0,8,9,10,11,14,15,16,17,19,20,21};
    private final static int[] CLASS_END_MIN={0,45,40,55,50,45,40,55,50,15,10,5};

    private final static int START_MONTH=8;
    private final static int START_DAY=29;

    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private DatabaseManager databaseManager;
    private TextView tip;
    private FloatingActionButton floatingActionButton;
    private LinearLayoutManager linearLayoutManager;

    private int nowWeek;
    private int nowDate;
    private int nowMin;
    private int nowHour;
    private int nowDay;
    private int nowMonth;
    private Date date;

    private List<Days> daysList = new ArrayList<Days>();

    private int nowClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobQuery<Days> query = new BmobQuery<Days>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
        query.setLimit(20);
        query.findObjects(new FindListener<Days>() {
            @Override
            public void done(List<Days> list, BmobException e) {
                if(e==null){
                    daysList=list;
                }else {
                    for(int i=1;i<=20;i++){
                        daysList.add(Days.getDefaultDays(i));
                    }
                }
                getClasses();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        tip=(TextView) view.findViewById(R.id.classCount);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatButtonOfClass);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOfClass);
        linearLayoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ClassAdapter(this.getActivity());

        date=new Date();
        nowDate=date.getData();
        if(nowDate==0)nowDate=7;
        Log.d("nowDate",""+nowDate);
        nowMonth=date.getMonth();
        Log.d("nowMonth",""+nowMonth);
        nowWeek=date.getWeekOfTerm();
        Log.d("nowWeek",""+nowWeek);
        nowDay=date.getDay();
        Log.d("nowDay",""+nowDay);
        nowHour=date.getHour();
        Log.d("nowHour",""+nowHour);
        nowMin=date.getMinute();
        Log.d("nowMin",""+nowMin);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getClasses(){
        databaseManager= new DatabaseManager(getContext());

        nowClass=-1;
        int weekCount=0;
        int day=START_DAY;
        int month=START_MONTH;
        int actualDate;
        boolean noClass=false;
        boolean isToday;
        boolean passed;
        ArrayList<ClassItem> classes=(ArrayList<ClassItem>) databaseManager.queryClass();
        ArrayList<ClassItem> dayClasses;

        passed=true;
        for(int i=1;i<=20;i++){
            for(int j=1;j<=7;j++){
                actualDate=daysList.get(i-1).getDays().charAt(j-1)-'0';
                dayClasses = new ArrayList<ClassItem>();

                if(i==nowWeek&&j==nowDate){
                    nowClass=adapter.getItemCount();
                }
                for(ClassItem aClass:classes){
                    if(actualDate!=0){
                        if(actualDate==aClass.getDate()&&(i>=aClass.getWeekStart()&&i<=aClass.getWeekEnd())&&((i%2==0&&aClass.getDoubleWeek()==1)||(i%2==1&&aClass.getSingleWeek()==1))){
                            if(nowWeek==i){
                                if((nowDate==j&&(nowHour<CLASS_END_HOUR[aClass.getTimeEnd()]||(nowHour==CLASS_END_HOUR[aClass.getTimeEnd()]&&nowMin<CLASS_END_MIN[aClass.getTimeEnd()])))||nowDate<j){
                                    weekCount++;
                                }
                                if((nowDate==j&&(nowHour<CLASS_END_HOUR[aClass.getTimeEnd()]||(nowHour==CLASS_END_HOUR[aClass.getTimeEnd()]&&nowMin<CLASS_END_MIN[aClass.getTimeEnd()])))){
                                    if(passed){
                                        passed=false;
                                    }
                                }
                                if(nowDate<j){
                                    if(passed){
                                        passed=false;
                                    }
                                }
                            }
                            aClass.setPassed(passed);
                            try {
                                dayClasses.add((ClassItem) aClass.clone());
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if(j==nowDate&&day==nowDay&&month==nowMonth){
                    isToday=true;
                }else{
                    isToday=false;
                }
                if(dayClasses.size()!=0){
                    adapter.addClass(new ClassItem(j,day,month,isToday));
                }
                for(ClassItem item:dayClasses){
                    adapter.addClass(item);
                }

                day=getDay(day,month,1);
                month=day==1?month+1:month;
                month=month==13?1:month;
            }
            if(i==nowWeek&&weekCount==0){
                passed=false;
            }
        }
        String classCountText;
        if(weekCount==0){
            classCountText="这周没有课啦~休息下吧~";

        }else{
            if(!noClass)classCountText="现在是第"+nowWeek+"周，这周还有"+weekCount+"节课，认真听课哦~";
            else classCountText="下周有"+weekCount+"节课，准备一下吧~";
        }
        tip.setText(classCountText);
        linearLayoutManager.scrollToPositionWithOffset(nowClass,0);
        //recyclerView.smoothScrollToPosition(nowClass);

        databaseManager.closeDB();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutManager.scrollToPositionWithOffset(nowClass,0);
//                recyclerView.smoothScrollToPosition(nowClass);
            }
        });
    }

    private int getMonth(int nowMonth,int offset){
        if(getDay(nowMonth,offset)!=nowDay+offset){
            return nowMonth+1>12?1:nowMonth+1;
        }
        return nowMonth;
    }

    private int getMonth(int nowDay,int nowMonth,int offset){
        if(getDay(nowDay,nowMonth,offset)!=nowDay+offset){
            return nowMonth+1>12?1:nowMonth+1;
        }
        return nowMonth;
    }

    private int getDay(int nowMonth,int offset){
        int result=nowDay;
        result+=offset;
        if(nowMonth!=2){
            if(result>MONTH_DAY[nowMonth-1]){
                result-=MONTH_DAY[nowMonth-1];
            }
        }else {
            int nowYear=date.getYear();
            if(nowYear%400==0||(nowYear%4==0&&nowYear%100!=0)){
                if(result>MONTH_DAY[nowMonth-1]+1){
                    result-=MONTH_DAY[nowMonth-1]+1;
                }else{
                    if(result>MONTH_DAY[nowMonth-1]){
                        result-=MONTH_DAY[nowMonth-1];
                    }
                }
            }
        }
        return result;
    }

    private int getDay(int nowDay,int nowMonth,int offset){
        int result=nowDay;
        result+=offset;
        if(nowMonth!=2){
            if(result>MONTH_DAY[nowMonth-1]){
                result-=MONTH_DAY[nowMonth-1];
            }
        }else {
            int nowYear=date.getYear();
            if(nowYear%400==0||(nowYear%4==0&&nowYear%100!=0)){
                if(result>MONTH_DAY[nowMonth-1]+1){
                    result-=MONTH_DAY[nowMonth-1]+1;
                }else{
                    if(result>MONTH_DAY[nowMonth-1]){
                        result-=MONTH_DAY[nowMonth-1];
                    }
                }
            }
        }
        return result;
    }

}
