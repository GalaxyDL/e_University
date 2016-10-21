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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import org.jsoup.nodes.Document;

/**
 * Created by wangh on 2016/8/22.
 */
public class LoginChooseFragment extends Fragment {
    private Button submit;
    private TextInputLayout username;
    private TextInputLayout password;
    private TextInputLayout inputCodes;
    private ImageView codes;
    private final String codesUrl = "http://xk.tjut.edu.cn/xsxk/servlet/ImageServlet";
    private Document doc;
    private String usr;
    private String pas;
    private String cod;
    private SharedPreferences sharedPreferences;
    private HttpRequester httpRequester=new HttpRequester();

    private CoordinatorLayout coordinatorLayout;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private PublicClassFragment publicClassFragment;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj == null)
                        Snackbar.make(coordinatorLayout, "获取验证码失败了QwQ", Snackbar.LENGTH_SHORT).show();
                    codes.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 1:
                    doc = (Document) msg.obj;
                    if (!doc.getElementsByTag("a").first().text().equals("登录")) {
                        Snackbar.make(coordinatorLayout, "登录成功", Snackbar.LENGTH_SHORT).show();
//                        DataManager dataManager=new DataManager(getContext());
//                        dataManager.getPublicClasses();
                        changeToPublicClass();
                    } else {
                        Snackbar.make(coordinatorLayout, "登录失败了,看看学号、密码和验证码是否正确QwQ", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        submit = (Button) view.findViewById(R.id.submitLogin);
        username = (TextInputLayout) view.findViewById(R.id.id);
        password = (TextInputLayout) view.findViewById(R.id.password);
        inputCodes = (TextInputLayout) view.findViewById(R.id.inputCodes);
        codes = (ImageView) view.findViewById(R.id.codes);
        httpRequester.setChoose(true);
        getUrlImage();

        fragmentManager=getFragmentManager();

        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrlImage();
            }
        });

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
                                httpRequester.post("http://xk.tjut.edu.cn/xsxk/login.xk","username="+usr
                                        +"&password="+pas
                                        +"&verifyCode="+cod,true);
                                Document doc = httpRequester.get("http://xk.tjut.edu.cn/xsxk/");
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

    private void changeToPublicClass(){
        fragmentTransaction=fragmentManager.beginTransaction();
        if(publicClassFragment ==null){
            publicClassFragment =new PublicClassFragment();
        }
        fragmentTransaction.replace(R.id.frameLayout, publicClassFragment);
        fragmentTransaction.commit();
    }

    private void getUrlImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap image = httpRequester.getImage(codesUrl);
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
}
