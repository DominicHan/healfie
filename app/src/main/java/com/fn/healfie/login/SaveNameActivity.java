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
import com.fn.healfie.databinding.SaveNameActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.RegisterModule;
import com.fn.healfie.module.SaveNameModule;
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

public class SaveNameActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    SaveNameActivityBinding binding;
    SaveNameModule module;
    String token = "";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    if (bean.getResultCode().equals("200")) {
                        PrefeUtil.saveString(activity, PrefeKey.TOKEN, token);
                        Intent intents = new Intent(SaveNameActivity.this, MainActivity.class);
                        startActivity(intents);
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.save_name_activity, new LoginComponent(this));
        module = new SaveNameModule();
        module.setName("");
        binding.setSave(module);
        binding.setVariable(BR.click, this);
        token = getIntent().getStringExtra(PrefeKey.TOKEN);
    }

    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        Log.e(TAG, "1111111" + module.getName());
        switch (id) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.btn_tj:
                this.sendData();
                break;
            case R.id.iv_tx:

                break;
        }
    }

    /**
     * from @zhaojian
     * content 提交信息
     */
    public void sendData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", token);
        map.put("name", module.getName());
        connect.putData(MyUrl.MEMBERINFO, map, new ConnectBack() {
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
