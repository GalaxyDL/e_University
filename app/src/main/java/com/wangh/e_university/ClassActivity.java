package com.wangh.e_university;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

public class ClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View rootView;

    private BaseScheduleItem classItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    classItem=(BaseScheduleItem) getIntent().getSerializableExtra("class");
        switch (classItem.getColorID()){
            case 0:setTheme(R.style.Color0); break;
            case 1:setTheme(R.style.Color1); break;
            case 2:setTheme(R.style.Color2); break;
            case 3:setTheme(R.style.Color3); break;
            case 4:setTheme(R.style.Color4); break;
            case 5:setTheme(R.style.Color5); break;
            case 6:setTheme(R.style.Color6); break;
            case 7:setTheme(R.style.Color7); break;
            case 8:setTheme(R.style.Color8); break;
            case 9:setTheme(R.style.Color9); break;
            case 10:setTheme(R.style.Color10); break;
            case 11:setTheme(R.style.Color11); break;
            case 12:setTheme(R.style.Color12); break;
            case 13:setTheme(R.style.Color13); break;
            case 14:setTheme(R.style.Color14); break;
            case 15:setTheme(R.style.Color15); break;
            case 16:setTheme(R.style.Color16); break;
            case 17:setTheme(R.style.Color17); break;
        }
        if(classItem.isPassed()){
            setTheme(R.style.Grey);
        }


        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_class);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewOfClassInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayoutOfClassInfo);
        toolbar = (Toolbar)findViewById(R.id.toolbarOfClassInfo);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinishAnimate();
            }
        });
        infoAdapter = new InfoAdapter(this);

        rootView = findViewById(R.id.coordinatorLayoutOfClassInfo);
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                onDrawAnimate(this);
                return true;
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            onFinishAnimate();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onDrawAnimate(ViewTreeObserver.OnPreDrawListener onPreDrawListener){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int x=displayMetrics.widthPixels;
        int y=displayMetrics.heightPixels;

        rootView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
        rootView.setScaleY(getIntent().getIntExtra("height",0)/(float)y*1.2f);
        rootView.setScaleX(getIntent().getIntExtra("width",0)/(float)x*1.2f);
        rootView.setPivotX(getIntent().getIntExtra("x",0)*0.8f);
        rootView.setPivotY(getIntent().getIntExtra("y",0)*0.8f);

        rootView.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleY(1)
                .scaleX(1)
                .setDuration(150)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        textAppear();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .start();
    }

    private void textAppear(){
        if(classItem.isExam()){
            infoAdapter.addInfo(new InfoItem(classItem.getTime(),"考试时间"));
        }else{
            infoAdapter.addInfo(new InfoItem(classItem.getTime(),"上课时间"));
            infoAdapter.addInfo(new InfoItem(((ClassItem)classItem).getTeacher(),"老师"));
        }
        infoAdapter.addInfo(new InfoItem(classItem.getLocation(),"地点"));
        collapsingToolbarLayout.setTitle(classItem.getTitle());

        recyclerView.setAdapter(infoAdapter);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
    }

    private void textDisappear(){
        infoAdapter=new InfoAdapter(this);
        collapsingToolbarLayout.setTitle("");
        recyclerView.setAdapter(infoAdapter);
    }

    private void onFinishAnimate(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int x=displayMetrics.widthPixels;
        int y=displayMetrics.heightPixels;

        rootView.setPivotX(getIntent().getIntExtra("x",0)*0.8f);
        rootView.setPivotY(getIntent().getIntExtra("y",0)*0.8f);

        rootView.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleY(getIntent().getIntExtra("height",0)/(float)y*1.2f)
                .scaleX(getIntent().getIntExtra("width",0)/(float)x*1.2f)
                .setDuration(100)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        textDisappear();
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ClassActivity.this.finish();
                        overridePendingTransition(0, 0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .start();
    }

}
