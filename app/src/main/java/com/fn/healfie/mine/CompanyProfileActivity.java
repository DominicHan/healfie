package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.AboutBean;
import com.fn.healfie.model.MessageListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import org.w3c.dom.Text;

import java.util.HashMap;

public class CompanyProfileActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;
    private AboutBean bean;

    private ImageView backIv;
    private TextView introduceTv;
    private TextView telTv;
    private TextView emailTv;
    private TextView facebookTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.company_profile_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        introduceTv = findViewById(R.id.introduce_tv);
        telTv = findViewById(R.id.tel_tv);
        emailTv = findViewById(R.id.email_tv);
        facebookTv = findViewById(R.id.facebook_tv);

        getData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
    }

    private void setData(){
        if(bean != null){
            introduceTv.setText(bean.getItem().getIntroduce());
            telTv.setText("電話：" + bean.getItem().getTel());
            emailTv.setText("郵箱：" + bean.getItem().getEmail());
            facebookTv.setText("Facebook：" + bean.getItem().getFacebook());
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.ABOUT, map, new ConnectBack() {
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

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    bean = JsonUtil.getBean(msg.obj.toString(), AboutBean.class);
                    loge(msg.obj.toString()+"");
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
                                    Intent intent = new Intent(CompanyProfileActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(CompanyProfileActivity.this, LoginActivity.class);
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
}
