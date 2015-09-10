package com.dxjia.httpdemo;

import android.os.Handler;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.concurrent.TimeUnit;

/**
 * Created by dxjia on 2015-9-10.
 * 封装okhttp网络请求
 */
public class HttpUtils {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static {
        // 设置最长15S延时
        mOkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
    }

    /**
     * 异步访问网络
     *
     * @param uri              网址
     * @param responseCallback 返回结果处理
     */
    public static void getResponse(String uri, Callback responseCallback) {
        if (responseCallback instanceof OkHttpCallback) {
            OkHttpCallback callback = (OkHttpCallback) responseCallback;
            Handler handler = callback.getHandler();
            if (handler != null) {
                // 延时1S response未返回再显示dialog，这样就弹性啦，速度快的请求不会显示出等待框
                handler.sendEmptyMessageDelayed(LoadingHandler.EVENT_SHOW_LOADING_DIALOG, LoadingHandler.DELAY_TIME_SHOW_LOADING_DIALOG);
            }
        }

        Request request = new Request.Builder().url(uri).build();
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

}
