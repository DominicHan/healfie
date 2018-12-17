package com.fn.healfie.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.RegisterActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.RegisterModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * from @zhaojian
 * content 登錄頁
 */

public class RegisterActivity extends BaseActivity implements BaseOnClick {

    RegisterActivityBinding binding;
    RegisterModule module;
    Activity activity = this;
    Timer timer;
    TimerTask timerTask;
    int count = 60;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    if (bean.getResultCode().equals("200")) {
                        /**
                         * from @zhaojian
                         * content  創建定時器，進行讀秒操作
                         */
                        timer = new Timer();
                        timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (count <= 0) {
                                    timer.cancel();
                                    timerTask.cancel();
                                    count = 60;
                                    module.setCodeSec("重新獲取驗證碼");
                                } else {
                                    module.setCodeSec(count + "s");
                                    count--;
                                }
                            }
                        };
                        timer.schedule(timerTask, 1, 1000);//延时1s执行
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:
                    RegisterBean beans = JsonUtil.getBean(msg.obj.toString(), RegisterBean.class);
                    if (beans.getResultCode().equals("200")) {
                        PrefeUtil.saveString(activity, PrefeKey.USERNAME, module.getName());
                        PrefeUtil.saveString(activity, PrefeKey.USERPWD, module.getPassword());
                        Intent intent = new Intent(activity, SaveNameActivity.class);
                        intent.putExtra(PrefeKey.TOKEN, beans.getItem().getAuthorization());
                        startActivity(intent);
                    } else {
                        ToastUtil.errorToast(activity, beans.getResultCode());
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.register_activity, new LoginComponent(this));
        module = new RegisterModule();
        module.setImageId(R.mipmap.ic_notify_normal);
        module.setPwdShow(true);
        module.setName("");
        module.setPassword("");
        module.setCode("");
        module.setCodeSec("獲取驗證碼");
        binding.setRegister(module);
        binding.setVariable(BR.click, this);

    }

    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        Log.e(TAG, "1111111" + module.getName());
        switch (id) {
            case R.id.iv_see:
                if (module.getPwdShow()) {
                    module.setImageId(R.mipmap.ic_notify_pressed);
                    module.setPwdShow(false);
                } else {
                    module.setImageId(R.mipmap.ic_notify_normal);
                    module.setPwdShow(true);
                }
                TimerTask tasks = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.etPwd.setSelection(binding.etPwd.getText().toString().length());
                            }
                        });
                    }
                };
                Timer timers = new Timer();
                timers.schedule(tasks, 500);//0.5秒后执行光标后移操作
                break;
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.rl_get_code:
                if (module.getName().length() < 11) {
                    ToastUtil.showToast(this, "請輸入正確手機號");
                    return;
                }
                if (count < 60) {
                    return;
                }
                getCode();
                break;
            case R.id.btn_zc:
                sendRegister();
                break;
        }
    }

    /**
     * from @zhaojian
     * content  注册
     */
    private void sendRegister() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("phoneNumber", module.getName());
        map.put("verificationCode", module.getCode());
        map.put("mobileId", getIMEI());
        map.put("mobileType", "2");
        map.put("password", module.getPassword());
        connect.postData(MyUrl.REGISTER, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    /**
     * from @zhaojian
     * content 獲取手機唯一標識符
     */
    private String getIMEI() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }


    /**
     * from @zhaojian
     * content 獲取手機驗證碼
     */
    public void getCode() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("phoneNumber", module.getName());
        map.put("cityCode", "+86");
        connect.getData(MyUrl.VERIFICATION, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }
}
