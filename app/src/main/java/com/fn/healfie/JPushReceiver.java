package com.fn.healfie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fn.healfie.main.MainActivity;

import cn.jpush.android.api.JPushInterface;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static cn.jpush.android.api.JPushInterface.ACTION_NOTIFICATION_OPENED;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private String extras;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            if (bundle != null) {
                Log.d(TAG, "[MyReceiver] JPush用户注册成功" + bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID));
            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            if (bundle != null) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            }
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setClass(context, PushTipActivity.class);
//            context.startActivity(intent);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            if (bundle != null) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
            }
        } else if (ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] ACTION_NOTIFICATION_OPENED");

            JPushInterface.reportNotificationOpened(context, JPushInterface.EXTRA_MSG_ID);//统计消息打开的次数
//            if (bundle != null) {
//                openNotification(context, bundle);
//            }
        }
    }

}
