package com.dxjia.httpdemo;

import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by dxjia on 2015-9-10.
 */
public class OkHttpCallback implements Callback {
    private Handler mHandler;
    private int mSuccessEventCode;
    private int mFailEventCode;

    public OkHttpCallback(Handler handler, int successCode, int failCode) {
        mHandler = handler;
        mSuccessEventCode = successCode;
        mFailEventCode = failCode;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        mHandler.sendEmptyMessage(mFailEventCode);
        mHandler.removeMessages(LoadingHandler.EVENT_SHOW_LOADING_DIALOG);
        // 向这个handler发送一个指定的特殊event code
        // 因为此handler继承自LoadingHandler，所以是可以处理的
        mHandler.sendEmptyMessage(LoadingHandler.EVENT_CLOSE_LOADING_DIALOG);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (response != null) {
            String responseBody = response.body().string();

            Message message = Message.obtain(mHandler, mSuccessEventCode, responseBody);
            message.sendToTarget();

            mHandler.removeMessages(LoadingHandler.EVENT_SHOW_LOADING_DIALOG);

            // 向这个handler发送一个指定的特殊event code
            // 因为此handler继承自LoadingHandler，所以是可以处理的
            mHandler.sendEmptyMessage(LoadingHandler.EVENT_CLOSE_LOADING_DIALOG);
        }
    }

    /**
     * 在这里实现一个getHandler接口是为了显示和关闭dialog使用的是同一个handler
     * @return
     */
    public Handler getHandler() {
        return mHandler;
    }
}
