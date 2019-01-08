package com.example.kesha.myapplication;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyResponses {
    private static final String TAG = MyResponses.class.getSimpleName();
    private static final String consumer = "hh9qjam3232s5nrv8wt2nugs";
    private static final String consumerSecret = "K8mxCZDYQd";
    private static final String urlAuth = "https://api.lufthansa.com/v1/oauth/token";
    private static Response response = null;
    private static String responseStr = null;
    private static String accessToken = null;

    public static void authorisation() {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("client_id", consumer)
                .add("client_secret", consumerSecret)
                .add("grant_type", "client_credentials")
                .build();
        final Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url(urlAuth)
                .post(formBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = client.newCall(request).execute();
                    responseStr = response.body() != null ? response.body().string() : null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(responseStr!=null){
                    String[] respList = responseStr.split("\"");
                    accessToken = respList[3];
                }
            }
        }).start();
    }

    public static void getCountries(){
        String urlCountries = "https://api.lufthansa.com/v1/references/countries/";
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept","application/json")
                .url(urlCountries)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = client.newCall(request).execute();

                    String str = response.body().string();
                    Log.e(TAG,response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
