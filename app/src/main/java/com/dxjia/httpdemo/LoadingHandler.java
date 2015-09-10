package com.dxjia.httpdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by dxjia on 2015-9-10.
 * 作为基础父类，统一处理loading dialog的显示和关闭
 */
public class LoadingHandler extends Handler {
    // 1s, 超过1s没有返回，自动显示dialog
    public static final int DELAY_TIME_SHOW_LOADING_DIALOG = 1000;
    public static final int EVENT_SHOW_LOADING_DIALOG = -1000;
    public static final int EVENT_CLOSE_LOADING_DIALOG = -1001;

    // 我这里直接使用了ACProgressLite库，你可以换成你自己的显示dialog
    private ACProgressFlower dialog;

    public LoadingHandler(Context context) {
        dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.LTGRAY).build();
        dialog.setCancelable(false);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case EVENT_SHOW_LOADING_DIALOG:
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                break;
            case EVENT_CLOSE_LOADING_DIALOG:
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
                break;
        }

        super.handleMessage(msg);
    }
}
