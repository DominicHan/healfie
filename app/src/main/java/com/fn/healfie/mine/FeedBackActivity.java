package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.contact.EditContactActivity;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.MessageListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.HashMap;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;
    private boolean isPerformance = true;

    private ImageView backIv;
    private TextView performanceTv;
    private TextView functionTv;
    private EditText contentEt;
    private EditText contactEt;
    private TextView finishTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.feedback_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        performanceTv = findViewById(R.id.qt_performance_tv);
        performanceTv.setOnClickListener(this);
        functionTv = findViewById(R.id.qt_function_tv);
        functionTv.setOnClickListener(this);

        contentEt = findViewById(R.id.content_et);
        contactEt = findViewById(R.id.contact_et);

        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.qt_performance_tv){
            isPerformance = true;
            changeQtType();
        }
        if(view.getId() == R.id.qt_function_tv){
            isPerformance = false;
            changeQtType();
        }
        if(view.getId() == R.id.finish_tv){
            getData();
        }
    }

    private void getData() {
        String content = contentEt.getText().toString();
        if(TextUtils.isEmpty(content)){
            ToastUtil.showToast(this,"內容不能為空");
            return;
        }
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("type", isPerformance?"1":"2");
        map.put("content", content);
        //map.put("image", content);
        map.put("contactInformation", contactEt.getText().toString());

        connect.getData(MyUrl.PROPOSAL, map, new ConnectBack() {
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

    private void changeQtType(){
        if(isPerformance){
            performanceTv.setBackgroundResource(R.drawable.care_card_board_shape);
            performanceTv.setTextColor(getResources().getColor(R.color.buttonBlue));
            functionTv.setBackgroundResource(R.drawable.care_card_shape);
            functionTv.setTextColor(getResources().getColor(R.color.textBlack));
        }else{
            functionTv.setBackgroundResource(R.drawable.care_card_board_shape);
            functionTv.setTextColor(getResources().getColor(R.color.buttonBlue));
            performanceTv.setBackgroundResource(R.drawable.care_card_shape);
            performanceTv.setTextColor(getResources().getColor(R.color.textBlack));
        }
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {
                        ToastUtil.showToast(FeedBackActivity.this,"提交成功");
                        finish();
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
                                    Intent intent = new Intent(FeedBackActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(FeedBackActivity.this, LoginActivity.class);
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
