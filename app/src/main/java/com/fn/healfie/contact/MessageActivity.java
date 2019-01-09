package com.fn.healfie.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.MessageListAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    Activity activity = this;

    private ImageView backIv;
    private ListView messageLv;
    private List<MessageBean> mData;
    private MessageListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.message_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        messageLv = findViewById(R.id.lv_message);

        mData = new ArrayList<>();
        mAdapter = new MessageListAdapter(this,mData);
        messageLv.setAdapter(mAdapter);

        getData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
    }


    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.FRIENDAUDIT, map, new ConnectBack() {
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

    private void changeData(MessageListBean bean){
        mData.clear();
        MessageBean titleBeanUnaudit = new MessageBean();
        titleBeanUnaudit.setType(0);
        mData.add(titleBeanUnaudit);

        if(bean.getItem().getUnaudited() != null && bean.getItem().getUnaudited().size() > 0){
            for(MessageBean msg : bean.getItem().getUnaudited()){
                msg.setType(1);
                mData.add(msg);
            }
        }

        MessageBean titleBeanAudit = new MessageBean();
        titleBeanUnaudit.setType(2);
        mData.add(titleBeanAudit);

        if(bean.getItem().getAudited() != null && bean.getItem().getAudited().size() > 0){
            for(MessageBean msg : bean.getItem().getAudited()){
                msg.setType(3);
                mData.add(msg);
            }
        }

        mAdapter.notifyDataSetChanged();

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MessageListBean bean = JsonUtil.getBean(msg.obj.toString(), MessageListBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {
                        changeData(bean);
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
                                    Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
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
