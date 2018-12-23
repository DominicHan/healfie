package com.fn.healfie.connect;

import android.util.Log;


import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.utils.JsonUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/11/1.
 */

public class MyConnect {

    public final OkHttpClient client = new OkHttpClient(); //构建FormBody，传入要提交的参数

    public void postData(String url, HashMap<String, String> map, final ConnectBack callBack) {
        Log.e(TAG, "postData: post");
        FormBody.Builder add = new FormBody.Builder();
        for (String key : map.keySet()) {
            add.add(key, map.get(key));
            Log.e(TAG, "postData: key==" + key + "  value==" + map.get(key));
        }
        Log.e(TAG, "postData: url" + url);
        FormBody formBody = add.build();
        final Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: -----------");
                callBack.error("");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String authorization = response.header("authorization");
                Log.e(TAG, "onResponse: " + authorization);
                final String responseStr = response.body().string();
                Log.e(TAG, "onResponse: " + responseStr);
                callBack.success(responseStr);
            }
        });


    }

    public void login(String url, HashMap<String, String> map, final ConnectLoginBack callBack) {
        Log.e(TAG, "postData: post");
        FormBody.Builder add = new FormBody.Builder();
        for (String key : map.keySet()) {
            add.add(key, map.get(key));
            Log.e(TAG, "postData: key==" + key + "  value==" + map.get(key));
        }
        Log.e(TAG, "postData: url" + url);
        FormBody formBody = add.build();
        final Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: -----------");
                callBack.error("");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String authorization = response.header("authorization");
                Log.e(TAG, "onResponse: " + authorization);
                final String responseStr = response.body().string();
                Log.e(TAG, "onResponse: " + responseStr);
                callBack.success(responseStr, authorization);
            }
        });


    }

    public void getData(String url, HashMap<String, String> map, final ConnectBack callBack) {
        url = url + "?";
        for (String key : map.keySet()) {
            Log.e(TAG, "getData: key==" + key + "  value==" + map.get(key));
            url = url + key + "=" + map.get(key) + "&";
        }
        url.substring(0, url.length() - 1);
        Log.e(TAG, "getData: url  " + url);
        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request); //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callBack.error("");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String authorization = response.header("authorization");
                Log.e(TAG, "onResponse: " + authorization);
                final String responseStr = response.body().string();
                Log.e(TAG, "onResponse: " + responseStr);
                BaseBean bean = JsonUtil.getBean(responseStr, BaseBean.class);
                if (bean.getStatus() != null && bean.getStatus().equals("401")) {
                    bean.setResultCode("-10010");
                    callBack.success(JsonUtil.getJson(bean));
                    return;
                }
                callBack.success(responseStr);
            }
        });
    }

    public void putData(String url, HashMap<String, String> map, final ConnectBack callBack) {
        Log.e(TAG, "postData: post");
        FormBody.Builder add = new FormBody.Builder();
        for (String key : map.keySet()) {
            add.add(key, map.get(key));
            Log.e(TAG, "postData: key==" + key + "  value==" + map.get(key));
        }
        url = url + "?";
        for (String key : map.keySet()) {
            url = url + key + "=" + map.get(key) + "&";
        }
        Log.e(TAG, "postData: url" + url);
        FormBody formBody = add.build();
        final Request request = new Request.Builder().url(url).put(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: -----------");
                callBack.error("");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.e(TAG, "onResponse: " + responseStr);
                callBack.success(responseStr);
            }
        });
    }


}
