package com.wangh.e_university;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import org.jsoup.nodes.Document;

/**
 * Created by wangh on 2016/8/4.
 */
public class LoginFragment extends Fragment {
    private Button submit;
    private TextInputLayout username;
    private TextInputLayout password;
    private TextInputLayout inputCodes;
    private SwitchCompat rememberPas;
    private ImageView codes;
    private final String codesUrl = "http://my.tjut.edu.cn/captchaGenerate.portal";
    private Document doc;
    private String usr;
    private String pas;
    private String cod;
    private SharedPreferences sharedPreferences;
    private HttpRequester httpRequester=new HttpRequester();
//    public static int updated=0;

    private CoordinatorLayout coordinatorLayout;

    private MeFragment meFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj == null)
                        Snackbar.make(coordinatorLayout, "获取验证码失败", Snackbar.LENGTH_SHORT).show();
                    codes.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 1:
                    doc = (Document) msg.obj;
                    if (!doc.getElementsByClass("userinfo").first().text().equals("您好！")) {
                        synchronized (this) {
                            String name = doc.getElementsByClass("userinfo").first().text();
                            name = name.replace("您好！", "");
                            name = name.replace("，", "");
                            name = name.replace("\n", "");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", usr);
                            editor.putString("name", name);
                            editor.putBoolean("isLogin", true);
                            if(rememberPas.isChecked()){
                                editor.putBoolean("remPas",true);
                                editor.putString("pas",pas);
                            }else{
                                editor.putBoolean("remPas",false);
                            }
                            notifyAll();
                            editor.commit();
                            Snackbar.make(coordinatorLayout, "登录成功！" + name+"。正在更新数据", Snackbar.LENGTH_SHORT).show();
                        }
                        DataManager dataManager=new DataManager(getContext());
//                        updated=0;
                        dataManager.updateInfo();
                        dataManager.updateClassTable();
                        dataManager.updateScore();
                        dataManager.updateExam();

                        changeToMe();
                    } else {
                        Snackbar.make(coordinatorLayout, "登录失败", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        submit = (Button) view.findViewById(R.id.submitLogin);
        username = (TextInputLayout) view.findViewById(R.id.id);
        password = (TextInputLayout) view.findViewById(R.id.password);
        inputCodes = (TextInputLayout) view.findViewById(R.id.inputCodes);
        rememberPas = (SwitchCompat) view.findViewById(R.id.rememberPassword);
        codes = (ImageView) view.findViewById(R.id.codes);
        httpRequester.setChoose(false);
        getUrlImage();

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrlImage();
            }
        });

        sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isLogin",false)){
            username.getEditText().setText(sharedPreferences.getString("id",""));
            if(sharedPreferences.getBoolean("remPas",false)){
                password.getEditText().setText(sharedPreferences.getString("pas",""));
                rememberPas.setChecked(true);
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                usr = username.getEditText().getText().toString();
                pas = password.getEditText().getText().toString();
                cod = inputCodes.getEditText().getText().toString();
                login();
            }
        });

        return view;
    }

    private void login() {
        if (!usr.equals("")) {
            username.setErrorEnabled(false);
            if (!pas.equals("")) {
                password.setErrorEnabled(false);
                if (!cod.equals("")) {
                    inputCodes.setErrorEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {
//                                httpRequester.post("http://my.tjut.edu.cn/userPasswordValidate.portal", "Login.Token1=" + usr
//                                                + "&Login.Token2=" + pas
//                                                + "&captchaField=" + cod
//                                                + "&goto=http://my.tjut.edu.cn/loginSuccess.portal" +
//                                                "&gotoOnFail=http://my.tjut.edu.cn/loginFailure.portal");
                                httpRequester.post("http://ssfw.tjut.edu.cn/ssfw/j_spring_ids_security_check","j_username="+usr
                                +"&j_password="+pas
                                +"&validateCode="+cod,false);
                                //Document doc = httpRequester.get("http://my.tjut.edu.cn/");
                                Document doc = httpRequester.get("http://ssfw.tjut.edu.cn/ssfw/index.do");
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = doc;
                                handle.sendMessage(msg);
                            }
                        }
                    }).start();
                } else {
                    inputCodes.setErrorEnabled(true);
                    inputCodes.setError("请输入验证码");
                }
            } else {
                password.setErrorEnabled(true);
                password.setError("请输入密码");
            }
        } else {
            username.setErrorEnabled(true);
            username.setError("请输入学号");
        }
    }

    private void getUrlImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap image = httpRequester.getImage("http://ssfw.tjut.edu.cn/ssfw/jwcaptcha.do");
                Message msg = new Message();
                msg.what = 0;
                msg.obj = image;
                handle.sendMessage(msg);
            }
        }).start();
    }

    private void hideSoftKeyboard() {
        Activity activity = getActivity();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void changeToMe(){
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        if(meFragment==null){
            meFragment=new MeFragment();
        }
        fragmentTransaction.replace(getId(),meFragment);
        fragmentTransaction.commit();
    }
}
