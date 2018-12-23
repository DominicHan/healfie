package com.fn.healfie.food;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.LoginActivityBinding;
import com.fn.healfie.databinding.UnidentifiedFoodActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.login.RegisterActivity;
import com.fn.healfie.login.ResetPwdActivity;
import com.fn.healfie.login.SaveNameActivity;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.LoginModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * from @zhaojian
 * content 食物圖片未識別
 */

public class UnidentifiedFoodActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    UnidentifiedFoodActivityBinding binding;
    String path;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.unidentified_food_activity);
        binding.setVariable(BR.click, this);
        path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        Log.e(TAG, "onCreate: " + path);
        Log.e(TAG, "onCreate: " + Environment.DIRECTORY_PICTURES);
        binding.rvImage.changeRadius(5);
        File file = new File(path);
        Glide.with(this).load(file).into(binding.rvImage);
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
            case R.id.btn_zjsw:
                Intent intents = new Intent(UnidentifiedFoodActivity.this, CreateFoodActivity.class);
                intents.putExtra(CameraActivity.CAMERA_PATH_VALUE1, path);
                startActivity(intents);
                break;
            case R.id.btn_ssgd:
                Intent intent = new Intent(UnidentifiedFoodActivity.this, SearchFoodActivity.class);
                intent.putExtra(CameraActivity.CAMERA_PATH_VALUE1, path);
                startActivity(intent);
                break;
        }
    }


}
