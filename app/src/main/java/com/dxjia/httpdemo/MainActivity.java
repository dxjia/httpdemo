package com.dxjia.httpdemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mResultView;

    public static final String URL_WITHOUT_DELAY = "http://dxjia.cn/demos/http-handler-loadingdialog/";
    // delay 5s
    public static final String URL_WITH_DELAY = "http://dxjia.cn/demos/http-handler-loadingdialog/?delay=5";

    private MyHandler mMyHandler;

    public static final int EVENT_GET_RESPONSE_SUCCESS = 0;
    public static final int EVENT_GET_RESPONSE_FAILED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultView = (TextView) findViewById(R.id.result_text_view);
        mMyHandler = new MyHandler(this);

        final OkHttpCallback callback = new OkHttpCallback(mMyHandler, EVENT_GET_RESPONSE_SUCCESS, EVENT_GET_RESPONSE_FAILED);

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 不带延时
                HttpUtils.getResponse(URL_WITHOUT_DELAY, callback);
            }
        });

        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 带延时
                HttpUtils.getResponse(URL_WITH_DELAY, callback);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyHandler extends LoadingHandler {
        public MyHandler(Context context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_GET_RESPONSE_SUCCESS:
                    if (msg.obj != null) {
                        String result = (String) msg.obj;
                        mResultView.setTextColor(Color.BLUE);
                        mResultView.setText(result);
                    }
                    break;
                case EVENT_GET_RESPONSE_FAILED:
                    mResultView.setTextColor(Color.RED);
                    mResultView.setText("get response failed.");
                    break;

            }
            // 让父handler处理dialog的显示和关闭
            super.handleMessage(msg);
        }

    }
}
