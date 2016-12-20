package com.wangh.e_university;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

public class InfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOfInfo);
        toolbar=(Toolbar) findViewById(R.id.toolbarOfInfo);

        toolbar.setTitle("关于");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoActivity.this.finish();
            }
        });

        infoAdapter = new InfoAdapter(this);
        infoAdapter.addInfo(new InfoItem("Haochen Wang","作者"));
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            infoAdapter.addInfo(new InfoItem(packageInfo.versionName,"版本"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        infoAdapter.addInfo(new InfoItem("https://github.com/GalaxyDL/e_University","开放源代码"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(infoAdapter);
    }
}
