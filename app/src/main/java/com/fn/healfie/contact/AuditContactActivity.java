package com.fn.healfie.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.MessageBean;
import com.fn.healfie.model.MessageListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.HashMap;


public class AuditContactActivity extends BaseActivity implements View.OnClickListener{

    Activity activity = this;

    private String data;
    private MessageBean bean;
    private ImageView backIv;
    private TextView nameTv;
    private TextView markTv;
    private RoundImageView headIv;
    private TextView rejectTv;
    private TextView acceptTv;

    private String btnResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.audit_contact_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        nameTv = findViewById(R.id.tv_name);
        markTv = findViewById(R.id.mark_tv);
        headIv = findViewById(R.id.rv_image);

        rejectTv = findViewById(R.id.reject_tv);
        acceptTv = findViewById(R.id.accept_tv);
        rejectTv.setOnClickListener(this);
        acceptTv.setOnClickListener(this);

        initData();

    }

    private void initData(){
        data = getIntent().getStringExtra("data");
        bean =  JsonUtil.getBean(data,MessageBean.class);

        nameTv.setText(bean.getName());
        markTv.setText(bean.getRemark());

        Glide.with(activity).load(getUrl(bean.getHeadImageObject(),bean.getHeadImageBucket())).into(headIv);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.reject_tv){
            btnResult = "2";
            getData();
        }
        if(view.getId() == R.id.accept_tv){
            btnResult = "1";
            getData();
//            Intent intents = new Intent(AuditContactActivity.this, EditContactActivity.class);
//            intents.putExtra("data",data);
//            startActivity(intents);
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("memberId", bean.getMemberId()+"");
        map.put("audit", btnResult);//1通过 2拒绝
        connect.putData(MyUrl.FRIENDAUDIT, map, new ConnectBack() {
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

    public String getUrl(String img, String bucket) {
        Log.e("getUrl: ", MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(activity, PrefeKey.TOKEN, "");
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MessageListBean bean = JsonUtil.getBean(msg.obj.toString(), MessageListBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {
                        if(btnResult == "2"){
                            finish();
                        }
                        if(btnResult == "1"){
                            Intent intents = new Intent(AuditContactActivity.this, EditContactActivity.class);
                            intents.putExtra("data",data);
                            startActivity(intents);
                            finish();
                        }
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
                                    Intent intent = new Intent(AuditContactActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(AuditContactActivity.this, LoginActivity.class);
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
