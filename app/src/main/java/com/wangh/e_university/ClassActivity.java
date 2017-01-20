package com.wangh.e_university;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class ClassActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                ClassActivity.this.finish();
            }
        });
        infoAdapter = new InfoAdapter(this);



        ClassItem classItem=(ClassItem) getIntent().getSerializableExtra("class");
        collapsingToolbarLayout.setTitle(classItem.getClassTitle());

//        switch (classItem.getColorID()){
//            case 0:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor0)); break;
//            case 1:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor1)); break;
//            case 2:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor2)); break;
//            case 3:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor3)); break;
//            case 4:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor4)); break;
//            case 5:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor5)); break;
//            case 6:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor6)); break;
//            case 7:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor7)); break;
//            case 8:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor8)); break;
//            case 9:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor9)); break;
//            case 10:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor10)); break;
//            case 11:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor11)); break;
//            case 12:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor12)); break;
//            case 13:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor13)); break;
//            case 14:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor14)); break;
//            case 15:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor15)); break;
//            case 16:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor16)); break;
//            case 17:toolbar.setBackgroundColor(getResources().getColor(R.color.classColor17)); break;
//        }
        infoAdapter.addInfo(new InfoItem(classItem.getClassTime(),"上课时间"));
        infoAdapter.addInfo(new InfoItem(classItem.getTeacher(),"老师"));
        infoAdapter.addInfo(new InfoItem(classItem.getClassLocation(),"地点"));
        recyclerView.setAdapter(infoAdapter);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);


    }
}
