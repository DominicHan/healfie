package com.fn.healfie.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.elvishew.xlog.XLog;
import com.fn.healfie.component.loading.CustomProgressDialog;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.login.SaveNameActivity;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.ToastUtil;


import java.util.HashMap;
import java.util.List;

/**
 * Created by thinkpad on 2016/6/15.
 */
public abstract class BaseFragment extends Fragment {
    public Context context;
    public MainActivity activity;
    public boolean isLoadingMore = true;
    public boolean canLoad = true;
    private CustomProgressDialog mCustomProgressDialog;
    private Handler baseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        activity = (MainActivity) getActivity();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCustomProgressDialog = new CustomProgressDialog(context, activity);
        baseHandler.postDelayed(LOAD_DATA, 500);
    }

    private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {            //在这里讲数据内容加载到Fragment上
            initData();
        }
    };

    public void loge(String content) {
        XLog.e(content);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * from @zhaojians
     * content 獲取手機唯一標識符
     */
    private String getIMEI() {
        TelephonyManager TelephonyMgr = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    /**
     * from @zhaojian
     * content  自動登錄
     */
    public void sendLogin(ConnectLoginBack callBack) {
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", PrefeUtil.getString(activity, PrefeKey.USERNAME, ""));
        map.put("password", PrefeUtil.getString(activity, PrefeKey.USERPWD, ""));
        map.put("isFaceBookLogin", "0");
        map.put("mobileId", getIMEI());
        map.put("isAutoLogin", "1");
        map.put("mobileType", "2");
        connect.login(MyUrl.LOGIN, map, callBack);
    }


    /**
     * 获取数据
     */
    protected abstract void initData();

    /**
     * 判断字符串是否为空
     *
     * @param value 需要判断的字符串
     * @return true为空
     */
    public boolean isEmpty(String value) {
        if (value == null || value.trim().equals("") || value.trim().equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    public void showDialog() {
//        if (!mCustomProgressDialog.isShowing()) {
        mCustomProgressDialog.show("");
//        }
    }

    public void hideDialog() {
//        if (mCustomProgressDialog.isShowing()) {
        mCustomProgressDialog.dismiss("");
//        }
    }
}
