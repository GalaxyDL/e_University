package com.wangh.e_university;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import org.jsoup.nodes.Document;

/**
 * Created by wangh on 2017/2/13.
 */

public class LoginHelper{
    private TextInputLayout username;
    private TextInputLayout password;
    private TextInputLayout inputCodes;
    private SwitchCompat rememberPas;
    private ImageView codes;
    private final String codesUrl = "http://xk.tjut.edu.cn/xsxk/servlet/ImageServlet";
    private Document doc;
    private String usr;
    private String pas;
    private String cod;
    private SharedPreferences sharedPreferences;
    private HttpRequester httpRequester=new HttpRequester();

    private ProgressDialog progressDialog;

    private CoordinatorLayout coordinatorLayout;

    private Context context;
    private Activity activity;

    private LoginListener listener;
    private IsLoginListener isLoginListener;

    public interface LoginListener{
        void done();
    }

    public interface IsLoginListener{
        void done(boolean isLogin);
    }


    public LoginHelper(Context context,Activity activity, final LoginListener listener){
        this.context = context;
        this.activity = activity;
        this.listener = listener;
        httpRequester.setChoose(true);
        isLoginListener = new IsLoginListener() {
            @Override
            public void done(boolean isLogin) {
                progressDialog.dismiss();
                if(isLogin){
                    listener.done();
                }else{
                    doLogin();
                }
            }
        };
    }

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
                        listener.done();
                        Snackbar.make(coordinatorLayout, "登录成功", Snackbar.LENGTH_SHORT).show();
                    } else {
                        doLogin();
                        Snackbar.make(coordinatorLayout, "登录失败了,看看学号、密码和验证码是否正确QwQ", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    doc = (Document) msg.obj;
                    if(doc==null){
                        isLoginListener.done(false);
                        Log.d("login","false");
                    }else {
                        if (!doc.getElementsByTag("a").first().text().equals("登录")) {
                            isLoginListener.done(true);
                            Log.d("login","true");
                            Log.d("login",doc.getElementsByTag("a").first().text());
                        } else {
                            isLoginListener.done(false);
                            Log.d("login","false");
                        }
                    }

                    break;
            }
        }
    };

    private void isLogin(IsLoginListener isLoginListener){
        this.isLoginListener = isLoginListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = httpRequester.get("http://xk.tjut.edu.cn/xsxk/index.xk");
                Message msg = new Message();
                msg.what = 2;
                msg.obj = doc;
                handle.sendMessage(msg);
            }
        }).start();
    }

    public void login(){
        progressDialog = ProgressDialog.show(context,"","请稍后");
        isLogin(isLoginListener);
    }

    private void doLogin(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_login,null);

        username = (TextInputLayout) view.findViewById(R.id.id_dialog);
        password = (TextInputLayout) view.findViewById(R.id.password_dialog);
        inputCodes = (TextInputLayout) view.findViewById(R.id.inputCodes_dialog);
        rememberPas = (SwitchCompat) view.findViewById(R.id.rememberPassword_dialog);
        codes = (ImageView) view.findViewById(R.id.codes_dialog);

        getUrlImage();

        coordinatorLayout = (CoordinatorLayout) activity.findViewById(R.id.coordinatorLayout);

        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle("请登录")
                .setView(view)
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hideSoftKeyboard();
                        usr = username.getEditText().getText().toString();
                        pas = password.getEditText().getText().toString();
                        cod = inputCodes.getEditText().getText().toString();
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
                })
                .create();
        dialog.show();

        codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrlImage();
            }
        });

        sharedPreferences = activity.getSharedPreferences("account", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isLogin",false)){
            username.getEditText().setText(sharedPreferences.getString("id",""));
            if(sharedPreferences.getBoolean("remPas",false)){
                password.getEditText().setText(sharedPreferences.getString("pas",""));
                rememberPas.setChecked(true);
            }
        }
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
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}