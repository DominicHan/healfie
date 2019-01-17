package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.component.imageselector.utils.ImageSelector;
import com.fn.healfie.component.imageselector.utils.ImageSelectorUtils;
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
import com.fn.healfie.utils.FileUtil;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{

    private final int REQUEST_CODE = 0x0001;
    private Activity activity = this;
    private boolean isPerformance = true;

    private ImageView backIv;
    private TextView performanceTv;
    private TextView functionTv;
    private EditText contentEt;
    private EditText contactEt;
    private TextView finishTv;

    private RelativeLayout photoRl;
    private ImageView contentIv;
    private ImageView deleteIv;
    private String imgBase64;

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

        photoRl = findViewById(R.id.photo_rl);
        photoRl.setOnClickListener(this);
        contentIv = findViewById(R.id.content_iv);
        deleteIv = findViewById(R.id.delete_iv);
        deleteIv.setOnClickListener(this);
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
        if(view.getId() == R.id.photo_rl){
            ImageSelector.builder()
                    .useCamera(true) // 设置是否使用拍照
                    .setSingle(true)  //设置是否单选
                    .setViewImage(true) //是否点击放大图片查看,，默认为true
                    .start(this, REQUEST_CODE); // 打开相册
        }
        if(view.getId() == R.id.delete_iv){
            deleteIv.setVisibility(View.GONE);
            contentIv.setImageBitmap(null);
            imgBase64 = "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelector.SELECT_RESULT);
            if(!TextUtils.isEmpty(images.get(0))){
                File file = new File(images.get(0));
                if(file.exists()){
                    try{
                        Glide.with(this).load(file).into(contentIv);
                        imgBase64 = FileUtil.encodeBase64File(new Compressor(this).compressToFile(file));
                        deleteIv.setVisibility(View.VISIBLE);
                    }catch (Exception ex){

                    }

                }
            }
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
        map.put("image", TextUtils.isEmpty(imgBase64)?"":imgBase64);
        map.put("contactInformation", contactEt.getText().toString());

        connect.postData(MyUrl.PROPOSAL, map, new ConnectBack() {
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
