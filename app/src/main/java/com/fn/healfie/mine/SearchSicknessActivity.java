package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.event.AddAllergyEvent;
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

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class SearchSicknessActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private ImageView searchIv;
    private EditText inputEt;

    private LinearLayout noResultLl;
    private TextView hintTv;
    private TextView finishTv;
    private ListView searchLv;

    private String searchName;
    private String noResultFmt = "沒有搜索到\"%s\"這種疾病，您是否確定添加\"<font color='#0ba7c5'>%s</font>\"為您的既往疾病";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.search_sickness_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        searchIv = findViewById(R.id.iv_search);
        searchIv.setOnClickListener(this);

        inputEt = findViewById(R.id.et_input);
        inputEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    getData();
                }
                return false;
            }
        });

        noResultLl = findViewById(R.id.no_result_ll);
        noResultLl.setVisibility(View.VISIBLE);
        hintTv = findViewById(R.id.hint_tv);
        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        searchLv = findViewById(R.id.search_lv);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.iv_search){
            getData();
        }
        if(view.getId() == R.id.finish_tv){
            setNoResultData();
        }
    }

    private void getData(){
        searchName = inputEt.getText().toString();
        if(TextUtils.isEmpty(searchName)){
            ToastUtil.showToast(activity,"請輸入內容");
            return;
        }

        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("type", "1");
        map.put("keywords", searchName);
        connect.getData(MyUrl.ALLERGYSEARCH, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = json;
                //myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void setNoResultData(){
        searchName = inputEt.getText().toString();
        if(TextUtils.isEmpty(searchName)){
            ToastUtil.showToast(activity,"請輸入內容");
            return;
        }
        hintTv.setText(Html.fromHtml(String.format(noResultFmt,searchName,searchName)));

        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("type", "1");
        map.put("isCustom", "1");
        map.put("name", searchName);
        connect.postData(MyUrl.ALLERGYADD, map, new ConnectBack() {
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

            if(msg.what == 1){
                SearchContactBean bean = JsonUtil.getBean(msg.obj.toString(), SearchContactBean.class);
//                    loge(bean.getItem().toString()+"");
                if (bean.getResultCode().equals("200")) {

                } else if (bean.getResultCode().equals("-10010")) {
                    postLogin();
                } else {
                    ToastUtil.errorToast(activity, bean.getResultCode());
                }
            }

            if(msg.what == 2){
                getData();
            }

            if(msg.what == 3){
                BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                if (bean.getResultCode().equals("200")) {
                    EventBus.getDefault().post(new AddAllergyEvent());
                    finish();
                } else if (bean.getResultCode().equals("-10010")) {
                    postLogin();
                } else {
                    ToastUtil.errorToast(activity, bean.getResultCode());
                }
            }

            super.handleMessage(msg);
        }
    };

    private void postLogin(){
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
    }
}
