package com.example.liuhui.codex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by liuhui on 2018/9/18.
 */

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "==== 接收到了广播  ：  onReceive ====");
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Log.d(TAG, "安装了  : " + packageName);
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Log.d(TAG, "卸载了  : " + packageName);
        }
    }
}
