package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.event.BindMobileEvent;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.login.ResetPwdActivity;
import com.fn.healfie.model.MineBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

public class SafetyCheckActivity extends BaseActivity implements View.OnClickListener{


    Activity activity = this;

    private MineBean bean;
    private ImageView backIv;
    private RelativeLayout fbRl;
    private TextView fbTv;
    private RelativeLayout mobileRl;
    private TextView mobileTv;
    private RelativeLayout passwordRl;
    private TextView passwordTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.safety_check_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        fbRl = findViewById(R.id.fb_rl);
        fbRl.setOnClickListener(this);
        fbTv = findViewById(R.id.fb_tv);

        mobileRl = findViewById(R.id.mobile_rl);
        mobileRl.setOnClickListener(this);
        mobileTv = findViewById(R.id.mobile_tv);

        passwordRl = findViewById(R.id.password_rl);
        passwordRl.setOnClickListener(this);
        passwordTv = findViewById(R.id.password_tv);

        EventBus.getDefault().register(this);

        getData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }

        if(view.getId() == R.id.fb_rl){

        }
        if(view.getId() == R.id.mobile_rl){
            Intent intent = new Intent(activity, BindMobileActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.password_rl){
            Intent intents = new Intent(activity, ResetPwdActivity.class);
            startActivity(intents);
        }

    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.MEMBERINFO, map, new ConnectBack() {
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

    private void setData(){
        if(bean.getItem().getIsBindFaceBook() == 0){
            fbTv.setText("去綁定");
            fbTv.setTextColor(getResources().getColor(R.color.textGray9));
        }else if(bean.getItem().getIsBindFaceBook() == 1){
            fbTv.setText("已綁定");
            fbTv.setTextColor(getResources().getColor(R.color.textBlack));
        }

        if(TextUtils.isEmpty(bean.getItem().getPhoneNumber())){
            mobileTv.setText("去添加");
            mobileTv.setTextColor(getResources().getColor(R.color.textGray9));
        }else{
            mobileTv.setText(bean.getItem().getPhoneNumber());
            mobileTv.setTextColor(getResources().getColor(R.color.textGray9));
        }
    }


    @Subscribe
    public void refreshData(BindMobileEvent event) {
        getData();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    bean = JsonUtil.getBean(msg.obj.toString(), MineBean.class);
                    if (bean.getResultCode().equals("200")) {
                        setData();
                    } else if (bean.getResultCode().equals("-10010")) {
                        showDialog();
                        sendLogin(new ConnectLoginBack() {
                            @Override
                            public void success(String json, String header) {
                                hideDialog();
                                RegisterBean registerBean = JsonUtil.getBean(json, RegisterBean.class);
                                if(registerBean.getResultCode().equals("200")){
                                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, registerBean.getItem().getAuthorization());
                                    myHandler.sendEmptyMessage(2);

                                }else{
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(activity, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:
                    getData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
