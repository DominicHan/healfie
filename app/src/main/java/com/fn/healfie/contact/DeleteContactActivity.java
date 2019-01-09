package com.fn.healfie.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
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

public class DeleteContactActivity extends BaseActivity implements View.OnClickListener{

    Activity activity = this;

    final int REQUEST_REMARK = 0x01;

    private String data;
    private MessageBean bean;

    private ImageView backIv;
    private TextView nameTv;
    private TextView remarkTv;
    private TextView permissionTv;
    private TextView finishTv;
    private RelativeLayout remarkRl;
    private RelativeLayout permissionRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.delete_contact_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        nameTv = findViewById(R.id.name_tv);
        remarkTv = findViewById(R.id.remark_tv);
        permissionTv = findViewById(R.id.permission_tv);
        finishTv = findViewById(R.id.finish_tv);
        remarkRl = findViewById(R.id.remark_rl);
        permissionRl = findViewById(R.id.permission_rl);
        permissionRl.setOnClickListener(this);
        remarkRl.setOnClickListener(this);
        finishTv.setOnClickListener(this);

        initData();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }

        if(view.getId() == R.id.finish_tv){
            deleteMember();
        }

        if(view.getId() == R.id.remark_rl){
            Intent intent = new Intent(this, EditRemarkActivity.class);
            intent.putExtra("data",remarkTv.getText().toString());
            startActivityForResult(intent,REQUEST_REMARK);
        }

        if(view.getId() == R.id.permission_rl){

        }
    }


    private void initData(){
        data = getIntent().getStringExtra("data");
        bean =  JsonUtil.getBean(data,MessageBean.class);

        nameTv.setText(bean.getName());
        remarkTv.setText(bean.getRemark());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_REMARK){
                String result = data.getExtras().getString("result");
                remarkTv.setText(result);
                getData();
            }
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("memberId", bean.getMemberId()+"");
        map.put("desc", remarkTv.getText().toString());
        map.put("showLimit",1+""); //1-星标；2-一般；3-屏蔽
        connect.putData(MyUrl.FRIENDINFO, map, new ConnectBack() {
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

    private void deleteMember(){
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("memberId", bean.getMemberId()+"");
        connect.deleteData(MyUrl.FRIENDINFO + "/" + bean.getMemberId(), map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 3;
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
                    MessageListBean bean = JsonUtil.getBean(msg.obj.toString(), MessageListBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {

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
                                    Intent intent = new Intent(DeleteContactActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(DeleteContactActivity.this, LoginActivity.class);
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
                case 3:
                    MessageListBean bean3 = JsonUtil.getBean(msg.obj.toString(), MessageListBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean3.getResultCode().equals("200")) {
                        finish();
                    } else if (bean3.getResultCode().equals("-10010")) {
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
                                    Intent intent = new Intent(DeleteContactActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(DeleteContactActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, bean3.getResultCode());
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
