package com.fn.healfie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.telephony.TelephonyManager;

import com.elvishew.xlog.XLog;
import com.fn.healfie.component.loading.CustomProgressDialog;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.utils.PrefeUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/11/1.
 */

public class BaseActivity extends Activity {
    private CustomProgressDialog mCustomProgressDialog;

    public void showDialog() {
        if(mCustomProgressDialog==null){
            mCustomProgressDialog = new CustomProgressDialog(this, this);
        }
        mCustomProgressDialog.show("");
    }

    public void hideDialog() {
        if(mCustomProgressDialog==null){
            mCustomProgressDialog = new CustomProgressDialog(this, this);
        }
        mCustomProgressDialog.dismiss("");
    }

    public void loge(String content) {
        XLog.e(content);
    }
    /**
     * from @zhaojian
     * content  自動登錄
     */
    public void sendLogin(ConnectLoginBack callBack) {
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", PrefeUtil.getString(getApplicationContext(), PrefeKey.USERNAME, ""));
        map.put("password", PrefeUtil.getString(getApplicationContext(), PrefeKey.USERPWD, ""));
        map.put("isFaceBookLogin", "0");
        map.put("mobileId", getIMEI());
        map.put("isAutoLogin", "1");
        map.put("mobileType", "2");
        connect.login(MyUrl.LOGIN, map, callBack);
    }

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

    /**
     * from @zhaojians
     * content 獲取手機唯一標識符
     */
    private String getIMEI() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getApplicationContext().getSystemService(Activity.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }
}
