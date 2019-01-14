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
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.NewsListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;
import com.google.zxing.common.StringUtils;

import java.util.HashMap;

public class EditCareActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private TextView finishTv;
    private RelativeLayout allergyRl;
    private TextView allergyTv;
    private RelativeLayout sicknessRl;
    private TextView sicknessTv;

    private EditText nameEt;
    private EditText sexEt;
    private EditText ageEt;
    private EditText heightEt;
    private EditText weightEt;
    private EditText bloodEt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.edit_care_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        allergyRl = findViewById(R.id.allergy_rl);
        allergyRl.setOnClickListener(this);
        allergyTv = findViewById(R.id.allergy_tv);

        sicknessRl = findViewById(R.id.sickness_rl);
        sicknessRl.setOnClickListener(this);
        sicknessTv = findViewById(R.id.sickness_tv);

        nameEt = findViewById(R.id.name_et);
        sexEt = findViewById(R.id.sex_et);
        ageEt = findViewById(R.id.age_et);
        heightEt = findViewById(R.id.height_et);
        weightEt = findViewById(R.id.weight_et);
        bloodEt = findViewById(R.id.blood_et);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.finish_tv){
            putData();
        }
        if(view.getId() == R.id.allergy_rl){
            Intent intent = new Intent(activity,AddAllergyActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.sickness_rl){
            Intent intent = new Intent(activity,AddSicknessActivity.class);
            startActivity(intent);
        }

    }

    private void putData(){
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("height", getStringOrEmpty(heightEt.getText().toString()));
        map.put("weight", getStringOrEmpty(weightEt.getText().toString()));
        map.put("bloodType", getStringOrEmpty(bloodEt.getText().toString()));
        map.put("dateOfBirth", getStringOrEmpty(ageEt.getText().toString()));
        connect.getData(MyUrl.MESSAGELIST, map, new ConnectBack() {
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
                    BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {
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
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(activity, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:
                    putData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private String getStringOrEmpty(String str){
        return TextUtils.isEmpty(str)?"":str;
    }

}
