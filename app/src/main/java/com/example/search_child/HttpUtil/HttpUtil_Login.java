package com.example.search_child.HttpUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil_Login {
    public static void loginWithOkHttp(String address, String account, String password, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("name",account)
                .add("pwd",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //注册
    public static void registerWithOkHttp(String address,String account,String password,String age,String sex,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("name",account)
                .add("pwd",password)
                .add("age",age)
                .add("sex",sex)
                .build();

        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build()
                ;
        client.newCall(request).enqueue(callback);
    }

}
