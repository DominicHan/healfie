package com.fn.healfie.contact;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.AddContactInfoActivityBinding;
import com.fn.healfie.databinding.SearchNameActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.model.SearchContactBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.HashMap;

/**
 * from @zhaojian
 * content 創建食物
 */

public class AddContactInfoActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    AddContactInfoActivityBinding binding;
    String data;
    SearchContactBean bean;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BaseBean result = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);

                    if (result.getResultCode().equals("200")) {
                        finish();
                    } else if (result.getResultCode().equals("-10010")) {
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
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, result.getResultCode());
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.add_contact_info_activity);
        binding.setVariable(BR.click, this);
        initData();
    }

    private void initData() {
        data = getIntent().getStringExtra("data");
        bean =  JsonUtil.getBean(data,SearchContactBean.class);
        binding.tvName.setText(bean.getItem().getName());
    }

    private void getData() {
//        if(binding.etInput.getText().toString().equals("")){
//            ToastUtil.showToast(this,"請輸入會員名");
//            return;
//        }
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("desc", binding.etInput.getText().toString());
        map.put("memberId", bean.getItem().getId());
        map.put("showLimit", "2");
        connect.postData(MyUrl.FRIENDINFO, map, new ConnectBack() {
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
                loge("2222"+json);
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }
    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_fsgzsq:
                getData();
                break;
        }
    }


}
