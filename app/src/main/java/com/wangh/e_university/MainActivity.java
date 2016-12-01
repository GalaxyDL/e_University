package com.wangh.e_university;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class MainActivity extends AppCompatActivity {
    private final String APPID = BmobAppID.getAPPID();
    private UpdateResponse ur;

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private View drawerHeader;
    private DrawerLayout drawerLayout;
    private BottomBar bottomBar;

    private ClassFragment classFragment;
    private ExamFragment examFragment;
    private ScoreFragment scoreFragment;
    private ChooseFragment chooseFragment;
    private LoginFragment loginFragment;
    private MeFragment meFragment;
    private LoginChooseFragment loginChooseFragment;
    private ChooseNotTimeFragment chooseNotTimeFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private SharedPreferences sharedPreferences;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    TextView name = (TextView) drawerHeader.findViewById(R.id.name);
                    TextView id = (TextView) drawerHeader.findViewById(R.id.id);
//                    TextView college=(TextView) drawerHeader.findViewById(R.id.college);
                    name.setText(sharedPreferences.getString("name", ""));
                    id.setText(sharedPreferences.getString("id", ""));
//                    college.setText(sharedPreferences.getString("college",""));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, APPID);

//        BmobUpdateAgent.initAppVersion();

        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i == UpdateStatus.Yes) {
                    ur = updateResponse;
                    Log.d("update","need");
                }
                Log.d("UpdateStatus",i+"");
            }
        });

        BmobUpdateAgent.update(this);

        sharedPreferences = getSharedPreferences("account", MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("isLogin", true)) {
            editor.putBoolean("isLogin", false);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("课程表");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView,true);
            }
        });

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.inflateMenu(R.menu.navigation_drawer_search);
        drawerHeader = navigationView.getHeaderView(0);
        setDrawer();
        setDrawerHeaderInfo();

        fragmentManager = getSupportFragmentManager();

        changeToClass();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.search:
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.navigation_drawer_search);
                        changeToClass();
                        break;
                    case R.id.choose:
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.navigation_drawer_choose);
                        changeToChooseLogin();
//                        changeToChooseNotTime();
                        break;
                    case R.id.me:
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.navigation_darwer_me);
                        changeToMe();
                        break;
                }
                setDrawer();
            }
        });
        //bottomBar.setActiveTabColor("#eeeeee");

    }

    private void changeToClass() {
        toolbar.setTitle("课程表");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (classFragment == null) {
            classFragment = new ClassFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, classFragment);
        fragmentTransaction.commit();
    }

    private void changeToExam() {
        toolbar.setTitle("考试安排");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (examFragment == null) {
            examFragment = new ExamFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, examFragment);
        fragmentTransaction.commit();
    }

    private void changeToScore() {
        toolbar.setTitle("成绩单");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (scoreFragment == null) {
            scoreFragment = new ScoreFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, scoreFragment);
        fragmentTransaction.commit();
    }

    private void changeToChooseLogin() {
        toolbar.setTitle("选课");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (loginChooseFragment == null) {
            loginChooseFragment = new LoginChooseFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, loginChooseFragment);
        fragmentTransaction.commit();
    }

    private void changeToChooseNotTime() {
        toolbar.setTitle("选课");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (chooseNotTimeFragment == null) {
            chooseNotTimeFragment = new ChooseNotTimeFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, chooseNotTimeFragment);
        fragmentTransaction.commit();
    }

    private void changeToLogin() {
        toolbar.setTitle("登录");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, loginFragment);
        fragmentTransaction.commit();
    }

    private void changeToMe() {
        toolbar.setTitle("");
        fragmentTransaction = fragmentManager.beginTransaction();
        if (meFragment == null) {
            meFragment = new MeFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, meFragment);
        fragmentTransaction.commit();
    }

    private void setDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.classTable:
                        navigationView.getMenu().findItem(R.id.examTime).setChecked(false);
                        navigationView.getMenu().findItem(R.id.scores).setChecked(false);
                        changeToClass();
                        break;
                    case R.id.examTime:
                        navigationView.getMenu().findItem(R.id.classTable).setChecked(false);
                        navigationView.getMenu().findItem(R.id.scores).setChecked(false);
                        changeToExam();
                        break;
                    case R.id.scores:
                        navigationView.getMenu().findItem(R.id.examTime).setChecked(false);
                        navigationView.getMenu().findItem(R.id.classTable).setChecked(false);
                        changeToScore();
                        break;
                    case R.id.sportsClass:
                        navigationView.getMenu().findItem(R.id.publicClass).setChecked(false);
                        navigationView.getMenu().findItem(R.id.recommendClass).setChecked(false);
                        break;
                    case R.id.publicClass:
                        navigationView.getMenu().findItem(R.id.sportsClass).setChecked(false);
                        navigationView.getMenu().findItem(R.id.recommendClass).setChecked(false);
                        break;
                    case R.id.recommendClass:
                        navigationView.getMenu().findItem(R.id.publicClass).setChecked(false);
                        navigationView.getMenu().findItem(R.id.sportsClass).setChecked(false);
                        break;
                    case R.id.account:
                        navigationView.getMenu().findItem(R.id.login).setChecked(false);
                        changeToMe();
                        break;
                    case R.id.login:
                        navigationView.getMenu().findItem(R.id.account).setChecked(false);
                        changeToLogin();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void setDrawerHeaderInfo() {
        if (sharedPreferences.getBoolean("isLogin", true)) {
            TextView name = (TextView) drawerHeader.findViewById(R.id.name);
            TextView id = (TextView) drawerHeader.findViewById(R.id.id);
//                    TextView college=(TextView) drawerHeader.findViewById(R.id.college);
            name.setText(sharedPreferences.getString("name", ""));
            id.setText(sharedPreferences.getString("id", ""));
//                    college.setText(sharedPreferences.getString("college",""));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        while (!sharedPreferences.getBoolean("isLogin", true)) {
                            try {
                                wait();
                            } catch (InterruptedException e) {
                            }
                        }
                        Message msg = new Message();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                }
            }).start();
        }
    }

}

