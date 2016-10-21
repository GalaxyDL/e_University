package com.wangh.e_university;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangh on 2016/6/9.
 */
public class HttpRequester {
    private static Cookie cookie=new Cookie();
//    private static String COOKIE="";
    private static Cookie cookieOfChoose=new Cookie();
    private boolean isChoose=false;

    public Document get(String url){
        String html="";
        Document document;
        Log.d("get",url);

        try{
            URL getUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

            connection.setRequestMethod("GET");
            if(isChoose){
                connection.setRequestProperty("Cookie",cookieOfChoose.toString());
                Log.d("cookie",cookieOfChoose.toString());
            }
            else {
                connection.setRequestProperty("Cookie",cookie.toString());
                Log.d("cookie",cookie.toString());
            }
            //Log.d("get cookie",cookie.toString());
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            connection.setRequestProperty("Connection","Keep-alive");
            connection.connect();
            Log.d("get","connect");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while((lines = reader.readLine())!=null){
                html+=lines;
                //Log.d("html",lines);
            }
            reader.close();
            Log.d("ResponseCode of "+url,connection.getResponseCode()+"");
            connection.disconnect();

            if(!connection.getHeaderField("Set-Cookie").isEmpty()){
                if(isChoose)cookieOfChoose.getCookie(connection.getHeaderField("Set-Cookie"));
                else cookie.getCookie(connection.getHeaderField("Set-Cookie"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if(!html.equals("")){
            document=Jsoup.parse(html);
        }else {
            document=null;
        }
        return document;
    }

    public Document get(String url,String referer,boolean keepAlive){
        String html="";
        Document document;
        Log.d("get",url);

        try{
            URL getUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

            connection.setRequestMethod("GET");
            if(isChoose){
                connection.setRequestProperty("Cookie",cookieOfChoose.toString());
                Log.d("cookie",cookieOfChoose.toString());
            }
            else {
                connection.setRequestProperty("Cookie",cookie.toString());
                Log.d("cookie",cookie.toString());
            }
            //Log.d("get cookie",cookie.toString());
            connection.setRequestProperty("Host","xk.tjut.edu.cn");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            connection.setRequestProperty("Accept-Encoding","gzip, deflate");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            if(keepAlive)connection.setRequestProperty("Connection","Keep-alive");
            connection.setRequestProperty("Referer",referer);
            connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
            connection.connect();
            Log.d("get","connect");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while((lines = reader.readLine())!=null){
                html+=lines;
                Log.d("html",lines);
            }
            reader.close();
            Log.d("ResponseCode of "+url,connection.getResponseCode()+"");
            connection.disconnect();

            if(!connection.getHeaderField("Set-Cookie").isEmpty()){
                if(isChoose)cookieOfChoose.getCookie(connection.getHeaderField("Set-Cookie"));
                else cookie.getCookie(connection.getHeaderField("Set-Cookie"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if(!html.equals("")){
            document=Jsoup.parse(html);
        }else {
            document=null;
        }
        return document;
    }

    public String getString(String url){
        String html="";
        Log.d("get",url);

        try{
            URL getUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

            connection.setRequestMethod("GET");
            if(isChoose){
                connection.setRequestProperty("Cookie",cookieOfChoose.toString());
                Log.d("cookie",cookieOfChoose.toString());
            }
            else {
                connection.setRequestProperty("Cookie",cookie.toString());
                Log.d("cookie",cookie.toString());
            }
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            connection.setRequestProperty("Connection","Keep-alive");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while((lines = reader.readLine())!=null){
                html+=lines;
            }
            reader.close();
            Log.d("ResponseCode",connection.getResponseCode()+"");
            connection.disconnect();

            if(!connection.getHeaderField("Set-Cookie").isEmpty()){
                if(isChoose)cookieOfChoose.getCookie(connection.getHeaderField("Set-Cookie"));
                else cookie.getCookie(connection.getHeaderField("Set-Cookie"));
            }
            Log.d("get cookie",connection.getHeaderField("Set-Cookie"));

        }catch (Exception e){
            e.printStackTrace();
        }
        return html;
    }

    public Document post(String url, String content, Boolean isEncoded) {
        String html="";
        Document document;

        Log.d("post",url+":"+content);

        try {
            URL postUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            connection.setRequestProperty("Connection","Keep-alive");
            if(isChoose){
                connection.setRequestProperty("Cookie",cookieOfChoose.toString());
                Log.d("cookie",cookieOfChoose.toString());
            }
            else {
                connection.setRequestProperty("Cookie",cookie.toString());
                Log.d("cookie",cookie.toString());
            }
            if(isEncoded){
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            }

            connection.connect();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            outputStreamWriter.write(content);

            outputStreamWriter.flush();
            outputStreamWriter.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while((lines = reader.readLine())!=null){
                html+=lines;
            }
            Log.d("ResponseCode",connection.getResponseCode()+"");
            reader.close();
            connection.disconnect();

            if(!connection.getHeaderField("Set-Cookie").isEmpty()){
                if(isChoose)cookieOfChoose.getCookie(connection.getHeaderField("Set-Cookie"));
                else cookie.getCookie(connection.getHeaderField("Set-Cookie"));
            }
            Log.d("post cookie",connection.getHeaderField("Set-Cookie"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!html.equals("")){
            document=Jsoup.parse(html);
        }else {
            document=null;
        }
        return document;
    }

    public Bitmap getImage(String imageUrl){
        Bitmap image=null;
        try {
            URL url=new URL(imageUrl);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();

            InputStream inputStream=connection.getInputStream();
            image= BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            connection.disconnect();
            if(!connection.getHeaderField("Set-Cookie").isEmpty()) {
                if (isChoose) cookieOfChoose.getCookie(connection.getHeaderField("Set-Cookie"));
                else cookie.getCookie(connection.getHeaderField("Set-Cookie"));
            }
            Log.d("image cookie",connection.getHeaderField("Set-Cookie"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return image;
    }

    public static void setCookie(Cookie cookie) {
        HttpRequester.cookie = cookie;
    }

    public static Cookie getCookie() {
        return cookie;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}